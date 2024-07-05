package com.onboarding.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onboarding.DTO.OnboardingCreateDTO;
import com.onboarding.DTO.OnboardingDTO;
import com.onboarding.DTO.ModOnboardingDTO;
import com.onboarding.DTO.TaskCreateDTO;
import com.onboarding.service.OnboardingService;
import com.onboarding.util.JwtTokenUtil;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@EnableSwagger2
@RequestMapping("/onboardings")
public class OnboardingController {
	
	@Autowired
	OnboardingService service;
	
	@Autowired 
	JwtTokenUtil jwtTokenUtil;
	
	
	@GetMapping()
	public List<? extends OnboardingDTO> getOnboardings(@RequestHeader("Authorization") String auth) throws Exception {
		return service.getOnboardings(jwtTokenUtil.getUsernameFromToken(auth.substring(7)));
	}
	
	@GetMapping("/user/{id}")
	public List<? extends OnboardingDTO> getOnboardingsForUser(@PathVariable String id) {
		return service.getOnboardingsForUser(id);
	}
	
	@GetMapping("/{id}")
	public ModOnboardingDTO getOnboarding(@PathVariable Long id) {
		return service.getOnboarding(id);
	}
	
	@PostMapping()
	public ModOnboardingDTO addOnboarding(@RequestHeader("Authorization") String auth, @RequestBody OnboardingCreateDTO onboarding) throws Exception {
		return service.addOnboarding(jwtTokenUtil.getUsernameFromToken(auth.substring(7)), onboarding);
	}
	
	@PutMapping("/{id}") 
	public ModOnboardingDTO editOnboarding(@PathVariable Long id, @RequestBody OnboardingCreateDTO onboarding) {
		return service.editOnboarding(id, onboarding);
	}
	
	@DeleteMapping("/{id}")
	public String deleteOnboarding(@PathVariable long id) {
		return service.deleteOnboarding(id);
	}
	
	@PostMapping("/{id}/addusers")
	public ModOnboardingDTO addUsersToOnboarding(@RequestHeader("Authorization") String auth, @PathVariable Long id, @RequestBody List<String> userIds) throws Exception {
		return service.addUsersToOnboarding(jwtTokenUtil.getUsernameFromToken(auth.substring(7)), id, userIds);
	}
	
	@PostMapping("/{id}/addtasks")
	public ModOnboardingDTO addTasksToOnboarding(@RequestHeader("Authorization") String auth, @PathVariable Long id, @RequestBody List<TaskCreateDTO> tasks) throws Exception {
		return service.addTasksToOnboarding(jwtTokenUtil.getUsernameFromToken(auth.substring(7)), id, tasks);
	}
	
}
