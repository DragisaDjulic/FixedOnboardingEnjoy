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
import com.onboarding.DTO.NotificationDTO;
import com.onboarding.model.NotificationEntity;
import com.onboarding.service.NotificationService;
import com.onboarding.util.JwtTokenUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@EnableSwagger2
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	private NotificationService service;
	
	@Autowired 
	JwtTokenUtil jwtTokenUtil;
	
	@GetMapping()
	public List<? extends NotificationDTO> getNotifications(@RequestHeader("Authorization") String auth) throws Exception{
		return service.getNotifications(jwtTokenUtil.getUsernameFromToken(auth.substring(7)));
	}
	
	@GetMapping("/{id}")
	public NotificationDTO getNotification(@PathVariable int id) {
		return service.getNotification(id);
	}
	
	@PostMapping()
	public NotificationEntity addNotification(@RequestBody NotificationEntity noti) {
		return service.addNotification(noti);
	}
	
	@PutMapping("/{id}")
	public NotificationEntity editNotification(@PathVariable int id, @RequestBody NotificationDTO noti) {
		return service.editNotification(id, noti);
	}
	
	@DeleteMapping("/{id}")
	public String deleteNotification(@PathVariable int id) {
		return service.deleteNotification(id);
	}
	
	@GetMapping("/{id}/opened")
	public String openedNotification(@PathVariable Long id) {
		return service.openedNotification(id);
	}
	
	@GetMapping("/{id}/openedall")
	public String openAllNotifications(@PathVariable String id) {
		return service.openAllNotifications(id);
	}
}
