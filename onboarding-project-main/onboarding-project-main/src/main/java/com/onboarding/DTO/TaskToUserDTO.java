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
public class TaskToUserDTO {

	private TaskUserDTO user;
	private Long task;
	private Boolean completed;
	private Boolean isMentor;
	private List<UserDTO> mentors = new ArrayList<UserDTO>();
	
//	public TaskUserDTO getUser() {
//		return user;
//	}
//	
//	public void setUser(TaskUserDTO user) {
//		this.user = user;
//	}
//
//	public Long getTask() {
//		return task;
//	}
//
//	public void setTask(Long task) {
//		this.task = task;
//	}
//
//	public boolean isCompleted() {
//		return completed;
//	}
//
//	public void setCompleted(boolean completed) {
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
//
//	public List<UserDTO> getMentors() {
//		return mentors;
//	}
//
//	public void setMentors(List<UserDTO> mentors) {
//		this.mentors = mentors;
//	}
	
}
