package com.onboarding.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.onboarding.DTO.RoleCreateDTO;
import com.onboarding.DTO.UserRoleDTO;
import com.onboarding.model.UserRoleEntity;
import com.onboarding.repository.PermissionRepository;
import com.onboarding.repository.RoleRepository;
import com.onboarding.service.RoleService;
import com.onboarding.util.ApiException;
import com.onboarding.util.DTOConversions;

@Service
public class RoleServiceImplementation implements RoleService{
	
	@Autowired
	private RoleRepository repo;
	
	@Autowired
	private PermissionRepository permRepo;

	@Override
	public UserRoleDTO addRole(RoleCreateDTO role) {
		UserRoleEntity roleEnt = DTOConversions.roleCreateDTOToRole(role);
		roleEnt.setPermission((permRepo.findById(role.getPermission())).get());
		return DTOConversions.roleToDTO(repo.save(roleEnt));
		
//		return DTOConversions.roleToDTO(repo.save(DTOConversions.roleDTOToRole(role)));
	}

	@Override
	public List<UserRoleDTO> getRoles() {
		List<UserRoleDTO> roleDTOs = new ArrayList<UserRoleDTO>();
		for(UserRoleEntity role : repo.findAll()) {
			roleDTOs.add(DTOConversions.roleToDTO(role));
		}
		return roleDTOs;
	}

	@Override
	public UserRoleDTO getRole(int id) {
		return null;
		// ?????
	}

	@Override
	public UserRoleDTO editRole(int id, UserRoleDTO role) {
		return null;
		// ?????
	}

	@Override
	public String deleteRole(int id) {
		UserRoleEntity role = repo.findById(id).orElseThrow(() -> new ApiException("Role not found!", HttpStatus.NOT_FOUND.value()));
		String msg;
		if(role != null) {
			msg = "Role removed";
			repo.deleteById(id);
		}else {
			msg = "Role not found";
		}
		return msg;
	}

}
