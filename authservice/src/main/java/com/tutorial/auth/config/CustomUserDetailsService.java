package com.tutorial.auth.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tutorial.auth.config.social.AuthProvider;
import com.tutorial.auth.config.social.exception.OAuth2AuthenticationProcessingException;
import com.tutorial.auth.config.social.user.SocialUserInfo;
import com.tutorial.auth.config.social.user.SocialUserInfoFactory;
import com.tutorial.auth.dao.UserDAO;
import com.tutorial.auth.model.Group;
import com.tutorial.auth.model.Role;
import com.tutorial.auth.model.User;
import com.tutorial.auth.service.GroupService;
import com.tutorial.common.UserPrincipal;

@Service
public class CustomUserDetailsService extends DefaultOAuth2UserService implements UserDetailsService{
	
	private UserDAO userDAO;
	private GroupService groupService;
	
	@Autowired
	public CustomUserDetailsService(UserDAO userDAO, GroupService groupService){
		this.userDAO = userDAO;
		this.groupService = groupService;
	}
	
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                try{
	                User domainUser = userDAO.loadByUsername(username);
	                return this.create(domainUser);
                }catch(UsernameNotFoundException e){
                    throw new RuntimeException(e);
                }
    }
	
	public UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal =  this.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }
	
	public UserPrincipal create(User user){
		
		
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
	 
	@Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
    		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
        		
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }
	
	
	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws Exception{
        
	
		SocialUserInfo oAuth2UserInfo = SocialUserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
	    if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
	        throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
	    }
	
	    User user = userDAO.loadByUsername(oAuth2UserInfo.getId());
	    if(user != null) {
	    		 if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
	               throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
	                       user.getProvider() + " account. Please use your " + user.getProvider() +
	                       " account to login.");
	           }
	    		 user = updateExistingUser(user, oAuth2UserInfo);
	    }else {
	    		user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
	    }
		
	    return create(user, oAuth2User.getAttributes());
	}
	
	
	private User registerNewUser(
			OAuth2UserRequest oAuth2UserRequest, 
			SocialUserInfo oAuth2UserInfo
	) throws IOException{
	    
		User user = new User();
	
	    user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
	    user.setName(oAuth2UserInfo.getName());
	    user.setPassword("");
	    user.setEmail(oAuth2UserInfo.getEmail());
	    user.setUsername(oAuth2UserInfo.getId());
	    
	    User savedUser = registerUser(user);
	     
	    return savedUser;
	}

	private User updateExistingUser(User existingUser, SocialUserInfo oAuth2UserInfo) {
	    
		existingUser.setName(oAuth2UserInfo.getName());
	    return userDAO.edit(existingUser);
	}
	
	
	public User registerUser(User user){
		
		
		user.setActive(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		//Give the user the adequate group which gives them the roles
		Group userGroup = groupService.findByID(new Long(1));
		if (userGroup != null) {
			user.getGroups().add(userGroup);
		}
		
		User savedUser = userDAO.save(user);
		
		//Reload User with the right information to avoid failed to initialize a collection of role
		
		return userDAO.loadByUsername(savedUser.getUsername());
	}
	
}
