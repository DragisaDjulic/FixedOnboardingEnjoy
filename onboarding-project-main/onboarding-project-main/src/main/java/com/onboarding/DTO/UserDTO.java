package com.onboarding.DTO;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
//	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private UserRoleDTO role;
	private String phone;
	private String avatar;
	private List<UserToOnBoardingDTO> onboardings = new ArrayList<UserToOnBoardingDTO>();
	private List<UserToTaskDTO> tasks = new ArrayList<UserToTaskDTO>();
	
//	public UserDTO() {}
	
//	public long getId() {
//		return this.id;
//	}
//	
//	public void setId(long i) {
//		this.id = i;
//	}

//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public UserRoleDTO getRole() {
//		return role;
//	}
//
//	public void setRole(UserRoleDTO role) {
//		this.role = role;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//	
//	public List<UserToOnBoardingDTO> getOnboardings() {
//		return onboardings;
//	}
//
//	public void setOnboarding(List<UserToOnBoardingDTO> onboardings) {
//		this.onboardings = onboardings;
//	}
//
//	public List<UserToTaskDTO> getTasks() {
//		return tasks;
//	}
//
//	public void setTasks(List<UserToTaskDTO> tasks) {
//		this.tasks = tasks;
//	}
	
	public void addOnboarding(UserToOnBoardingDTO onboarding) {
		this.onboardings.add(onboarding);
	}
	
	public void addTask(UserToTaskDTO task) {
	    this.tasks.add(task);
	}

//	public String getAvatar() {
//		return avatar;
//	}
//
//	public void setAvatar(String avatar) {
//		this.avatar = avatar;
//	}

	

	
}
