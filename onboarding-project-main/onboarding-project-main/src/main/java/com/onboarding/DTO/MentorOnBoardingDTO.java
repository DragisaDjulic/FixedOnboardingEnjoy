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
public class MentorOnBoardingDTO {

	private String email;
	private List<String> actions = new ArrayList<String>();
	
//	MentorOnBoardingDTO(){}

//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	
//	public List<String> getActions() {
//		return actions;
//	}
//	
//	public void setActions(List<String> actions) {
//		this.actions = actions;
//	}
	
	public void addAction(String action) {
		this.actions.add(action);
	}
	
}
