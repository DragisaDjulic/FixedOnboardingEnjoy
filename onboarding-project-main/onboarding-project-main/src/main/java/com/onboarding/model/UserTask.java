package com.onboarding.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.onboarding.util.UserTaskId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users_tasks")
@IdClass(UserTaskId.class)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserTask {
	
	@Id
	@ManyToOne
	private UserEntity user;
	@Id
	@ManyToOne
	private TaskEntity task;
//	@Column(columnDefinition = "BOOLEAN CHECK (CASE WHEN is_mentor = true AND completed IS NOT NULL THEN 0 ELSE 1)")
	private Boolean completed;
	@ManyToOne
//	@Column(columnDefinition = "INTEGER CHECK (CASE WHEN is_mentor = false AND permission IS NOT NULL THEN 1 ELSE 0)")
	private PermissionEntity permission;
//	@Column(nullable = false)
	private Boolean isMentor;
//	@ManyToMany
//	private List<UserEntity> mentors = new ArrayList<UserEntity>();
	
	public UserTask(UserEntity user, TaskEntity task, PermissionEntity permisson, boolean isMentor, Boolean completed) {
		this.user = user;
		this.task = task;
		this.permission = permisson;
		this.isMentor = isMentor;
		this.completed = completed;
	}
	
//	public UserTask() {}
	
	public UserTask(Boolean completed) {
		this.completed = completed;
	}
	
//	public PermissionEntity getPermission() {
//		return permission;
//	}
//
//	public void setPermission(PermissionEntity permission) {
//		this.permission = permission;
//	}
//
//	public Boolean completed() {
//		return this.completed;
//	}
//	
//	public void setCompleted(Boolean completed) {
//		this.completed = completed;
//	}
//	
//	public UserEntity getUser() {
//		return this.user;
//	}
//	
//	public TaskEntity getTask() {
//		return this.task;
//	}
//
//	public Boolean getIsMentor() {
//		return isMentor;
//	}
//
//	public void setIsMentor(Boolean isMentor) {
//		this.isMentor = isMentor;
//	}

}
