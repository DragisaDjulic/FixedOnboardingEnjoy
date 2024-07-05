package com.onboarding.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onboarding.model.UserEntity;
import com.onboarding.model.UserTask;
import com.onboarding.util.UserTaskId;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId>{
	
	public void deleteAllByUserEmail(String email);
	
	public List<UserTask> findByTaskId(Long ids);

	public List<UserTask> findByUser(UserEntity user);
	
	public List<UserTask> findByTaskIdInAndUserEmail(List<Long> taskIds, String userId);

	public UserTask findByTaskIdAndUserEmail(Long taskId, String email);
	
}