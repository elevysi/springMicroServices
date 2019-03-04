package com.tutorial.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

//Hybrid Security Configuration

@Configuration
public class SecurityConfig {
	
	@Configuration
	@Order(10)
	@EnableResourceServer
	public static class ResourceServerConfig extends ResourceServerConfigurerAdapter{
		
		private static final String  RESOURCE_ID = "authorizationResourceApi";
		
		@Override
        public void configure(ResourceServerSecurityConfigurer resources) {
                resources.resourceId(RESOURCE_ID).stateless(false);
        }

        @Override
		public void configure(HttpSecurity http) throws Exception{
		    http
		    .requestMatchers().antMatchers("/api/**")
		    .and()
		    .authorizeRequests()
		      .anyRequest().authenticated();
		}
		
	}
	
	
	@Configuration
	@Order(20)
//	@EnableOAuth2Client
	public static class FormSecurityConfig extends WebSecurityConfigurerAdapter{

		@Autowired
		private CustomUserDetailsService customUserDetailsService;
		
		
		
		@Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	        return bCryptPasswordEncoder;
	    }
		
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			
			http.requestMatchers()
				.antMatchers("/" , "/ui/**", "/login", "/oauth/authorize")
				.and()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/ui/public/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().permitAll()
				;
			
		}
		
		public void configure(WebSecurity webSecurity) throws Exception{
			webSecurity
				.ignoring()
				.antMatchers("/js/**")
				.antMatchers("/css/**")
				;
		}
		
		@Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	    
	    	
	    @Bean
	    public DaoAuthenticationProvider authenticationProvider(){
	    	DaoAuthenticationProvider authenticattionProvider = new DaoAuthenticationProvider();
	    	authenticattionProvider.setUserDetailsService(customUserDetailsService);
	    	authenticattionProvider.setPasswordEncoder(passwordEncoder());
	    	
	    	return authenticattionProvider;
	    }
	    
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    	auth.parentAuthenticationManager(authenticationManagerBean()).userDetailsService(customUserDetailsService);
	        auth.authenticationProvider(authenticationProvider());
	    }
		
	}

}
