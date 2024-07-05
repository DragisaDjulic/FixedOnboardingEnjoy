package com.onboarding.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "links")
@Setter
@Getter
public class LinkEntity {

	@Id
	private Long id;
	private String link;

//	@ManyToMany
//	private List<TaskEntity> tasks = new ArrayList<TaskEntity>();
//	
//	public Long getId() {
//		return id;
//	}
//	
//	public void setId(Long id) {
//		this.id = id;
//	}
//	
//	public String getLink() {
//		return link;
//	}
//	
//	public void setLink(String link) {
//		this.link = link;
//	}
	
}
