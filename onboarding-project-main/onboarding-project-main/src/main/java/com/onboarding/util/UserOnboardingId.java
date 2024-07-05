package com.onboarding.util;

import java.io.Serializable;

public class UserOnboardingId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private Long onboarding;
	
	public UserOnboardingId() {}
	
	public UserOnboardingId(Long onboarding, String user) {
		this.onboarding = onboarding;
		this.user = user;
	}
	
}