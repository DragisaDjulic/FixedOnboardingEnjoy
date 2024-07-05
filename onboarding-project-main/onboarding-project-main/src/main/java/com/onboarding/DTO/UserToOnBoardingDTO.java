package com.onboarding.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserToOnBoardingDTO {

		private String user;
//		@JsonIgnore
		private Long onboarding;
//		private Boolean canAddTask;
//		private Boolean canDeleteTask;
		private Boolean completed;
		private Boolean isMentor;
		private Date startDate;
		private Date endDate;
		
//		public String getUser() {
//			return this.user;
//		}
//		
//		public void setUser(String user) {
//			this.user = user;
//		}
//		
//		public Long getOnboarding() {
//			return this.onboarding;
//		}
//		
//		public void setOnboarding(Long onboarding) {
//			this.onboarding = onboarding;
//		}
		
//		public boolean canAddTask() {
//			return canAddTask;
//		}
//		
//		public void setCanAddTask(boolean canAddTask) {
//			this.canAddTask = canAddTask;
//		}
//		
//		public boolean canDeleteTask() {
//			return canDeleteTask;
//		}
//		
//		public void setCanDeleteTask(boolean canDeleteTask) {
//			this.canDeleteTask = canDeleteTask;
//		}
		
		
//		public Boolean completed() {
//			return completed;
//		}
//		
//		public void setCompleted(Boolean completed) {
//			this.completed = completed;
//		}
//
//		public Boolean getIsMentor() {
//			return isMentor;
//		}
//
//		public void setIsMentor(Boolean isMentor) {
//			this.isMentor = isMentor;
//		}
//
//		public Date getStartDate() {
//			return startDate;
//		}
//
//		public void setStartDate(Date startDate) {
//			this.startDate = startDate;
//		}
//
//		public Date getEndDate() {
//			return endDate;
//		}
//
//		public void setEndDate(Date endDate) {
//			this.endDate = endDate;
//		}
		
	}
