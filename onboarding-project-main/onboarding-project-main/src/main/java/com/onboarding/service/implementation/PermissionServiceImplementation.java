package com.onboarding.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onboarding.DTO.PermissionDTO;
import com.onboarding.model.PermissionEntity;
import com.onboarding.repository.ActionRepository;
import com.onboarding.repository.PermissionRepository;
import com.onboarding.service.PermissionService;
import com.onboarding.util.DTOConversions;

@Service
public class PermissionServiceImplementation implements PermissionService {
	
	@Autowired
	private PermissionRepository repo;
	@Autowired
	private ActionRepository actionRepo;

	@Override
	public List<PermissionDTO> getPermissions() {
		List<PermissionDTO> permDTOs = new ArrayList<PermissionDTO>();
		for(PermissionEntity perm : repo.findAll()) {
			permDTOs.add(DTOConversions.permissionToDTO(perm));
		}
		return permDTOs;
	}

	@Override
	public PermissionDTO addPermission(PermissionDTO permission) {
		PermissionEntity permEnt = DTOConversions.permissionDTOToPerm(permission);
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
//		permEnt.setActions(actionRepo.findAllById(ids));
		permEnt.setActions(actionRepo.findByTypeIn(permission.getActions()));
		return DTOConversions.permissionToDTO(repo.save(permEnt));
	}

}
