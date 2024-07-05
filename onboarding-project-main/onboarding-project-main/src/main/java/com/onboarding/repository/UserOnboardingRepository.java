package com.onboarding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onboarding.model.OnboardingEntity;
import com.onboarding.model.UserEntity;
import com.onboarding.model.UserOnboarding;
import com.onboarding.util.UserOnboardingId;

@Repository
public interface UserOnboardingRepository extends JpaRepository<UserOnboarding, UserOnboardingId>{

	public List<UserOnboarding> findByOnboardingId(Long id);
	
	public List<UserOnboarding> findAllByUserEmail(String email);
	
	public void deleteAllByUserEmail(String email);

	public List<UserOnboarding> findByOnboarding(OnboardingEntity onbr);

	public UserOnboarding findByOnboardingIdAndUserEmail(Long onboardingId, String userId);

	public UserOnboarding findByUserAndOnboarding(UserEntity currentUser, OnboardingEntity currentOnboarding);
	
}
