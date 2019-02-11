package com.tutorial.basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tutorial.basic.model.Post;
import com.tutorial.basic.service.PostService;

@Controller
@RequestMapping(value="/ui/public")
public class PublicController {
	
	
	private PostService postService;
	
	@Autowired
	public PublicController(PostService postService){
		this.postService = postService;
	}
	
	@GetMapping("/posts")
	public String getPublicPosts(Model model){
		List<Post> posts = postService.getPosts();
		model.addAttribute("posts", posts);
		return "indexPosts";
	}

}
