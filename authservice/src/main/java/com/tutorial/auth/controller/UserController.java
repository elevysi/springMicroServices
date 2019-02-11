package com.tutorial.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tutorial.auth.model.User;
import com.tutorial.auth.service.UserService;

@Controller
@RequestMapping("/ui/users")
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService){
		this.userService = userService;
	}
	
	@GetMapping("/add")
	public String addUser(Model model){
		User user = new User();
		model.addAttribute("user", user);
		return "addUser";
	}
	
	@PostMapping("/add")
	public String doAddUser(Model model, @Valid User user, BindingResult result){
		if(result.hasErrors()){
			return "addUser";
		}
		
		userService.save(user);
		return "redirect:/ui/users/";
	}
	
	@GetMapping("/")
	public String users(Model model){
		model.addAttribute("users", userService.findAll());
		return "usersIndex";
	}
	

}
