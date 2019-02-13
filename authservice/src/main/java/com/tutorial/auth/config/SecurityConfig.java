package com.tutorial.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Hybrid Security Configuration

@Configuration
public class SecurityConfig {
	
	
	@Configuration
	public static class FormSecurityConfig extends WebSecurityConfigurerAdapter{
		

		
		@Autowired
	    private DataSource dataSource;
		
		
		@Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	        return bCryptPasswordEncoder;
	    }
		
		@Override
		protected void configure(HttpSecurity http) throws Exception{
			
			http.requestMatchers()
				.antMatchers("/" , "/ui/**", "/login")
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
	    protected void configure(AuthenticationManagerBuilder auth)
	            throws Exception {
	        auth.
	                jdbcAuthentication()
	                .usersByUsernameQuery("select username, password, active from users where username=?")
	                .authoritiesByUsernameQuery("select u.email, r.name from users u "
	                		+ "inner join user_groups ug on(u.id=ug.user_id) "
	                		+ "inner join group_roles gr on(ug.group_id=gr.id)"
	                		+ "inner join roles r on(gr.role_id=r.id) where u.username=?")
	                .dataSource(dataSource)
	                .passwordEncoder(passwordEncoder())
	                ;
	    }
		
	}

}
