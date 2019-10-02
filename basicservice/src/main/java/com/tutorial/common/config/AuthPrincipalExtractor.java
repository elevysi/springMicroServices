package com.tutorial.common.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Component
public class AuthPrincipalExtractor implements PrincipalExtractor{

    
    
    @Override
	public Object extractPrincipal(Map<String, Object> map) {
		
		String principalUsername = (String) map.get("username");
		Boolean isEnabled = (Boolean) map.get("isEnabled");
		String email = (String) map.get("email");
		
		
    	boolean enabled = isEnabled;
	    boolean accountNonExpired = isEnabled;
	    boolean credentialsNonExpired = isEnabled;
	    boolean accountNonLocked = isEnabled;
	    
	    ArrayList<String> authStringList = (ArrayList<String>) map.get("authorities");
	    String authorityString = StringUtils.join(authStringList, ',');
	    List<GrantedAuthority> authList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
	    
		
		UserPrincipal activeUser = new UserPrincipal(
		 	principalUsername,
    	 	"",
    	 	enabled, 
            accountNonExpired, 
            credentialsNonExpired, 
            accountNonLocked,
            authList,
            email
		);
	    	 
    	return activeUser;
	}
}