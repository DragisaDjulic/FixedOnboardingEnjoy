package com.onboarding.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
	
	private Long id;
	private String userRecever;
	private String text;
	private Date date;
	private String title;
	private Boolean isMentor;
	private Boolean opened;
	
//	public NotificationDTO() {}
	
	public NotificationDTO(long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public NotificationDTO(long id, String text, Date date, String title, String userRecever, Boolean isMentor, Boolean opened) {
		this.id = id;
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

//	public void setId(Long id) {
//		this.id = id;
//	}
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
//	public NotiToUserTaskDTO getUserTask() {
//		return userTask;
//	}
//
//	public void setUserTask(NotiToUserTaskDTO ut){
//	    this.userTask = ut;
//	}
}
