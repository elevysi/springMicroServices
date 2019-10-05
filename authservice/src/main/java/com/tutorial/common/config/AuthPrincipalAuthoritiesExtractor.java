package com.tutorial.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthPrincipalAuthoritiesExtractor implements AuthoritiesExtractor{

	@Override
	public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
		
		ArrayList<String> authStringList = (ArrayList<String>) map.get("authorities");
	    String authorityString = StringUtils.join(authStringList, ',');
	    List<GrantedAuthority> authList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
		
		return authList;
		
	}
}