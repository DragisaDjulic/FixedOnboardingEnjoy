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
public class OnboardingCreateDTO {
	
	
	private String name;
	private String description;
	private String createdBy;
	private List<MentorOnBoardingDTO> mentors= new ArrayList<MentorOnBoardingDTO>();
	
	
//	public String getCreatedBy() {
//		return createdBy;
//	}
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public List<MentorOnBoardingDTO> getMentors() {
//		return mentors;
//	}
//	public void setMentors(List<MentorOnBoardingDTO> mentors) {
//		this.mentors = mentors;
//	}
	
	
	
	
}
