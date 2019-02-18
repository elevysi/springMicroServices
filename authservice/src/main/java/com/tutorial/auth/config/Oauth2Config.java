package com.tutorial.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter{
	
	
	@Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
	
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
          .tokenKeyAccess("permitAll()")
          .checkTokenAccess("isAuthenticated()")
          .passwordEncoder(bCryptPasswordEncoder)
          ;
    }
	
	@Override
	 public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	
	      clients.inMemory()
	          .withClient("web")
			  .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit", "client_credentials")
			  .authorities("ROLE_CLIENT")
			  .scopes("read", "write").resourceIds("authorizationResourceApi")
//			  .secret("secret")
			  .secret("$2a$10$YkTbWNA/jGQqvSEZwDqJOOdjXGNFjttY3HslAUCFJO2u/3ObNGUCK") //root
			  .redirectUris("http://localhost:1000")
			  .accessTokenValiditySeconds(120)//Access token is only valid for 2 minutes.
			  .refreshTokenValiditySeconds(600)//Refresh token is only valid for 10 minutes.
//			  .autoApprove(true)
	          ;
	 }
	
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
  
        endpoints
          .tokenStore(tokenStore())
          .authenticationManager(authenticationManager);
    }

}
