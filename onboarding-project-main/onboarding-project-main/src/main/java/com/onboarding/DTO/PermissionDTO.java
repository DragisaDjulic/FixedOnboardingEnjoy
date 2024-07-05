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
public class PermissionDTO {

	private int id;
	private String name;
	private List<String> actions = new ArrayList<String>();
	
//	public PermissionDTO() {}
	
	public PermissionDTO(List<String> actions) {
		this.actions = actions;
	}
	
//	public int getId() {
//		return id;
//	}
//	
//	public void setId(int id) {
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
