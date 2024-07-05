package com.onboarding.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeOnboardingDTO extends OnboardingDTO {

	
	private List<EmployeeUserTaskDTO> tasks = new ArrayList<EmployeeUserTaskDTO>();
	//private Float percentage;
	private Boolean completed;
	private Date startDate;
	private Date endDate;
	private List<UserDTO> mentors = new ArrayList<UserDTO>();

	
//	public List<EmployeeUserTaskDTO> getTasks() {
//		return tasks;
//	}
//	
//	public void setTasks(List<EmployeeUserTaskDTO> tasks) {
//		this.tasks = tasks;
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
//	public Date getStartDate() {
//		return startDate;
//	}
//	
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//	
//	public Date getEndDate() {
//		return endDate;
//	}
//	
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
	
	public void addTask(EmployeeUserTaskDTO task) {
		this.tasks.add(task);
	}

//	public List<UserDTO> getMentors() {
//		return mentors;
//	}
//
//	public void setMentors(List<UserDTO> mentors) {
//		this.mentors = mentors;
//	}
	
	public void addMentor(UserDTO mentor) {
		this.mentors.add(mentor);
	}
}
