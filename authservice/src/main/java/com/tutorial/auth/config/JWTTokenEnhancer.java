package com.tutorial.auth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenEnhancer implements TokenEnhancer{
	
	private static final String ISSUER = "http://localhost:2000";
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication){
		
		Map<String, Object> additionalinfo = new HashMap<>();
		additionalinfo.put("iss", ISSUER);
		additionalinfo.put("sub", authentication.getPrincipal());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalinfo);
		return accessToken;
	}
}
