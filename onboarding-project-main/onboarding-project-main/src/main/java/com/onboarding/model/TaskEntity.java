package com.onboarding.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
	
	@Id
	@Column(columnDefinition = "serial")
	@SequenceGenerator(name = "tasks_id_seq", sequenceName = "tasks_id_seq", allocationSize = 1, initialValue = 20)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_id_seq")
	private Long id;
	private String name;
	private String description;
	private String createdBy;
	@OneToMany(mappedBy="parentTask")
	private List<TaskEntity> children = new ArrayList<TaskEntity>();
	@ManyToOne
	private TaskEntity parentTask;
	@ManyToOne
	private OnboardingEntity onboarding;
	@OneToMany(mappedBy="task")
	private List<UserTask> users = new ArrayList<UserTask>();
	private Integer serialNo;
	
//	@ManyToMany(mappedBy = "mentoringTasks")
//	private List<UserEntity> mentors = new ArrayList<UserEntity>();
	
	@ManyToMany
	private List<LinkEntity> links = new ArrayList<LinkEntity>();
	
//	public TaskEntity() {}
	
	public TaskEntity(List<TaskEntity> children) {
		this.children = children;
	}
	
	public TaskEntity(Long id, String name){
		this.id = id;
		this.name = name;
	}
	
	public TaskEntity(Long id, String name, String description, String createdBy) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdBy = createdBy;
	}
	
	public TaskEntity(String description, TaskEntity parentTask, OnboardingEntity onboarding) {
		this.description = description;
		this.parentTask = parentTask;
		this.onboarding = onboarding;
	}
	
	public TaskEntity(String description, TaskEntity parentTask, String createdBy) {
		this.description = description;
		this.parentTask = parentTask;
		this.createdBy = createdBy;
	}
	
	public TaskEntity(Long id, String name, String description, String createdBy, 
			List<TaskEntity> children, TaskEntity parentTask, OnboardingEntity onboarding, List<UserTask> users) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.createdBy = createdBy;
		this.children = children;
		this.parentTask = parentTask;
		this.onboarding = onboarding;
		this.users = users;
	}
	
//	public String getName() {
//		return this.name;
//	}
//	
//	public Long getId() {
//		return this.id;
//	}
//	
//	public void setId(Long id) {
//		this.id = id;
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
//	public TaskEntity getParentTask() {
//		return parentTask;
//	}
//
//	public void setParentTask(TaskEntity parentTask) {
//		this.parentTask = parentTask;
//	}
//
//	public OnboardingEntity getOnboarding() {
//		return onboarding;
//	}
//
//	public void setOnboarding(OnboardingEntity onboarding) {
//		this.onboarding = onboarding;
//	}
//	
//	public List<TaskEntity> getChildren() {
//		return this.children;
//	}
//	
//	public void setChildren(List<TaskEntity> children) {
//		this.children = children;
//	}
//	
//	public List<UserTask> getUsers() {
//		return this.users;
//	}
//	
//	public void setUsers(List<UserTask> users) {
//		this.users = users;
//	}
	
	public void addUser(UserTask task) {
		this.users.add(task);
	}
	
	public void addChild(TaskEntity task) {
		this.children.add(task);
	}
	
	public void removeChild(Long id) {
		this.children.removeIf(task -> task.id == id);
	}
	
//	public List<LinkEntity> getLinks() {
//		return links;
//	}
	
//	public List<UserEntity> getMentors() {
//		return mentors;
//	}
//	
//	public void setMentors(List<UserEntity> mentors) {
//		this.mentors = mentors;
//	}
//	
//	public void addMentor(UserEntity mentor) {
//		this.mentors.add(mentor);
//	}
	
//	public void setLinks(List<LinkEntity> links) {
//		this.links = links;
//	}
	
	public void addLink(LinkEntity link) {
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
