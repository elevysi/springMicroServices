package com.tutorial.auth.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.tutorial.auth.config.social.SocialUserInfo;
import com.tutorial.auth.dao.UserDAO;
import com.tutorial.auth.model.Group;
import com.tutorial.auth.model.Role;
import com.tutorial.common.UserPrincipal;

@Service
public class CustomUserDetailsService extends DefaultOAuth2UserService implements UserDetailsService{
	
	private UserDAO userDAO;
	
	@Autowired
	public CustomUserDetailsService(UserDAO userDAO){
		this.userDAO = userDAO;
	}
	
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                try{
	                com.tutorial.auth.model.User domainUser = userDAO.loadByUsername(username);
	                return this.create(domainUser);
                }catch(UsernameNotFoundException e){
                    throw new RuntimeException(e);
                }
    }
	
	public UserDetails create(com.tutorial.auth.model.User user){
		
		
		boolean enabled = user.isActive();
        boolean accountNonExpired = user.isActive();
        boolean credentialsNonExpired = user.isActive();
        boolean accountNonLocked = user.isActive();

        Set<Role> roles = new HashSet<Role>();
        Set<Group> groups = user.getGroups();

        //Add the roles inherited from the groups to the array of user's roles
        for(Group group : groups){
            roles.addAll(group.getRoles());
        }

        List<String> springSecurityRoles = treatRoles(roles);
        List<GrantedAuthority> authList = getGrantedAuthorities(springSecurityRoles);

        return new UserPrincipal(
            user.getUsername(),
            user.getPassword(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            authList,
            user.getEmail()
        );

        
	}
	
	
	public List<String> treatRoles(Set<Role> roles) {
        List<String> security_roles = new ArrayList<String>();

        for (Role userRole : roles) {
        	security_roles.add(userRole.getName());
        }

        return security_roles;
    }
	
	
	 public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
	 
//	@Override
//    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
//    		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
//
//        try {
//            return processOAuth2User(oAuth2UserRequest, oAuth2User);
//        } catch (AuthenticationException ex) {
//        		
//            throw ex;
//        } catch (Exception ex) {
//            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
//            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
//        }
//    }
//	
//	
//	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws Exception{
//        
//	
//		SocialUserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
//	    if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
//	        throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
//	    }
//	
//	//    Optional<User> userOptional = userService.findByEmailOptional(oAuth2UserInfo.getEmail());
//	//    Optional<User> userOptional = userService.findByUsernameOptional(oAuth2UserInfo.getId());
//	    com.tutorial.auth.model.User user = userDAO.loadByUsername(oAuth2UserInfo.getId());
//	    if(user != null) {
//	    		 if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
//	               throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
//	                       user.getProvider() + " account. Please use your " + user.getProvider() +
//	                       " account to login.");
//	           }
//	    		 user = updateExistingUser(user, oAuth2UserInfo);
//	    }else {
//	    		user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
//	    }
//	    		return create(user, oAuth2User.getAttributes());
//	}
//	
//	
//	private User registerNewUser(
//			OAuth2UserRequest oAuth2UserRequest, 
//			OAuth2UserInfo oAuth2UserInfo
//	) throws IOException{
//	    
//		User user = new User();
//	
//	    user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
//	    user.setProviderId(oAuth2UserInfo.getId());
//	    user.setFirst_name(oAuth2UserInfo.getName());
//	    user.setLast_name(oAuth2UserInfo.getName());
//	    user.setPassword("");
//	    user.setEmail(oAuth2UserInfo.getEmail());
//	    user.setUsername(oAuth2UserInfo.getId());
//	    
//	    User savedUser = registerUser(user);
//	    ProfileDTO profileDTO = registerUserForProfile(savedUser, "user");
//	    
//	    /**
//	     * Handle the Photo
//	     */
//	    String fileName = "Avatar.jpeg";
//	    
//	    String avatarDirPath = this.avatarUploadPath+"profiles"+this.ds+profileDTO.getId()+this.ds+"avatar"+this.ds;
//		File avatarDir = new File(avatarDirPath);
//		if (!avatarDir.exists()) {
//			avatarDir.mkdirs();
//		}
//	    
//	    try(InputStream in = new URL(oAuth2UserInfo.getImageUrl()).openStream()){
//        		Files.copy(in, Paths.get(avatarDirPath+fileName));
//        		
//        		
//		}catch (IOException e) {
//		}
//	     
//	    return savedUser;
//	}
//
//	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
//	    
//			existingUser.setName(oAuth2UserInfo.getName());
//	//    existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
//	    
//	    return userDAO.saveEdited(existingUser);
//	}
//	
//	
//	public User registerUser(User user){
//		
//		Date now = new Date();
//		
//		user.setActive(true);
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		user.setPassword(encoder.encode(user.getPassword()));
//		
//		user.setCreated(now);
//		user.setModified(now);
//		
//		//Give the user the adequate group which gives them the roles
//		Group userGroup = groupService.findByName("GROUP_USERS");
//		if (userGroup != null) {
//			user.getGroups().add(userGroup);
//		}
//		
//		User savedUser = userDAO.save(user);
//		return savedUser;
//	}
//	
//	public ProfileDTO registerUserForProfile(User user, String profileTypeName) {
//		//Created a user Profile for them as well
//		ProfileDTO profileDTO = socialFeignCallerService.createUserProfileDTOForUser(new Long(user.getId()), "user");
//		return profileDTO;
//	}
//	
//	public void authenticateUser(User user) {
//		Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        user.getUsername(),
//                        user.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//	}
}
