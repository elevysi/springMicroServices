package com.tutorial.basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tutorial.basic.model.Post;
import com.tutorial.basic.service.PostService;

@Controller
@RequestMapping(value="/api")
public class ApiController{
	
	private PostService postService;
	
	@Autowired
	public ApiController(PostService postService){
		this.postService = postService;
	}
	
	@GetMapping("/latestPosts")
	public @ResponseBody List<Post> getLatestPosts(){
		return postService.findAll();
	}

}