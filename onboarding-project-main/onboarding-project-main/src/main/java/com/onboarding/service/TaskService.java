package com.onboarding.service;

import java.util.List;
import com.onboarding.DTO.EmployeeOnboardingDTO;
import com.onboarding.DTO.TaskCreateDTO;
import com.onboarding.DTO.TaskDTO;
import com.onboarding.model.TaskEntity;

public interface TaskService {
	
	
	public TaskEntity addTask(TaskCreateDTO task, Long OBid);
	
	public List<TaskDTO> getTasks();
	
	public TaskDTO getTask(Long id);
	
	public TaskDTO editTask(Long id, TaskCreateDTO task);
	
	public String deleteTask(Long id);
	
	public EmployeeOnboardingDTO completeTask(Long id, String userId);
	
	public String sendHelp(Long id, String userName);
}


//TODO addtaskdto
//TODO edittaskdto
