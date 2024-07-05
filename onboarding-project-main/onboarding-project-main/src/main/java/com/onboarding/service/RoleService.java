package com.onboarding.service;

import java.util.List;

import com.onboarding.DTO.RoleCreateDTO;
import com.onboarding.DTO.UserRoleDTO;

public interface RoleService {

	public UserRoleDTO addRole(RoleCreateDTO role);
	
	public List<UserRoleDTO> getRoles();
	
	public UserRoleDTO getRole(int id);
	
	public UserRoleDTO editRole(int id, UserRoleDTO role);
	
	public String deleteRole(int id);
	
}
