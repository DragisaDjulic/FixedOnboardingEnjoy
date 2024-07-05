package com.onboarding.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onboarding.model.OnboardingEntity;
import com.onboarding.model.TaskEntity;
import com.onboarding.model.UserOnboarding;
import com.onboarding.repository.OnboardingRepository;
import com.onboarding.repository.TaskRepository;
import com.onboarding.service.EmailSenderService;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/help")
public class EmailController {
	
	@Autowired
	private EmailSenderService service;

	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired
	private OnboardingRepository onRepo;
	
	@GetMapping()
	public void sendHelp(Long onbrId, Long taskID) throws Exception{
		
		TaskEntity task = taskRepo.findById(taskID).get();
		OnboardingEntity onbr = onRepo.findById(onbrId).get();
		
		List<UserOnboarding> users = onbr.getUsers();
		List<String> mentors = new ArrayList<String>();
		
		for(UserOnboarding user : users) {
			if(user.getIsMentor() == true) {
				mentors.add(user.getUser().getEmail());
			}
		}
		
		for(String mentor : mentors) {
			service.sendEmail(mentor, "Potrebna mi je pomoc sa taskom: " + task.getName(), "Nesto nije u redu sa upisom u bazu.");
		}
	}
	
}
