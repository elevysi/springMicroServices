package com.tutorial.auth.feign;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import feign.RequestInterceptor;

public class FeignClientConfiguration {
	
	
	
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
    protected OAuth2ProtectedResourceDetails clientCredentialsResourceDetails() {
    //  AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
    //  ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
    //  resource.setUserAuthorizationUri(authorizeUrl);
    //  resource.setScope(Arrays.asList(scope));

        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setGrantType("client_credentials");
        resource.setAccessTokenUri(accessTokenUri);

        return resource;
    }

    @Bean
    public OAuth2FeignRequestInterceptor oauth2FeignRequestInterceptor(){
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

}





