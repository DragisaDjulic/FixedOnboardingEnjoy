package com.onboarding.service;

import java.util.List;

import com.onboarding.DTO.PermissionDTO;

public interface PermissionService {

	
	public List<PermissionDTO> getPermissions();
	public PermissionDTO addPermission(PermissionDTO permission);
	
}
