package com.onboarding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onboarding.model.OnboardingEntity;
import com.onboarding.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

	List<UserEntity> findAllByOnboardings(OnboardingEntity currentOnboarding);
	UserEntity findByRoleName(String roleName);
	List<UserEntity> findAllByRoleName(String string);
	
//	public List<UserEntity> findByUserIdIn(List<String> userIds);
	
}