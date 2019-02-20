package com.tutorial.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tutorial.auth.service.ApiService;

@Controller
public class HomeController {

	private ApiService apiService;
	
	@Autowired
	public HomeController(ApiService apiService){
		this.apiService = apiService;
	}
	
	@GetMapping("/")
	public String home(Model model){
		
		return "home";
	}
}
