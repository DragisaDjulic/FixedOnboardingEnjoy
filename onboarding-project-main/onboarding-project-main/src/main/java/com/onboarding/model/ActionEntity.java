package com.onboarding.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActionEntity {
	
	@Id
	private int id;
	
	private String type;
	// ne treba
//	@ManyToMany
//	private List<ActionEntity> actions = new ArrayList<ActionEntity>();
	
	
	
	
	
//	public ActionEntity() {}
//	
	public ActionEntity(String type) {
		this.type = type;
	}
//	
//	public int getId() {
//		return id;
//	}
//	
//	public String getType() {
//		return type;
//	}
//	
//	public void setType(String type) {
//		this.type = type;
//	}

}
