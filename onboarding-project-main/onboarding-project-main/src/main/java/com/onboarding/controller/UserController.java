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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onboarding.DTO.ResetPasswordDTO;
import com.onboarding.DTO.UserCreateDTO;
import com.onboarding.DTO.UserDTO;
import com.onboarding.DTO.UserEditDTO;
import com.onboarding.DTO.UserLoginDTO;
import com.onboarding.service.UserService;
import com.onboarding.util.JwtTokenUtil;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@EnableSwagger2
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired 
	JwtTokenUtil jwtTokenUtil;


	@GetMapping()
//	@RolesAllowed({"ROLE_MOD", "ROLE_EMPLOYEE"})
	public List<UserDTO> getUsers(){
		return service.getUsers();

	}
	
	@GetMapping("/{id}")
	public UserDTO getUser(@PathVariable String id) {
		return service.getUser(id);
	}
	
	@PostMapping()
	public UserDTO addUser(@RequestBody UserCreateDTO user) {
		return service.addUser(user);
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestBody UserLoginDTO user) throws Exception {
		return service.loginUser(user);
	}
	
	@PutMapping("/{id}")
	public UserDTO editUser(@PathVariable String id, @RequestBody UserEditDTO user) {
		return service.editUser(id, user);
	}
	
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable String id) {
		return service.deleteUser(id);
	}
	
	@GetMapping("/getcurrent")
	public UserDTO getCurrent(@RequestHeader("Authorization") String auth) throws Exception {
		return service.getUser(jwtTokenUtil.getUsernameFromToken(auth.substring(7)));
	}
	
	@PostMapping("/reset")
	public String resetPassword(@RequestBody ResetPasswordDTO resetDTO) {
		return service.resetPassword(resetDTO);
	}
	
}
