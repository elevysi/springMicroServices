package com.tutorial.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tutorial.auth.dao.UserDAO;
import com.tutorial.auth.model.Group;
import com.tutorial.auth.model.User;

@Service
public class UserService extends AbstractServiceImplement<User, Long>{
	
	private static final Long USER_GROUP_ID = (long) 1;
	
	private GroupService groupService;
	private UserDAO userDAO;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	public UserService(
			GroupService groupService, 
			UserDAO userDAO,
			BCryptPasswordEncoder bCryptPasswordEncoder
	){
		this.groupService = groupService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDAO = userDAO;
	}
	
	public User registerUser(User user){
		user.setActive(true);
		//handle password
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		//Add the user to the group of users
		Group userGroup = groupService.findByID(USER_GROUP_ID);
		if(userGroup != null){
			user.getGroups().add(userGroup);
		}
		
		return userDAO.save(user);
	}
	
	
	public List<User> getActiveUsers(){
		return userDAO.getActiveUsers();
	}
	
}
