package com.onboarding.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.onboarding.model.NotificationEntity;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

	void deleteAllByUserRecever(String email);
	List<NotificationEntity> findByUserRecever(String userId);
	
}