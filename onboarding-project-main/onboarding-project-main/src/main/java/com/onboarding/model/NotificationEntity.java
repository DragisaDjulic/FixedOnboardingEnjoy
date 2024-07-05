package com.onboarding.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

	@Id
	@Column(columnDefinition = "serial")
	@SequenceGenerator(name = "notifications_id_seq", sequenceName = "notifications_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_id_seq")
	private long id;
	
	private String text;
	private Date date;
	private String title;
	private String userRecever;
	private Boolean isMentor;
	private Boolean opened;
	
	
	public NotificationEntity(long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public NotificationEntity(String text, Date date, String title, String userRecever, Boolean isMentor, Boolean opened) {
		this.text = text;
		this.date = date;
		this.title = title;
		this.userRecever = userRecever;
		this.isMentor = isMentor;
		this.opened = opened;
	}


	// public String getUserRecever() {
	// 	return userRecever;
	// }

	// public void setUserRecever(String userRecever){
	//     this.userRecever = userRecever;
	// }
	
	
//	public NotificationEntity() {}
//	
//	public NotificationEntity(long id, String text, Date date, String title, UserTask ut) {
//		this.id = id;
//		this.text = text;
//		this.date = date;
//		this.title = title;
//		this.userTask = ut;
//	}
//	
//	
//	public long getId() {
//		return this.id;
//	}
//
//	public String getText() {
//		return text;
//	}
//
//	public void setText(String txt) {
//		this.text = txt;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public Date getDate() {
//		return date;
//	}
//
//	public void setDate(Date d) {
//		this.date = d;
//	}
//
//	}
	
}
