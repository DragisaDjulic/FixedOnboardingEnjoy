package com.onboarding.util;

import java.io.Serializable;

public class UserTaskId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private Long task;
	
	public UserTaskId() {}
	
	public UserTaskId(Long task, String user) {
		this.task = task;
		this.user = user;
	}
	
	
}