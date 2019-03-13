package com.tutorial.basic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.basic.feign.AuthServiceClientFlow;
import com.tutorial.basic.feign.AuthServiceFeign;
import com.tutorial.common.dto.UserDTO;

@Service
public class ApiService {
	
	private AuthServiceFeign authServiceFeign;
	private AuthServiceClientFlow authServiceClientFlow;

	@Autowired
	public ApiService(AuthServiceFeign authServiceFeign, AuthServiceClientFlow authServiceClientFlow){
		this.authServiceFeign = authServiceFeign;
		this.authServiceClientFlow = authServiceClientFlow;
	}

	public List<UserDTO> getAllUsers(){
		return authServiceFeign.getAllUsers();
	}
	
	public List<UserDTO> getActiveUsers(){
		return authServiceClientFlow.getActiveUsers();
	}
}