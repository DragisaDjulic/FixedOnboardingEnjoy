package com.onboarding.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

	private String token;
	private UserDTO user;
	
//	public LoginDTO() {}
//	
//	public LoginDTO(String token, UserDTO user) {
//		this.token = token;
//		this.user = user;
//	}
//	
//	public String getToken() {
//		return token;
//	}
//	
//	public void setToken(String token) {
//		this.token = token;
//	}
//	
//	public UserDTO getUser() {
//		return user;
//	}
//	
//	public void setUser(UserDTO user) {
//		this.user = user;
//	}
	
	
	
}
