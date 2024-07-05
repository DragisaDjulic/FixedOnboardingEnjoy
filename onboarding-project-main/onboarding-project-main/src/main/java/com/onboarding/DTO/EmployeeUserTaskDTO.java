

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
public class EmployeeUserTaskDTO {

	private Long task;
	private Long onboarding;
	private String name;
	private String description;
	private Boolean completed;
	private Boolean isMentor;
	private List<UserDTO> mentors = new ArrayList<UserDTO>();
	private List<EmployeeUserTaskDTO> children = new ArrayList<EmployeeUserTaskDTO>();
	private Integer serialNo;
	
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
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
//	
//	public List<UserDTO> getMentors() {
//		return mentors;
//	}
//	
//	public void setMentors(List<UserDTO> mentors) {
//		this.mentors = mentors;
//	}
//	
//	public List<EmployeeUserTaskDTO> getChildren() {
//		return children;
//	}
//	
//	public void setChildren(List<EmployeeUserTaskDTO> children) {
//		this.children = children;
//	}
	
	public void addMentor(UserDTO mentor) {
		this.mentors.add(mentor);
	}
	
	public void addChild(EmployeeUserTaskDTO child) {
		this.children.add(child);
	}

//	public Long getOnboarding() {
//		return onboarding;
//	}
//
//	public void setOnboarding(Long onboarding) {
//		this.onboarding = onboarding;
//	}
//
//	public Integer getSerialNo() {
//		return serialNo;
//	}
//
//	public void setSerialNo(Integer serialNo) {
//		this.serialNo = serialNo;
//	}
}
