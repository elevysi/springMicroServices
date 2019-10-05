package com.tutorial.auth.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tutorial.common.dto.PostDTO;

@FeignClient(contextId = "clientFlowBasicClient", name="basicService", configuration=FeignClientConfiguration.class)
public interface BasicServiceFeignClientFlow {
	@GetMapping("/api/latestPosts")
	List <PostDTO> getLatestPosts();
}
