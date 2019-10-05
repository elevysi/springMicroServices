package com.tutorial.basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tutorial.basic.model.Post;
import com.tutorial.basic.service.ApiService;
import com.tutorial.basic.service.PostService;
import com.tutorial.common.dto.UserDTO;

@Controller
@RequestMapping(value="/ui/public")
public class PublicController {
	
	
	private PostService postService;
	private ApiService apiService;
	
	@Autowired
	public PublicController(PostService postService, ApiService apiService){
		this.postService = postService;
		this.apiService = apiService;
	}
	
	@GetMapping("/posts")
	public String getPublicPosts(Model model){
		List<Post> posts = postService.findAll();
		model.addAttribute("posts", posts);
		return "indexPosts";
	}
	
	@GetMapping("/activeUsers")
	public String getActiveUsers(Model model){
		List<UserDTO> users = apiService.getActiveUsers();
		model.addAttribute("users", users);
		return "users"; 
	}
}
