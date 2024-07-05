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
public class ModOnboardingDTO extends OnboardingDTO {
	
	private List<TaskDTO> tasks = new ArrayList<TaskDTO>();
	private List<OnboardingToUserDTO> users= new ArrayList<OnboardingToUserDTO>();
	//TODO SAMO USERONBOARDING
	//TODO GENERALNE INFO SVI USERI USER PROGRESS SVI TASKOVI

//	public List<OnboardingToUserDTO> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<OnboardingToUserDTO> users) {
//		this.users = users;
//	}
	
	public void addUser(OnboardingToUserDTO user) {
		this.users.add(user);
	}

//	public List<TaskDTO> getTasks() {
//		return tasks;
//	}
//
//	public void setTasks(List<TaskDTO> tasks) {
//		this.tasks = tasks;
//	}
	
	public void addTask(TaskDTO task) {
		this.tasks.add(task);
	}
}
