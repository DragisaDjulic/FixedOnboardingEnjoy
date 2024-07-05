package com.onboarding.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.onboarding.util.UserOnboardingId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users_onboardings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserOnboardingId.class)
public class UserOnboarding {

	@Id
	@ManyToOne
	private UserEntity user;
	@Id
	@ManyToOne
	private OnboardingEntity onboarding;
	//@Column(nullable = true)
	//private Float percentage;
	@Column(nullable = true)
	private Boolean completed;
	private Date startDate;
	private Date endDate;
	@ManyToOne
	private PermissionEntity permission;
	private Boolean isMentor;
	
	
//	public UserOnboarding() {
//		
//	}
	
	
	public UserOnboarding(UserEntity user, OnboardingEntity onboarding, Boolean isMentor, 
			 Boolean completed, PermissionEntity permission, Date startDate) {
		this.user = user;
		this.onboarding = onboarding;
		this.isMentor = isMentor;
		//this.percentage = percentage;
		this.completed = completed;
		this.permission = permission;
		this.startDate = startDate;
	}
	
	public UserOnboarding(UserEntity user, OnboardingEntity onboarding, Boolean isMentor, PermissionEntity permission) {
		this.user = user;
		this.onboarding = onboarding;
		this.isMentor = isMentor;
		//this.percentage = percentage;
		this.permission = permission;
	}
	
	@Override
	public String toString() {
		return (this.user.getEmail() + " " + this.onboarding.getName() + " " + this.completed);
	}
	
//	public PermissionEntity getPermission() {
//		return permission;
//	}
//
//	public void setPermission(PermissionEntity permission) {
//		this.permission = permission;
//	}

//	public float getPercentage() {
//		return percentage;
//	}
//	
//	public void setPercentage(float percentage) {
//		this.percentage = percentage;
//	}
	
//	public Boolean completed() {
//		return completed;
//	}
//	
//	public void setCompleted(Boolean completed) {
//		this.completed = completed;
//	}
//	
//	public OnboardingEntity getOnboarding() {
//		return this.onboarding;
//	}
//	public UserEntity getUser() {
//		return this.user;
//	}
//
//	public Date getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	public Date getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
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



//class UserOnboardingId implements Serializable {
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private String user;
//	private Long onboarding;
//	
//}



//TODO permission constraint based on role
//TODO completed & progress constraint








