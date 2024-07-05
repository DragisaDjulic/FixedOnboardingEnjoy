package com.onboarding.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity {
	
	@Id
	@Column(columnDefinition = "serial")
	@SequenceGenerator(name = "permissions_id_seq", sequenceName = "permissions_id_seq", allocationSize = 1, initialValue = 500)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_id_seq")
	private int id;
	private String name;
	@ManyToMany
	private List<ActionEntity> actions = new ArrayList<ActionEntity>();
	
//	public PermissionEntity() {}
	
	public PermissionEntity(List<ActionEntity> actions) {
		this.actions = actions;
	}
	
	public PermissionEntity(String name, List<ActionEntity> actions) {
		this.name = name;
		this.actions = actions;
	}
	
//	public int getId() {
//		return id;
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
//	public List<ActionEntity> getActions() {
//		return actions;
//	}
//
//	public void setActions(List<ActionEntity> actions) {
//		this.actions = actions;
//	}

}
