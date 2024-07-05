package com.onboarding.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onboarding.DTO.EmployeeOnboardingDTO;
import com.onboarding.DTO.TaskCreateDTO;
import com.onboarding.DTO.TaskDTO;
import com.onboarding.service.TaskService;
import com.onboarding.util.DTOConversions;
import com.onboarding.util.JwtTokenUtil;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@EnableSwagger2
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskService service;
	
	@Autowired 
	JwtTokenUtil jwtTokenUtil;
	
	
	@GetMapping()
	public List<TaskDTO> getTasks(){	
		return service.getTasks();
	}
	
	@GetMapping("/{id}")
	public TaskDTO getTask(@PathVariable Long id) {
		return service.getTask(id);
	}
	
	@PostMapping()
	public TaskDTO addTask(@RequestBody TaskCreateDTO task, Long idOnbr) {
		return DTOConversions.taskToDTO(service.addTask(task, idOnbr));
	}
	

	@PutMapping("/{id}")
	public TaskDTO editTask(@PathVariable Long id, @RequestBody TaskCreateDTO task) {
		return service.editTask(id, task);
	}
	
	@DeleteMapping("/{id}")
	public String deleteTask(@PathVariable Long id) {
		return service.deleteTask(id);
	}
	
	@GetMapping("/{id}/done")
	public EmployeeOnboardingDTO completeTask(@PathVariable Long id, @RequestHeader("Authorization") String auth) throws Exception {
		return service.completeTask(id, jwtTokenUtil.getUsernameFromToken(auth.substring(7)));
	}
	
	@GetMapping("/{id}/help")
	public String getHelp(@PathVariable Long id,  @RequestHeader("Authorization") String auth) throws Exception {
		return service.sendHelp(id, jwtTokenUtil.getUsernameFromToken(auth.substring(7)));
	}

}
