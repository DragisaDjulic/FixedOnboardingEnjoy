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
public class NotiToTaskDTO {
	
	private Long id;
	private String name;
	private String description;
	private String createdBy;
	private Long parentTask;
	private Long onboarding;
	private List<NotiToTaskDTO> children = new ArrayList<NotiToTaskDTO>();
	
//	public NotiToTaskDTO() {}
	
//	public Long getId() {
//		return id;
//	}
//	
//	public void setId(Long id) {
//		this.id = id;
//	}
//
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
//	public String getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public Long getParentTask() {
//		return parentTask;
//	}
//
//	public void setParentTask(Long parentTask) {
//		this.parentTask = parentTask;
//	}
//
//	public Long getOnboarding() {
//		return onboarding;
//	}
//
//	public void setOnboarding(Long onboarding) {
//		this.onboarding = onboarding;
//	}
//
//	public List<NotiToTaskDTO> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<NotiToTaskDTO> children) {
//		this.children = children;
//	}
	
	public void addChild(NotiToTaskDTO task) {
		this.children.add(task);
	}
	
}
