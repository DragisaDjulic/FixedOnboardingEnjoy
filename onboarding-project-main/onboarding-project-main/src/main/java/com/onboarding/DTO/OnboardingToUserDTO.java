package com.onboarding.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingToUserDTO {
	

	private UserDTO user;
	private Long onboarding;
	//private Float percentage;
	private PermissionDTO permission;
	private Boolean completed;
	private Boolean isMentor;
	
//	public PermissionDTO getPermission() {
//		return permissionOnBoarding;
//	}
//	
//	public void setPermission(PermissionDTO perm) {
//		this.permissionOnBoarding = perm;
//	}
//	
//	public UserDTO getUser() {
//		return user;
//	}
//	
//	public void setUser(UserDTO user) {
//		this.user = user;
//	}
//	
//	public Long getOnboarding() {
//		return onboarding;
//	}
//	
//	public void setOnboarding(Long onboarding) {
//		this.onboarding = onboarding;
//	}
	
//	public Float getPercentage() {
//		return percentage;
//	}
//	
//	public void setPercentage(Float percentage) {
//		this.percentage = percentage;
//	}
	
//	public Boolean getCompleted() {
//		return completed;
//	}
//	
//	public void setCompleted(Boolean completed) {
//		this.completed = completed;
//	}
//
//	public Boolean getIsMentor() {
//		return isMentor;
//	}
//
//	public void setIsMentor(Boolean isMentor) {
//		this.isMentor = isMentor;
//	}
	
}
