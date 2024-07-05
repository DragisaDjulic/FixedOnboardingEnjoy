package com.onboarding.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onboarding.model.PasswordTokenEntity;

public interface PasswordTokenRepository extends JpaRepository<PasswordTokenEntity, Long> {
	
	Optional<PasswordTokenEntity> findByToken(String token);
	
	public void deleteAllByUserEmail(String email);

}
