package com.onboarding.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "onboardings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingEntity {
	
	@Id
	@Column(columnDefinition = "serial")
	@SequenceGenerator(name = "onboardings_id_seq", sequenceName = "onboardings_id_seq", allocationSize = 1, initialValue = 500)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "onboardings_id_seq")
	private Long id;
	private String name;
	private String type;
	private String createdBy;
	private String description;
	@OneToMany(fetch = FetchType.EAGER, mappedBy="onboarding")
	private List<TaskEntity> tasks;
	@OneToMany(mappedBy="onboarding")
	private List<UserOnboarding> users;
	
	
	
//	public Long getId() {
//		return id;
//	}
//	
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public void setUsers(List<UserOnboarding> users) {
//		this.users = users;
//	}
	
	public void addUser(UserOnboarding user) {
		this.users.add(user);
	}

//	public OnboardingEntity() {
//		
//	}
	
	public OnboardingEntity(String name,String description, String createdBy) {
		this.name=name;
		this.description=description;
		this.createdBy = createdBy;
	}
	
	
	public OnboardingEntity(long id, String name, String type, String createdBy) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.createdBy = createdBy;
	}
	
//	public String getName() {
//		return this.name;
//	}
//	
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public String getCreatedBy() {
//		return createdBy;
//	}
//	
//	public List<TaskEntity> getTasks() {
//		return tasks;
//	}
//	
//	public void setTasks(List<TaskEntity> tasks) {
//		this.tasks = tasks;
//	}
	
	public void addTask(TaskEntity task) {
		this.tasks.add(task);
	}
	
	public void removeTask(long id) {
		this.tasks.removeIf(task -> task.getId() == id);
	}

//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	
//	public List<UserOnboarding> getUsers() {
//		return users;
//	}
	
	public TaskEntity getTask(Integer serialNo) {
		return tasks.stream().filter(task -> (task.getParentTask() == null && task.getSerialNo().equals(serialNo))).findFirst().orElse(null);
	}

}
