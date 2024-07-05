package com.onboarding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onboarding.DTO.RoleCreateDTO;
import com.onboarding.DTO.UserRoleDTO;
import com.onboarding.service.RoleService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@EnableSwagger2
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	private RoleService service;

	@GetMapping()
	public List<UserRoleDTO> getRoles() {
		return service.getRoles();
	}
	
	@PostMapping()
	public UserRoleDTO addRole(@RequestBody RoleCreateDTO role) {
		return service.addRole(role);
	}
	
	@PutMapping("/{id}")
	public UserRoleDTO editRole(@PathVariable int id, @RequestBody UserRoleDTO role) {
		return service.editRole(id, role);
	}
	
	@DeleteMapping("/{id}")
	public String deleteRole(@PathVariable int id) {
		return service.deleteRole(id);
	}
	
	
	
}
