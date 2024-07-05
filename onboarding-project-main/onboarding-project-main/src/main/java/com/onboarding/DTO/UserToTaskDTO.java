package com.onboarding.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserToTaskDTO {

	private String user;
	@JsonIgnore
	private TaskDTO task;
//	private Boolean canEdit;
//	private Boolean canAdd;
//	private Boolean canDelete;
	private boolean completed;
	
//	public TaskDTO getTask() {
//		return task;
//	}
//	
//	public void setTask(TaskDTO task) {
//		this.task = task;
//	}
//
//	public String getUser() {
//		return user;
//	}
//
//	public void setUser(String user) {
//		this.user = user;
//	}

//	public Boolean canEdit() {
//		return canEdit;
//	}
//
//	public void setCanEdit(Boolean canEdit) {
//		this.canEdit = canEdit;
//	}
//
//	public Boolean canAdd() {
//		return canAdd;
//	}
//
//	public void setCanAdd(Boolean canAdd) {
//		this.canAdd = canAdd;
//	}
//
//	public Boolean canDelete() {
//		return canDelete;
//	}
//
//	public void setCanDelete(Boolean canDelete) {
//		this.canDelete = canDelete;
//	}

//	public boolean isCompleted() {
//		return completed;
//	}
//
//	public void setCompleted(boolean completed) {
//		this.completed = completed;
//	}
	
}
