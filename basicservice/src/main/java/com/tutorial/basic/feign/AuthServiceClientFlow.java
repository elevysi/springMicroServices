package com.tutorial.basic.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tutorial.common.dto.UserDTO;

@FeignClient(contextId = "clientFlowAuthClient", name="authenticationService", configuration=FeignClientFlowConfiguration.class)
public interface AuthServiceClientFlow {
	
	@GetMapping("/api/activeUsers")
	List <UserDTO> getActiveUsers();
}
