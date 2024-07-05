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
public class TaskDTO {

	private Long id;
	private String name;
	private String description;
	private String createdBy;
	private Long parentTask;
	private Long onboarding;
	private List<TaskDTO> children = new ArrayList<TaskDTO>();
	private List<TaskToUserDTO> users = new ArrayList<TaskToUserDTO>();
	private List<LinkDTO> links = new ArrayList<LinkDTO>();
	private Integer serialNo;
	
//	public TaskDTO() {}
	
	public TaskDTO(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
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
//	public List<TaskDTO> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<TaskDTO> children) {
//		this.children = children;
//	}
//
//	public List<TaskToUserDTO> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<TaskToUserDTO> users) {
//		this.users = users;
//	}
	
	public void addChild(TaskDTO task) {
		this.children.add(task);
	}
	
	public void addUser(TaskToUserDTO user) {
		this.users.add(user);
	}

//	public List<LinkDTO> getLinks() {
//		return links;
//	}
//
//	public void setLinks(List<LinkDTO> links) {
//		this.links = links;
//	}
	
	public void addLink(LinkDTO link) {
		this.links.add(link);
	}

//	public Integer getSerialNo() {
//		return serialNo;
//	}
//
//	public void setSerialNo(Integer serialNo) {
//		this.serialNo = serialNo;
//	}
	
}
