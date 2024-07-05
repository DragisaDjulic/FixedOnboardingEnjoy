package com.onboarding.service;

import java.util.List;
import com.onboarding.DTO.OnboardingCreateDTO;
import com.onboarding.DTO.OnboardingDTO;
import com.onboarding.DTO.ModOnboardingDTO;
import com.onboarding.DTO.TaskCreateDTO;

public interface OnboardingService {
	
	
	public ModOnboardingDTO addOnboarding(String userId, OnboardingCreateDTO onboarding);
	
	public List<? extends OnboardingDTO> getOnboardings(String userId);
	
	public ModOnboardingDTO getOnboarding(long id);
	
	public ModOnboardingDTO editOnboarding(long id , OnboardingCreateDTO onboarding);
	
	public String deleteOnboarding(long id);
	
	public ModOnboardingDTO addUsersToOnboarding(String auth, Long id, List<String> userIds);
	
	public ModOnboardingDTO addTasksToOnboarding(String email, Long id, List<TaskCreateDTO> tasks);

	public List<? extends OnboardingDTO> getOnboardingsForUser(String userId);
	
	
}
