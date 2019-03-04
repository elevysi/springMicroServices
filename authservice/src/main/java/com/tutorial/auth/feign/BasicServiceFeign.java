package com.tutorial.auth.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tutorial.common.dto.PostDTO;

@FeignClient(name="basicservice", configuration=FeignConfiguration.class)
public interface BasicServiceFeign {
	
	@GetMapping("/api/latestPosts")
	List <PostDTO> getLatestPosts();

}