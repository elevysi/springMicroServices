package com.tutorial.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tutorial.auth.feign.BasicServiceFeign;
import com.tutorial.auth.feign.BasicServiceFeignClientFlow;
import com.tutorial.common.dto.PostDTO;

@Service
public class ApiService {
	
	private BasicServiceFeign basicServiceFeign;
	private BasicServiceFeignClientFlow basicServiceFeignClientFlow;

	@Autowired
	public ApiService(BasicServiceFeign basicServiceFeign, BasicServiceFeignClientFlow basicServiceFeignClientFlow){
		this.basicServiceFeign = basicServiceFeign;
		this.basicServiceFeignClientFlow = basicServiceFeignClientFlow;
	}
	
	@HystrixCommand(fallbackMethod="getPostsFallBack")
	public List<PostDTO> getLatestPosts(){
		return basicServiceFeign.getLatestPosts();
	}
	
	@HystrixCommand(fallbackMethod="getPostsFallBack")
	public List<PostDTO> getPostsClientFlow(){
		return basicServiceFeignClientFlow.getLatestPosts();
	}
	
	
	public List<PostDTO> getPostsFallBack(){
		return new ArrayList<PostDTO>();
	}
	
}
