package com.tutorial.basic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.basic.feign.AuthServiceFeign;
import com.tutorial.common.dto.UserDTO;

@Service
public class ApiService {
	
	private AuthServiceFeign authServiceFeign;

	@Autowired
	public ApiService(AuthServiceFeign authServiceFeign){
		this.authServiceFeign = authServiceFeign;
	}

	public List<UserDTO> getUsers(){
		return authServiceFeign.getUsers();
	}
}
