package com.tutorial.auth.dao;

import java.util.List;

import com.tutorial.auth.model.User;

public interface UserDAO extends AbstractDAO<User, Long> {
	
	public User loadByUsername(String username);
	public List<User> getActiveUsers();

}
