package com.tutorial.basic.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.basic.dao.PostDAO;
import com.tutorial.basic.model.Post;

@Service
public class PostService extends AbstractServiceImplement<Post, Long> {
	
	private PostDAO postDAO;
	
	@Autowired
	public PostService(PostDAO postDAO){
		this.postDAO = postDAO;
	}

}
