package com.tutorial.basic.feign;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public class FeignConfiguration {
	
	@Value("${security.oauth2.client.userAuthorizationUri}")
	private String authorizeUrl;

	@Value("${security.oauth2.client.accessTokenUri}")
	private String accessTokenUri;

	@Value("${security.oauth2.client.clientId}")
	private String clientId;
	
	@Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;
	
    @Value("${security.oauth2.client.scope}")
    private String scope;
    
    
    @Bean
    protected OAuth2ProtectedResourceDetails authorizationCodeDetails() {
      AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
//      ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
      resource.setUserAuthorizationUri(authorizeUrl);
      resource.setScope(Arrays.asList(scope));
      resource.setClientId(clientId);
      resource.setClientSecret(clientSecret);
//      resource.setGrantType("authorization_code");
      resource.setAccessTokenUri(accessTokenUri);
      resource.setPreEstablishedRedirectUri("");

      return resource;
    }
   
    
    @Bean
    public OAuth2FeignRequestInterceptor oAuth2FeignRequestInterceptor(OAuth2ProtectedResourceDetails details) {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), authorizationCodeDetails());
    }

}