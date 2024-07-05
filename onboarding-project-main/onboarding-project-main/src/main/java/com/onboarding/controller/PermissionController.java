package com.onboarding.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onboarding.DTO.PermissionDTO;
import com.onboarding.service.PermissionService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@EnableSwagger2
@RequestMapping("/permissions")
public class PermissionController {
	
	@Autowired
	private PermissionService service;

	@GetMapping()
	public List<PermissionDTO> getPermissions() {
		return service.getPermissions();
	}
	
	@PostMapping()
	public PermissionDTO addPermission(@RequestBody PermissionDTO permission) {
		return service.addPermission(permission);
	}

}
