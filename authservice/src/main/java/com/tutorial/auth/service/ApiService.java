package com.tutorial.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.auth.feign.BasicServiceFeign;
import com.tutorial.common.dto.PostDTO;

@Service
public class ApiService {
	
	private BasicServiceFeign basicServiceFeign;

	@Autowired
	public ApiService(BasicServiceFeign basicServiceFeign){
		this.basicServiceFeign = basicServiceFeign;
	}
	

	public List<PostDTO> getLatestPosts(){
		return basicServiceFeign.getLatestPosts();
	}
}
