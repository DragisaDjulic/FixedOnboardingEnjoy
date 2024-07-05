package com.onboarding.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {
	
	@Id
	@Column(columnDefinition = "serial")
	@SequenceGenerator(name = "user_roles_id_seq", sequenceName = "user_roles_id_seq", allocationSize = 1, initialValue = 500)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_roles_id_seq")
	private int id;
	private String name;
	@OneToOne
	private PermissionEntity permission;
	
//	public UserRoleEntity() {}
	
	public UserRoleEntity(String name, PermissionEntity permission) {
		this.name = name;
		this.permission = permission;
	}
	
	public UserRoleEntity(String name) {
		this.name = name;
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
//	public PermissionEntity getPermission() {
//		return this.permission;
//	}
//	
//	public void setPermission(PermissionEntity permission) {
//		this.permission = permission;
//	}

}
