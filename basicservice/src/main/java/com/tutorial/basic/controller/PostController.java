package com.tutorial.basic.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tutorial.basic.model.Post;
import com.tutorial.basic.service.PostService;

@Controller
@RequestMapping(value="/ui/posts/")
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService){
		this.postService = postService;
	}
	
	@GetMapping(value="/")
	public String posts(Model model){
		
		model.addAttribute("posts", postService.getPosts());
		return "indexPosts";
	}
	
	
	@GetMapping(value="/add")
	public String addPost(Model model){
		Post post = new Post();
		model.addAttribute("post", post);
		
		return "addPost";
	}
	
	@PostMapping(value="/add")
	public String doAddPost(@Valid Post post, BindingResult result, Model model){
		
		if(result.hasErrors()){
			return "addPost";
		}
		
		postService.savePost(post);
		return "redirect:/ui/posts/";
	}
	
	
	@GetMapping(value="/edit/{id}")
	public String editPost(Model model, @PathVariable("id")Long id){
		Post post = postService.getPost(id);
		if(post != null){
			model.addAttribute("post", post);
			return "editPost";
		}
		
		return "redirect:/ui/posts/";
	}
	
	@PostMapping(value="/edit/{id}")
	public String doEditPost(@Valid Post post, BindingResult result, Model model, @PathVariable("id")Long id){
		
		if(result.hasErrors()){
			return "editPost";
		}
		
		postService.editPost(post);
		return "redirect:/ui/posts/";
	}
	
	
	@GetMapping(value="/delete/{id}")
	public String deletePost(Model model, @PathVariable("id")Long id){
		Post post = postService.getPost(id);
		if(post != null){
			postService.deletePost(post);
		}
		return "redirect:/ui/posts/";
	}
	
	

}
