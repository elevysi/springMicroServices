package com.tutorial.auth.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tutorial.auth.config.UserPrincipal;

@Controller
@RequestMapping("/api")
public class ApiController {
	
	@GetMapping("/user")
	public @ResponseBody Map<String, Object> user(Principal userPrincipal){
		Map<String, Object> userInfo = new HashMap<String, Object>();
		
//		userInfo.put("email", userPrincipal.getEmail());
//		userInfo.put("authorities", userPrincipal.getAuthorities());
		userInfo.put("user", userPrincipal.getName());
		
		return userInfo;
	}

}
