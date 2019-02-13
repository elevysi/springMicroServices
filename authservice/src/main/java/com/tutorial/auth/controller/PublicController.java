package com.tutorial.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tutorial.auth.service.UserService;

@Controller
@RequestMapping("/ui/public")
public class PublicController {
	
	private UserService userService;
	
	@Autowired
	public PublicController(UserService userService){
		this.userService = userService;
	}

	@GetMapping("/users")
	public String users(Model model){
		model.addAttribute("users", userService.findAll());
		return "usersIndex";
	}
}
