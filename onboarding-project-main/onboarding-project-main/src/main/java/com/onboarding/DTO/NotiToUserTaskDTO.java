package com.onboarding.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotiToUserTaskDTO {


		private UserDTO user;
		private NotiToTaskDTO task;
		private Boolean completed;
//		private Boolean isMentor;
//		private List<UserDTO> mentors = new ArrayList<UserDTO>();
		
//		public UserDTO getUser() {
//			return user;
//		}
//		
//		public void setUser(UserDTO user) {
//			this.user = user;
//		}
//
//		public NotiToTaskDTO getTask() {
//			return task;
//		}
//
//		public void setTask(NotiToTaskDTO task) {
//			this.task = task;
//		}
//
//		public boolean isCompleted() {
//			return completed;
//		}
//
//		public void setCompleted(boolean completed) {
//			this.completed = completed;
//		}

//		public Boolean getIsMentor() {
//			return isMentor;
//		}

//		public void setIsMentor(Boolean isMentor) {
//			this.isMentor = isMentor;
//		}

//		public List<UserDTO> getMentors() {
//			return mentors;
//		}
//
//		public void setMentors(List<UserDTO> mentors) {
//			this.mentors = mentors;
//		}
		
	
	
}
