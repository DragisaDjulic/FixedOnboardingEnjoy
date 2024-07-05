package com.onboarding.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class OnboardingDTO {

	
	private Long id;
	private String name;
	private String type;
	private String createdBy;
	private String description;
	private Boolean isMentor;
	
//	public Long getId() {
//		return id;
//	}
//	
//	public void setId(Long id) {
//		this.id = id;
//	}
//	
//	public String getName() {
//		return name;
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
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//	
//	public String getDescription() {
//		return description;
//	}
//	
//	public void setDescription(String description) {
//		this.description = description;
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
