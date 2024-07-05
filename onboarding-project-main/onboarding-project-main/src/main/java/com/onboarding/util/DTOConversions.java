package com.onboarding.util;


import com.onboarding.DTO.NotiToTaskDTO;
import com.onboarding.DTO.NotiToUserTaskDTO;
import com.onboarding.DTO.NotificationDTO;
import com.onboarding.DTO.OnboardingCreateDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.onboarding.DTO.ActionDTO;
import com.onboarding.DTO.EmployeeOnboardingDTO;
import com.onboarding.DTO.EmployeeUserTaskDTO;
import com.onboarding.DTO.LinkDTO;
import com.onboarding.DTO.ModOnboardingDTO;
import com.onboarding.DTO.OnboardingToUserDTO;
import com.onboarding.DTO.PermissionDTO;
import com.onboarding.DTO.RoleCreateDTO;
import com.onboarding.DTO.TaskCreateDTO;
import com.onboarding.DTO.TaskDTO;
import com.onboarding.DTO.TaskToUserDTO;
import com.onboarding.DTO.TaskUserDTO;
import com.onboarding.DTO.UserCreateDTO;
import com.onboarding.DTO.UserDTO;
import com.onboarding.DTO.UserRoleDTO;
import com.onboarding.DTO.UserToOnBoardingDTO;
import com.onboarding.DTO.UserToTaskDTO;
import com.onboarding.model.NotificationEntity;
import com.onboarding.model.ActionEntity;
import com.onboarding.model.LinkEntity;
import com.onboarding.model.OnboardingEntity;
import com.onboarding.model.PermissionEntity;
import com.onboarding.model.TaskEntity;
import com.onboarding.model.UserEntity;
import com.onboarding.model.UserOnboarding;
import com.onboarding.model.UserRoleEntity;
import com.onboarding.model.UserTask;

public final class DTOConversions {

	private DTOConversions() {}
	
	public static TaskEntity taskcreateToEnt(TaskCreateDTO task) {
		
		TaskEntity taskEnt = new TaskEntity();
		
		taskEnt.setName(task.getName());
		taskEnt.setDescription(task.getDescription());
		taskEnt.setSerialNo(task.getSerialNo());
		
		return taskEnt;
	}
	
	public static TaskDTO taskToDTO(TaskEntity task) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setId(task.getId());
		taskDTO.setName(task.getName());
		taskDTO.setDescription(task.getDescription());
		taskDTO.setCreatedBy(task.getCreatedBy());
		taskDTO.setSerialNo(task.getSerialNo());
		if(!task.getChildren().isEmpty()) {
			for(TaskEntity child : task.getChildren()) {
				taskDTO.addChild(taskToDTO(child));
			}
		}
		taskDTO.setOnboarding(task.getOnboarding().getId());
		if(task.getParentTask() != null) {
			taskDTO.setParentTask(task.getParentTask().getId());
		}
		if(!task.getUsers().isEmpty()) {
			for(UserTask user : task.getUsers()) {
				taskDTO.addUser(taskToTTUDTO(user));
			}
		}
		if(!task.getLinks().isEmpty()) {
			for(LinkEntity link : task.getLinks()) {
				taskDTO.addLink(linkToDTO(link));
			}
		}
		return taskDTO;
	}
	
	public static TaskToUserDTO taskToTTUDTO(UserTask userTask) {
		TaskToUserDTO ttuDTO = new TaskToUserDTO();
		ttuDTO.setUser(userToTUDTO(userTask.getUser()));
		ttuDTO.setTask(userTask.getTask().getId());
		ttuDTO.setCompleted(userTask.getCompleted());
		return ttuDTO;
	}
	
	public static TaskUserDTO userToTUDTO(UserEntity user) {
		TaskUserDTO taskUserDTO = new TaskUserDTO();
		taskUserDTO.setFirstName(user.getFirstName());
		taskUserDTO.setLastName(user.getLastName());
		taskUserDTO.setEmail(user.getEmail());
		taskUserDTO.setPhone(user.getPhone());
		taskUserDTO.setRole(roleToDTO(user.getRole()));
		return taskUserDTO;
	}
	
	public static UserDTO userToDTO(UserEntity user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhone(user.getPhone());
		userDTO.setRole(roleToDTO(user.getRole()));
		userDTO.setAvatar(user.getAvatar());
		if(!user.getTasks().isEmpty()) {
			for(UserTask task : user.getTasks()) {
				userDTO.addTask(userToUTTDTO(task));
			}
		}
		if(!user.getOnboardings().isEmpty()) {
			for(UserOnboarding onboarding : user.getOnboardings()) {
				userDTO.addOnboarding(userToUTOBDTO(onboarding));
			}
		}
		return userDTO;
	}
	
	public static UserToTaskDTO userToUTTDTO(UserTask userTask) {
		UserToTaskDTO uttDTO = new UserToTaskDTO();
		
		uttDTO.setUser(userTask.getUser().getEmail());
		uttDTO.setTask(taskToDTO(userTask.getTask()));
		uttDTO.setCompleted(userTask.getCompleted());
		return uttDTO;
	}
	
	public static UserToOnBoardingDTO userToUTOBDTO(UserOnboarding userOb){	    
		UserToOnBoardingDTO utobDTO = new UserToOnBoardingDTO();
		
	    utobDTO.setUser(userOb.getUser().getEmail());
		utobDTO.setOnboarding(userOb.getOnboarding().getId());
		utobDTO.setIsMentor(userOb.getIsMentor());
		utobDTO.setStartDate(userOb.getStartDate());
		utobDTO.setEndDate(userOb.getEndDate());
		utobDTO.setCompleted(userOb.getCompleted());
	    
	    return utobDTO;
	
	}
	
	public static ModOnboardingDTO onboardingToDTO(OnboardingEntity onboarding) {
		ModOnboardingDTO onboardingDTO = new ModOnboardingDTO();
		onboardingDTO.setId(onboarding.getId());
		onboardingDTO.setName(onboarding.getName());
		onboardingDTO.setDescription(onboarding.getDescription());
		onboardingDTO.setCreatedBy(onboarding.getCreatedBy());
		onboardingDTO.setIsMentor(true);
		if(!onboarding.getUsers().isEmpty()) {
			for(UserOnboarding user : onboarding.getUsers()) {
				onboardingDTO.addUser(onboardingToOBTUDTO(user));
			}
		}
		if(!onboarding.getTasks().isEmpty()) {
			for(TaskEntity task : onboarding.getTasks()) {
				if(task.getParentTask() == null) {
                    onboardingDTO.addTask(DTOConversions.taskToDTO(task));
                }
			}
		}
		return onboardingDTO;
	}
	
	public static OnboardingToUserDTO onboardingToOBTUDTO(UserOnboarding userOb){	    
		OnboardingToUserDTO utobDTO = new OnboardingToUserDTO();
	    utobDTO.setUser(userToDTO(userOb.getUser()));
		utobDTO.setOnboarding(userOb.getOnboarding().getId());
		utobDTO.setPermission(permissionToDTO(userOb.getPermission()));
		utobDTO.setCompleted(userOb.getCompleted());
		utobDTO.setIsMentor(userOb.getIsMentor());
	    return utobDTO;
	}
	
	
	public static NotificationDTO convertToDTO(NotificationEntity noti) {
		NotificationDTO notiDTO = new NotificationDTO();
		notiDTO.setId(noti.getId());
		notiDTO.setText(noti.getText());
		notiDTO.setDate(noti.getDate());
		notiDTO.setTitle(noti.getTitle());
		notiDTO.setUserRecever(noti.getUserRecever());
		notiDTO.setIsMentor(noti.getIsMentor());
		notiDTO.setOpened(noti.getOpened());
						
		return notiDTO;
	}
	

	public static NotiToUserTaskDTO convertUTToNToUTDTO(UserTask userTask) {
		NotiToUserTaskDTO notiUTDTO = new NotiToUserTaskDTO();
		
		notiUTDTO.setTask(DTOConversions.convertToNT(userTask.getTask()));
		notiUTDTO.setUser(DTOConversions.userToDTO(userTask.getUser()));
		notiUTDTO.setCompleted(userTask.getCompleted());
		
		return notiUTDTO;
	}
	
	public static NotiToTaskDTO convertToNT(TaskEntity task) {
		NotiToTaskDTO notiDTO = new NotiToTaskDTO();
		
		
		notiDTO.setId(task.getId());
		notiDTO.setName(task.getName());
		notiDTO.setDescription(task.getDescription());
		notiDTO.setCreatedBy(task.getCreatedBy());
		if(!task.getChildren().isEmpty()) {
			for(TaskEntity child : task.getChildren()) {
				notiDTO.addChild(convertToNT(child));
			}
		}
		notiDTO.setOnboarding(task.getOnboarding().getId());
		if(task.getParentTask() != null) {
			notiDTO.setParentTask(task.getParentTask().getId());
		}
			
		return notiDTO;
	}

	public static UserRoleDTO roleToDTO(UserRoleEntity role) {
		UserRoleDTO roleDTO = new UserRoleDTO();
		if(role != null) {
			roleDTO.setId(role.getId());
			roleDTO.setName(role.getName());
			roleDTO.setPermission(permissionToDTO(role.getPermission()));
		}
		return roleDTO;
	}
	
	public static UserRoleEntity roleCreateDTOToRole(RoleCreateDTO roleDTO) {
		UserRoleEntity role = new UserRoleEntity(roleDTO.getName());
		return role;
		
	}
	
	public static PermissionDTO permissionToDTO(PermissionEntity permission) {
		PermissionDTO permDTO = new PermissionDTO();
		permDTO.setId(permission.getId());
		permDTO.setName(permission.getName());
		for(ActionEntity action : permission.getActions()) {
			permDTO.addAction(action.getType());
		}
		return permDTO;
	}
	
	public static PermissionEntity permissionDTOToPerm(PermissionDTO permDTO) {
		PermissionEntity perm = new PermissionEntity(permDTO.getName(), actionDTOToAction(permDTO.getActions()));
		return perm;
	}
	
	public static ActionDTO actionToDTO(ActionEntity action) {
		ActionDTO actionDTO = new ActionDTO();
		actionDTO.setType(action.getType());
		return actionDTO;
	}
	
	public static List<ActionEntity> actionDTOToAction(List<String> actionType) {
		List<ActionEntity> actions = new ArrayList<ActionEntity>();
		for(String aType : actionType) {
			actions.add(new ActionEntity(aType));
		}
		return actions;
	}
	
	public static LinkDTO linkToDTO(LinkEntity link) {
		LinkDTO linkDTO = new LinkDTO();
		linkDTO.setLink(link.getLink());
		return linkDTO;
	}
	
	public static LinkEntity DTOtoLink(LinkDTO linkDTO) {
		LinkEntity link = new LinkEntity();
		link.setLink(linkDTO.getLink());
		return link;
	}
	
	public static UserEntity userCreateDTOToUser(UserCreateDTO userDTO) {
		UserEntity user = new UserEntity(userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPassword(), userDTO.getPhone());
		user.setOnboardings(new ArrayList<>());
		user.setTasks(new ArrayList<>());
		return user;
	}
	
	public static UserTask UserEntToUserTask(UserEntity user, TaskEntity task, PermissionEntity perm, boolean isMentor) {
		UserTask userTask = new UserTask(user, task, perm, true, false);
		return userTask;
	}
	
	public static OnboardingEntity onboardingCreateDTOToOnboarding(OnboardingCreateDTO onbDTO) {
		OnboardingEntity onboarding = new OnboardingEntity(onbDTO.getName(), onbDTO.getDescription(), onbDTO.getCreatedBy());
		
		onboarding.setUsers(new ArrayList<>());
		onboarding.setTasks(new ArrayList<>());
		
		return onboarding;
	}
	
	public static EmployeeOnboardingDTO userOnboardingtoOnboardingDTO(UserOnboarding userOnboarding) {
		EmployeeOnboardingDTO onboardingDTO = new EmployeeOnboardingDTO();
		OnboardingEntity onboarding = userOnboarding.getOnboarding();
		onboardingDTO.setId(onboarding.getId());
		onboardingDTO.setName(onboarding.getName());
		onboardingDTO.setDescription(onboarding.getDescription());
		onboardingDTO.setCreatedBy(onboarding.getCreatedBy());

		for(UserTask task : userOnboarding.getUser().getTasks()) {
			if(task.getTask().getOnboarding().getId().equals(onboarding.getId()) && task.getTask().getParentTask() == null) {
                onboardingDTO.addTask(DTOConversions.userTaskToEMPUTDTO(task));
            }
		}
		
		for(UserOnboarding userOnb : onboarding.getUsers()) {
			if(userOnb.getIsMentor() != null && userOnb.getIsMentor() == true) {
				onboardingDTO.addMentor(userToDTO(userOnb.getUser()));
			}
		}

		onboardingDTO.setCompleted(userOnboarding.getCompleted());
		onboardingDTO.setStartDate(userOnboarding.getStartDate());
		onboardingDTO.setEndDate(userOnboarding.getEndDate());
		onboardingDTO.setIsMentor(userOnboarding.getIsMentor());
		return onboardingDTO;
	}
	
	public static EmployeeUserTaskDTO userTaskToEMPUTDTO(UserTask task) {
		EmployeeUserTaskDTO empUTDTO = new EmployeeUserTaskDTO();
		empUTDTO.setCompleted(task.getCompleted());
		empUTDTO.setIsMentor(task.getIsMentor());
		empUTDTO.setTask(task.getTask().getId());
		empUTDTO.setOnboarding(task.getTask().getOnboarding().getId());
		empUTDTO.setName(task.getTask().getName());
		empUTDTO.setDescription(task.getTask().getDescription());
		empUTDTO.setSerialNo(task.getTask().getSerialNo());

		for(UserTask userTask : task.getTask().getUsers()) {
			if(userTask.getIsMentor() != null && userTask.getIsMentor() == true) {
				empUTDTO.addMentor(userToDTO(userTask.getUser()));
			}
		}
		List<EmployeeUserTaskDTO> children = task.getTask().getChildren().stream()
				.map(child -> child.getUsers())
				.flatMap(users -> users.stream())
				.filter(ut -> ut.getUser().getEmail().equals(task.getUser().getEmail()))
				.map(ut -> userTaskToEMPUTDTO(ut))
				.collect(Collectors.toList());
		empUTDTO.setChildren(children);

		return empUTDTO;
	}
		
}

