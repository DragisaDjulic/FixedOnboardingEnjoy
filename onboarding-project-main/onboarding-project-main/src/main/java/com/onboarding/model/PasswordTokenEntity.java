package com.onboarding.model;

import java.util.Date;
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
@Table(name = "password_token")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordTokenEntity {
	
	private static final int EXP = 60 * 24;
	
	@Id
	@Column(columnDefinition = "serial")
	@SequenceGenerator(name = "onboardings_id_seq", sequenceName = "onboardings_id_seq", allocationSize = 1, initialValue = 500)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "onboardings_id_seq")
	private Long id;
	private String token;
	@OneToOne
	private UserEntity user;
	private Date expired;
	
//	public PasswordTokenEntity() {}

	public PasswordTokenEntity(String token, UserEntity user) {
		this.token = token;
		this.user = user;
	}
	
	public PasswordTokenEntity(String token, UserEntity user, Date expired) {
		this.token = token;
		this.user = user;
		this.expired = expired;
	}
	
//	public Long getId() {
//		return id;
//	}
//	
//	public void setId(Long id) {
//		this.id = id;
//	}
//	
//	public String getToken() {
//		return token;
//	}
//	
//	public void setToken(String token) {
//		this.token = token;
//	}
//	
//	public UserEntity getUser() {
//		return user;
//	}
//	
//	public void setUser(UserEntity user) {
//		this.user = user;
//	}
//	
//	public Date getExpired() {
//		return expired;
//	}
//	
//	public void setExpired(Date expired) {
//		this.expired = expired;
//	}

}
