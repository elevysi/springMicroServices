package com.tutorial.common.config;

import java.util.Collection;

import org.springframework.security.core.userdetails.User;


public class UserPrincipal extends User{
	
	private String email;
	
	public UserPrincipal(String username,
			String password,
			boolean enabled,
			boolean accountNonExpired,
			boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection authorities,
			String email)
	{
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
