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
public class TaskCreateDTO {

	private Long id;
	private String name;
	private String description;
	private Long onboarding;
	private Long parentTask;
	private String createdBy;
	private List<TaskCreateDTO> children = new ArrayList<TaskCreateDTO>();
	private List<LinkDTO> links = new ArrayList<LinkDTO>();
	private List<String> mentors = new ArrayList<String>();
	private Integer serialNo;

//	public TaskCreateDTO() {}
	
//	public Long getId() {
//		return id;
//	}
//	
//	public void setId(Long id) {
//		this.id = id;
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
//	
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
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
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	
//	//subtasks
//	public List<TaskCreateDTO> getChildren() {
//		return children;
//	}
//
//	public void setChildren(List<TaskCreateDTO> children) {
//		this.children = children;
//	}
	
	public void addChild(TaskCreateDTO task) {
		this.children.add(task);
	}
	
//	//links
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
	
//	//mentors
//	public List<String> getMentors() {
//		return mentors;
//	}
//
//	public void setMentors(List<String> mentors) {
//		this.mentors = mentors;
//	}
	
	public void addMentor(String mentor) {
		this.mentors.add(mentor);
	}

//	public Integer getSerialNo() {
//		return serialNo;
//	}
//
//	public void setSerialNo(Integer serialNo) {
//		this.serialNo = serialNo;
//	}
	
}
