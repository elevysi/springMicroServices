package com.tutorial.auth.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tutorial.auth.dao.UserDAO;
import com.tutorial.auth.model.Group;
import com.tutorial.auth.model.Role;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
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


}
