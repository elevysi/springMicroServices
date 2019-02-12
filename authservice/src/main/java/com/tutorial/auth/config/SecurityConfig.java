package com.tutorial.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

//Hybrid Security Configuration

@Configuration
public class SecurityConfig {
	
	private static final String  RESOURCE_ID = "authservice";
	
	@Configuration
	public static class FormSecurityConfig extends WebSecurityConfigurerAdapter{
		
		
		private UserDetailsService userDetailsService;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			
			http.requestMatchers()
				.antMatchers("/" , "/ui/**", "/login")
				.and()
				.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/ui/public/**").permitAll()
//				.antMatchers("/ui/**").permitAll()
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
		
//		@Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService)
//	        .passwordEncoder(bCryptPasswordEncoder())
	        ;
	    }
		
	}

}
