package com.tutorial.basic.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.tutorial.common.dto.UserDTO;

@FeignClient(contextId = "AuthCodeAuthClient", name="authenticationService", configuration=FeignConfiguration.class)
public interface AuthServiceFeign {
	
	@GetMapping("/api/allUsers")
	List <UserDTO> getAllUsers();

}