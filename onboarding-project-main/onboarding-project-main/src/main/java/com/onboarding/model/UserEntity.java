package com.onboarding.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
//	@Id
//	private long id;
	@Id
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String phone;
	private String avatar;

	@ManyToOne
	private UserRoleEntity role;
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<UserOnboarding> onboardings;
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<UserTask> tasks;
	
//	@ManyToMany
//	private List<TaskEntity> mentoringTasks = new ArrayList<TaskEntity>();

//	public UserEntity() {
//		
//	}
	
	public UserEntity(String firstName) {
		this.firstName = firstName;
	}
	
	public UserEntity(String email, String firstName, String lastName, String password, String phone){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}
		
	
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public UserRoleEntity getRole() {
//		return role;
//	}
//
//	public void setRole(UserRoleEntity role) {
//		this.role = role;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//	
//	public List<UserOnboarding> getOnboardings() {
//		return onboardings;
//	}
//
//	public void setOnboardings(List<UserOnboarding> onboardings) {
//		this.onboardings = onboardings;
//	}
//
//	public List<UserTask> getTasks() {
//		return tasks;
//	}
//
//	public void setTasks(List<UserTask> tasks) {
//		this.tasks = tasks;
//	}
//
//	public String getAvatar() {
//		return avatar;
//	}
//
//	public void setAvatar(String avatar) {
//		this.avatar = avatar;
//	}
//
//	public List<TaskEntity> getMentoringTasks() {
//		return mentoringTasks;
//	}
//	
//	public void setMentoringTasks(List<TaskEntity> tasks) {
//		this.mentoringTasks = tasks;
//	}
//	
//	public void addMentoringTask(TaskEntity task) {
//		this.mentoringTasks.add(task);
//	}
	
}
