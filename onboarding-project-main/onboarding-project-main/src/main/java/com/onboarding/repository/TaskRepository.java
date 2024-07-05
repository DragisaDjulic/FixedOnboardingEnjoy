package com.onboarding.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.onboarding.model.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
	
	public List<TaskEntity> findByOnboardingId(Long onboardingId);

	public List<TaskEntity> findByParentTaskId(Long parentId);
	
	
}
