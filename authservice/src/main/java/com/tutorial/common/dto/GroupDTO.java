package com.tutorial.common.dto;

import java.util.HashSet;
import java.util.Set;

public class GroupDTO {
	
	private Long id;
	private String name;
	private Set<RoleDTO> roles = new HashSet<RoleDTO>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<RoleDTO> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDTO> roles) {
		this.roles = roles;
	}

}
