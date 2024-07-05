package com.onboarding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onboarding.model.OnboardingEntity;

@Repository
public interface OnboardingRepository extends JpaRepository<OnboardingEntity, Long> {

}
