package com.onboarding.service;

import java.util.List;

import com.onboarding.DTO.ResetPasswordDTO;
import com.onboarding.DTO.UserCreateDTO;
import com.onboarding.DTO.UserDTO;
import com.onboarding.DTO.UserEditDTO;
import com.onboarding.DTO.UserLoginDTO;


public interface UserService {
	
	public UserDTO addUser(UserCreateDTO user);
	
	public List<UserDTO> getUsers();
	
	
	public UserDTO editUser(String userId, UserEditDTO user);

	
	public String deleteUser(String userId);
	
	public UserDTO getUser(String id);
	
	public String loginUser(UserLoginDTO user) throws Exception;
	
	public String resetPassword(ResetPasswordDTO resetDTO);
	
}
