package com.tutorial.auth.feign;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

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
    public OAuth2FeignRequestInterceptor oAuth2FeignRequestInterceptor(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
            return new OAuth2FeignRequestInterceptor(oauth2ClientContext,details);
    }


}