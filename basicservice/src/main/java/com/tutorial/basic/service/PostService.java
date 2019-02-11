package com.tutorial.basic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.basic.dao.PostDAO;
import com.tutorial.basic.model.Post;

@Service
public class PostService {
	
	private PostDAO postDAO;
	
	@Autowired
	public PostService(PostDAO postDAO){
		this.postDAO = postDAO;
	}
	
	public List<Post> getPosts(){
		return postDAO.getPosts();
	}
	
	public Post getPost(Long id){
		return postDAO.getPost(id);
	}
	public Post savePost(Post post){
		return postDAO.savePost(post);
	}
	
	public Post editPost(Post post){
		return postDAO.editPost(post);
	}
	
	public void deletePost(Post post){
		postDAO.deletePost(post);
	}

}
