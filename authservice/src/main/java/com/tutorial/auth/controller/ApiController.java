package com.tutorial.auth.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tutorial.auth.model.User;
import com.tutorial.auth.service.UserService;
import com.tutorial.common.UserPrincipal;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	private UserService userService;
	
	@Autowired
	public ApiController(UserService userService){
		this.userService = userService;
	}
	
	@GetMapping("/user")
	public @ResponseBody Map<String, Object> user(Principal userPrincipal){
		Map<String, Object> userInfo = new HashMap<String, Object>();
		
//		userInfo.put("email", userPrincipal.getEmail());
//		userInfo.put("authorities", userPrincipal.getAuthorities());
		userInfo.put("user", userPrincipal.getName());
		
		return userInfo;
	}
	
	@GetMapping("/activeUsers")
	public @ResponseBody List<User> getPublicUsers(){
		return userService.getActiveUsers();
	}
	
	
	@GetMapping("/allUsers")
	public @ResponseBody List<User> getAllUsers(){
		return userService.findAll();
	}

}
