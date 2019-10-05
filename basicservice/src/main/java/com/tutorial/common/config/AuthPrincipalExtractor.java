package com.tutorial.common.config;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class AuthPrincipalExtractor implements PrincipalExtractor{

    
    
    @Override
	public Object extractPrincipal(Map<String, Object> map) {
		
		String principalUsername = (String) map.get("user");
		
		Boolean isEnabled = true;
    	boolean enabled = isEnabled;
	    boolean accountNonExpired = isEnabled;
	    boolean credentialsNonExpired = isEnabled;
	    boolean accountNonLocked = isEnabled;
	    
	    List<GrantedAuthority> authList = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	    
		
		UserPrincipal activeUser = new UserPrincipal(
		 	principalUsername,
    	 	"",
    	 	enabled, 
            accountNonExpired, 
            credentialsNonExpired, 
            accountNonLocked,
            authList,
            principalUsername
		);
	    	 
    	return activeUser;
	}
}