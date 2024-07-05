package com.onboarding.service.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.onboarding.DTO.MentorOnBoardingDTO;
import com.onboarding.DTO.ModOnboardingDTO;
import com.onboarding.DTO.OnboardingCreateDTO;
import com.onboarding.DTO.OnboardingDTO;
import com.onboarding.DTO.TaskCreateDTO;
import com.onboarding.model.ActionEntity;
import com.onboarding.model.OnboardingEntity;
import com.onboarding.model.PermissionEntity;
import com.onboarding.model.TaskEntity;
import com.onboarding.model.UserEntity;
import com.onboarding.model.UserOnboarding;
import com.onboarding.model.UserTask;
import com.onboarding.repository.OnboardingRepository;
import com.onboarding.repository.PermissionRepository;
import com.onboarding.repository.TaskRepository;
import com.onboarding.repository.UserOnboardingRepository;
import com.onboarding.repository.UserRepository;
import com.onboarding.repository.UserTaskRepository;
import com.onboarding.service.OnboardingService;
import com.onboarding.service.PDFGeneratorService;
import com.onboarding.service.TaskService;
import com.onboarding.util.ApiException;
import com.onboarding.util.DTOConversions;
import com.onboarding.util.UserOnboardingId;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class OnboardingServiceImplementation implements OnboardingService{

	@Autowired
	private OnboardingRepository repo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private UserOnboardingRepository uoRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Autowired 
	private TaskService taskService;
	
	@Autowired 
	private UserTaskRepository utRepo;
	
	@Autowired
	private PermissionRepository permRepo;
	
	@Autowired
	private PDFGeneratorService pdfGeneratorService;
		
	 @Autowired
	 private NotificationServiceImplementation notiService;
	
//	@Autowired
//	private ActionRepository actionRepo;
	
	
	public PermissionEntity thisPermissionIs(List<String> actions) {
		PermissionEntity pom = new PermissionEntity();
		
		//lista svih permisija
		List<PermissionEntity> allPermissions = permRepo.findAll();
		
		for(PermissionEntity permission : allPermissions) {//sve permisije
			List<ActionEntity> permActions = permission.getActions();
			List<String> actionNames = new ArrayList<String>();
			
			for(ActionEntity act : permActions)
				actionNames.add(act.getType());
			
			if((actions.size() == actionNames.size()) && actions.containsAll(actionNames)) {
				pom = permission;
				break;
			}
		}
		
		return pom;
	}
	
	public String addAdminOnbr(OnboardingEntity onbr) {
		List<UserEntity> admins = userRepo.findAllByRoleName("Admin");
		
		for(UserEntity admin : admins) {
			PermissionEntity perm = permRepo.findByName("AllTaskPerm");
			uoRepo.save(new UserOnboarding(admin, onbr, true, perm));
		}
		
		return "Admin is added on onboarding.";
	}
	
	@Override

	public ModOnboardingDTO addOnboarding(String userId, OnboardingCreateDTO onboarding) {
		try {
//			UserEntity creator = userRepo.findById(userId).get();
//			String creatorsFName = creator.getFirstName();
//			String creatorsLName = creator.getLastName();	
			OnboardingEntity onbEnt = DTOConversions.onboardingCreateDTOToOnboarding(onboarding);
			onbEnt.setCreatedBy(userId);
			onbEnt = repo.save(onbEnt);
			
			//add Admin onBoarding
			addAdminOnbr(onbEnt);
			
			List<UserOnboarding> users = new ArrayList<UserOnboarding>();
			
			PermissionEntity pom = new PermissionEntity();
			pom = thisPermissionIs(Arrays.asList("create_task", "edit_task", "delete_task"));
			
			users.add(uoRepo.save(new UserOnboarding(userRepo.findById(userId).get(), onbEnt, true, false, pom, null))); // onaj koji je kreirao OnBr
			
			List<MentorOnBoardingDTO> mentorActions = onboarding.getMentors(); //mentori na OnBr, koje kreator onboarding-a dodaje
			for(MentorOnBoardingDTO mentor : mentorActions) {
				List<String> actions = mentor.getActions();
				//moram da nadjem permisju koja odgovara ovim akcijama | MORA DA POSTOJI KOMBINACIJA
				pom = thisPermissionIs(actions);
				UserEntity iAmMentor = userRepo.findById(mentor.getEmail()).get();
				users.add(uoRepo.save(new UserOnboarding(iAmMentor, onbEnt, true, false, pom, null)));	
				
				//noti da je MENTOR dodat na task
				notiService.sendNotification(onbEnt.getId(), mentor.getEmail(), "Cao "+ mentor.getEmail() +", dodeljen si na onbr: "+ onbEnt.getName() +" kao mentor, cestitke", "Srecno i lep rad zelim.");
			}
			
			for(UserOnboarding user : users)
				onbEnt.addUser(user);
//			onbEnt.setUsers(users);
			
			return DTOConversions.onboardingToDTO(onbEnt);
		}
		catch(Exception e){
			log.error("Onboarding not added " + new Date());
			throw e;
		}

	}
	
	@Override
	public List<? extends OnboardingDTO> getOnboardings(String userId){
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		List<OnboardingDTO> onboardingDTOs = new ArrayList<OnboardingDTO>();
		
		if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			for(OnboardingEntity onboarding : (List<OnboardingEntity>)repo.findAll()) {
				onboardingDTOs.add(DTOConversions.onboardingToDTO(onboarding));
			}
		}
//		else if(authorities.contains(new SimpleGrantedAuthority("ROLE_MOD"))) {
//			List<OnboardingDTO> onboardingDTOs = new ArrayList<OnboardingDTO>();
//			for(OnboardingEntity onboarding : (List<UserOnboarding>)uoRepo)
//		}
		else {
//			List<EmployeeOnboardingDTO> onboardingDTOs = new ArrayList<EmployeeOnboardingDTO>();
			onboardingDTOs = (List<OnboardingDTO>)getOnboardingsForUser(userId);
		}
		return onboardingDTOs;
	}
	
	@Override
	public List<? extends OnboardingDTO> getOnboardingsForUser(String userId) {
		List<OnboardingDTO> onboardingDTOs = new ArrayList<OnboardingDTO>();
		for(UserOnboarding userOnb : (List<UserOnboarding>)uoRepo.findAllByUserEmail(userId)) {
			if(userOnb.getIsMentor() != null && userOnb.getIsMentor() == true) {
				onboardingDTOs.add(DTOConversions.onboardingToDTO(userOnb.getOnboarding()));
			}else {
				onboardingDTOs.add(DTOConversions.userOnboardingtoOnboardingDTO(userOnb));
			}
		}
		return onboardingDTOs;
	}
	
	@Override
	public ModOnboardingDTO getOnboarding(long id) {
		OnboardingEntity onboarding = repo.findById(id).orElseThrow(() -> new ApiException("Onboarding not found!", HttpStatus.NOT_FOUND.value()));
		return DTOConversions.onboardingToDTO(onboarding);
	}
	
	@Override
	public ModOnboardingDTO editOnboarding(long id, OnboardingCreateDTO onboarding) {	
		try {

		OnboardingEntity onbEnt = repo.findById(id).orElseThrow(() -> new ApiException("Onboarding not found!", HttpStatus.NOT_FOUND.value()));
		onbEnt.setName(onboarding.getName());
		onbEnt.setDescription(onboarding.getDescription());
		onbEnt.setCreatedBy(onboarding.getCreatedBy());
			
			List<UserOnboarding> pom = onbEnt.getUsers();
			List<UserOnboarding> usersOnBoarding = new ArrayList<UserOnboarding>();
			List<UserOnboarding> editedUserOnBoarding = new ArrayList<UserOnboarding>(); //svi koji ce biti ili su
			for(UserOnboarding uo:pom) {
				if(uo.getIsMentor() == true)
					usersOnBoarding.add(uo);
				else {
					editedUserOnBoarding.add(uo);
				}
			}
			for(MentorOnBoardingDTO userId : onboarding.getMentors()) { //novi
				PermissionEntity perm = thisPermissionIs(userId.getActions());
				Boolean added = false;
				
				for(UserOnboarding user : usersOnBoarding) { //stari
					List<String> actionType = new ArrayList<String>();
					for(ActionEntity ac : user.getPermission().getActions()) {
						actionType.add(ac.getType());
					}
				
					Boolean actionSame = (userId.getActions()).equals(actionType);
					
					if( ((userId.getEmail()).equals(user.getUser().getEmail())) && actionSame ) {
						added = true;
						editedUserOnBoarding.add(user);
						break;
					}
					if( ((userId.getEmail()).equals(user.getUser().getEmail())) && !actionSame ) {
						added = true;
						user.setPermission(perm);
						editedUserOnBoarding.add(user);
						break;
					}
				}
				
				if(added == false) {
					UserOnboarding newUOnB = uoRepo.save(new UserOnboarding(userRepo.findById(userId.getEmail()).get(), onbEnt, true, false, perm, null));
					editedUserOnBoarding.add(newUOnB);
					//noti da je MENTOR dodat na onbr
					String modName = newUOnB.getUser().getEmail();
					notiService.sendNotification(onbEnt.getId(), modName, "Cao " + modName +", dodeljen si na onbr kao mentor, cestitke", "Srecno i lep rad zelim.");
				}
			}
			onbEnt.setUsers(editedUserOnBoarding);
			
			//delete unused
			for(UserOnboarding u1 : usersOnBoarding) { //old
				Boolean isHere = false;
				for(UserOnboarding u2 : editedUserOnBoarding) { //new
					if((u1.getUser().getEmail()).equals(u2.getUser().getEmail())){
						isHere = true;
						break;
					}
				}
				if(isHere == false)
					uoRepo.delete(u1);
			}
			
			return DTOConversions.onboardingToDTO(repo.save(onbEnt));
		}
		catch(Exception e) {
			log.error("Onboarding not edited " + new Date());
			throw e;
		}
	}
	
	@Override
	public String deleteOnboarding(long id) {
		try {
			OnboardingEntity onboarding = repo.findById(id).orElseThrow(() -> new ApiException("Onboarding not found!", HttpStatus.NOT_FOUND.value()));
			List<UserOnboarding> users = uoRepo.findByOnboardingId(id);
			
			List<TaskEntity> onlyParents = new ArrayList<TaskEntity>();
			for(TaskEntity task : taskRepo.findByOnboardingId(id)) {
				if(task.getParentTask() == null)
					onlyParents.add(task);
			}
			
			for(UserOnboarding user : users) {
				uoRepo.delete(user);
			}
			for(TaskEntity task : onlyParents) {
				taskService.deleteTask(task.getId());
			}
			if(onboarding != null) {
				repo.delete(onboarding);
			}
			return "valjda se obrisao OnBr";
		}
		catch(Exception e) {
			log.error("Onboarding not deleted " + new Date());
			throw e;
		}
	}

	@Override

	public ModOnboardingDTO addUsersToOnboarding(String currentUser ,Long id, List<String> userIds) {
		try {
//		UserEntity admin = userRepo.findByRoleName("ROLE_ADMIN");
//		Optional<UserOnboarding> userOnb = uoRepo.findById(new UserOnboardingId(id, userId));
		OnboardingEntity onboarding = repo.findById(id).orElseThrow(() -> new ApiException("Onboarding not found!", HttpStatus.NOT_FOUND.value()));
		PermissionEntity perm = permRepo.findById(6).orElseThrow(() -> new ApiException("", HttpStatus.NOT_FOUND.value()));
		for(String userId : userIds) {
			Optional<UserOnboarding> userOnb = uoRepo.findById(new UserOnboardingId(id, userId));
			if(userOnb.isEmpty()) {
//				throw new ApiException("User already added!", HttpStatus.BAD_REQUEST.value());
				UserEntity user = userRepo.findById(userId).orElseThrow(() -> new ApiException("User not found!", HttpStatus.NOT_FOUND.value()));
				uoRepo.save(new UserOnboarding(user, onboarding, false, false, perm, new Date()));
				for(TaskEntity task : onboarding.getTasks()) {
					utRepo.save(new UserTask(user, task, perm, false, false));
				}
				log.info("User : " + userId + " added to : " + onboarding.getName() + " onboarding.");
				//ovde logovanje da je user dodat na onbr
				notiService.sendNotification(id, userId, "Dodat si na onboarding", "Cao " + userId + ", dodat si na onboarding (" + id +". " + onboarding.getName() + "). Srecan rad.");
			}
		}
		return DTOConversions.onboardingToDTO(onboarding);
		}
		catch(Exception e) {
			log.error("User not added to Onboarding " + new Date());
			throw e;
		}
	}
	
	public Boolean areSubsSame(TaskEntity t1, TaskCreateDTO t2) {
		Boolean pom = true;
		
		List<TaskEntity> t1Subs = t1.getChildren();
		List<TaskCreateDTO> t2Subs = t2.getChildren();
		
		if(t1Subs.size() != t2Subs.size()) {
			pom = false;
			return pom;
		}
		// broj subova u oba taska je isti
		for(TaskEntity oldT : t1Subs) {
			Boolean fHere = false;
			for(TaskCreateDTO newT : t2Subs) {
				if(oldT.getDescription().equals(newT.getDescription())) {
					fHere = true;
					break;
				}
			}
			if(fHere == false) {
				pom = false;
				return pom;
			}
		}
		
		return pom;
	}
	
	@Override
	public ModOnboardingDTO addTasksToOnboarding(String userEmail, Long id, List<TaskCreateDTO> tasks){
		try {
	//		admin moze uvek ovo da radi ovo za bilo koji onboarding
	//		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	//		if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {}
			
			UserEntity currentUser = userRepo.findById(userEmail).get();
			OnboardingEntity currentOnboarding = repo.findById(id).orElseThrow(() -> new ApiException("Onboarding not found!", HttpStatus.NOT_FOUND.value()));
			
			List<UserOnboarding> allUsers = uoRepo.findByOnboardingId(id);
			List<UserOnboarding> employee = new ArrayList<UserOnboarding>();
			
			for(UserOnboarding uo:allUsers) {
				if(uo.getIsMentor()==false) {
					employee.add(uo);
				}
			}
			
			//currentUser - MOD
			UserOnboarding currentUO = uoRepo.findByUserAndOnboarding(currentUser, currentOnboarding);
			
			List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			Boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
			//no need for action check if its Mr.Admin
			List<String> bigActions = new ArrayList<String>();
			if(isAdmin == false) {
				List<ActionEntity> smallActions = currentUO.getPermission().getActions();
				for(ActionEntity ae : smallActions) {
					bigActions.add(ae.getType());
				}
			}
			
			
			//prvo se kreira i sacuva OnBr(sa mentorima) pa mu se dodaju Taskovi		
			List<TaskEntity> allTasks = taskRepo.findByOnboardingId(id); // svi taskovi za sada na OnBoarding(id) | edit | delete
			List<TaskEntity> pomForDel = new ArrayList<TaskEntity>();
			
			List<TaskEntity> filtered = new ArrayList<TaskEntity>(); // samo roditelji taskovi (bez podtaskova)
			for(TaskEntity fil : allTasks) {
				if(fil.getParentTask() == null)
					filtered.add(fil);
			}
			
			//poredjenje prosledjenih i postojecih taskova, edit | delete | create new task
			Boolean subCompleated = true;
			for(TaskCreateDTO task : tasks) { //novi
				
				Long newTaskId = task.getId();
				Boolean added = false;
				for(TaskEntity one : filtered) { //stari
					if(bigActions.contains("edit_task") || isAdmin) {
						//subtask, task, ispitivanje jednakosti
						Boolean isName = false;
						Boolean isDescription = task.getDescription().equals(one.getDescription());
						if(task.getName() != null && one.getName() != null) {
							isName = (one.getName()).equals(task.getName());
						}
						Boolean areSubs = areSubsSame(one, task);
						if(isName && isDescription && areSubs) {
							added = true;
							pomForDel.add(one);
							break;
						}
						Long existTaskId = one.getId();
					    if(newTaskId != null && existTaskId != null && newTaskId.equals(existTaskId)){
							Long taskId = one.getId();
							task.setOnboarding(id);
							taskService.editTask(taskId, task);
							
							//svim user-ima(emp) na ovom onbr za ovaj task mora da se dobije noti da se editovao task
							for(UserOnboarding user : allUsers) {
								if(user.getUser().getEmail() != userEmail)
									notiService.sendNotification(id, user.getUser().getEmail(), userEmail + " je editovao task: " + task.getName(), "Doslo je do azuriranja onboarding-a ("+id+". "+ currentOnboarding.getName() +"), editavi task je sada upotpunjen savremenim tehnologijama koje ce Vam pomoci u radi u napredovanju.");
							}
							
							if(areSubs == false) {
								// proverim da li su sva deca compleated, tada ostavljam compleated task ceo, ako je neko dodato/editovano, onda je false za compleated i task je false za compleated
								List<TaskEntity> children = taskRepo.findByParentTaskId(taskId);
								
								for(UserOnboarding uo:employee) {
									for(TaskEntity child : children) {
										UserTask childTask = utRepo.findByTaskIdAndUserEmail(child.getId(), uo.getUser().getEmail());
										if(childTask.getCompleted() == false) {
											subCompleated = false;
											break;
										}
									}
									if(subCompleated == true) {
										break;
									}
									else {
										UserTask userTask = utRepo.findByTaskIdAndUserEmail(taskId, uo.getUser().getEmail());
										userTask.setCompleted(false);
										uo.setCompleted(false);
										uo.setEndDate(null);
									}
								}
							}
							pomForDel.add(one);
							added = true;
							break;
					    }
						
					}
				}
				
				if((added == false) && ((bigActions.contains("create_task")) || isAdmin)) { //setujem novi AKO se ima permisija za to
	//				task.setOnboarding(onboarding.getId());
					for(UserOnboarding uo:employee) {
						uo.setCompleted(false);
						uo.setEndDate(null);
					}
					TaskEntity newTask = taskService.addTask(task, id);
	//				newTask.setOnboarding(onboarding);
					// dodaj nove taskove vec postojecim userima na OnBr
					currentOnboarding.addTask(newTask);
					for(UserOnboarding user : allUsers) {
						if(user.getIsMentor() == false) {
							List<TaskEntity> subs = newTask.getChildren();
							UserEntity userOnbr = user.getUser();
							PermissionEntity permission = user.getPermission();
							UserTask forNewTask = new UserTask(userOnbr,newTask, permission, false, false);
							utRepo.save(forNewTask);
							for(TaskEntity s : subs) { //UserSubTask relacija mrtva pogana mi treba da bi mogli da vuku stiklice za subove
								UserTask forNewSubTask = new UserTask(userOnbr,s, permission, false, false);
								utRepo.save(forNewSubTask);
							}
						}
						if(user.getUser().getEmail() != userEmail)
							//novi noti kada se kreira task
							notiService.sendNotification(id, user.getUser().getEmail(), userEmail + " je kreirao task: " + newTask.getName(), "Doslo je do azuriranja onboarding-a ("+id+". "+ currentOnboarding.getName() +"), dodat je novi task u svrhu unapredjenja vestina kod zaposlenih.");
					}
					pomForDel.add(newTask);
				}
			}
			//delete
			if(bigActions.contains("delete_task") || isAdmin) {
				for(TaskEntity t1 : filtered) { //old
					Boolean isHere = false;
					for(TaskEntity t2 : pomForDel) { //new					
					    Boolean test1 = false;
					    if(t1.getName() != null && t2.getName() != null) {
						    test1 = (t2.getName()).equals(t1.getName());
					    }			
					    if(test1) {
						    isHere = true;
						    break;
					    }
				    }
				    if(isHere == false) {
					    Long pomT1 = t1.getId();
					    taskService.deleteTask(pomT1);
					    for(UserOnboarding user : allUsers) {
						    if(user.getUser().getEmail() != userEmail)
							    notiService.sendNotification(id, user.getUser().getEmail(), userEmail + " je obrisa task: " + t1.getName(), "Doslo je do azuriranja onboarding-a ("+id+". "+ currentOnboarding.getName() +"), obrisani task je zastareo i stoga je klonjen, u skorije vreme ce biti dodati azurirana verzija koja ce pomoci u daljem radu.");
					    }
				     }
			    }
		    }
			
		    currentOnboarding.setTasks(pomForDel);
		    return DTOConversions.onboardingToDTO(repo.save(currentOnboarding));
	    }
		catch(Exception e) {
			log.error("Tasks not added to Onboarding " + new Date());
			throw e;
		}
	}



//	@Scheduled(cron = "0 0 12 ? * WED")
//	public void weeklyReport() throws IOException {
//		System.out.println(new Date() + "Scheduled print");
//		List<UserOnboarding> userOnboardings = uoRepo.findAll();
////		System.out.println(userOnboardings);
//		List<UserEntity> mentors = new ArrayList<UserEntity>();
//		List<UserOnboarding> employees = new ArrayList<UserOnboarding>();
//		Map<String, List<UserOnboarding>> reports = new HashMap<String, List<UserOnboarding>>();
//		for(UserOnboarding userOnboarding : userOnboardings) {
//			if(userOnboarding.getIsMentor() != null && userOnboarding.getIsMentor() == true) {
//				List<UserOnboarding> status = new ArrayList<UserOnboarding>();
////				System.out.println("-------------------");
//				for(UserOnboarding employee : userOnboardings
//						.stream().filter(uOnb -> uOnb.getIsMentor() == null || uOnb.getIsMentor() == false).collect(Collectors.toList())) {
////					System.out.println(employee);
//					if(userOnboarding.getOnboarding().getId() == employee.getOnboarding().getId()) {
//						status.add(employee);
//					}
//				}
////				System.out.println(status);
//				String key = userOnboarding.getUser().getEmail();
//				if(reports.containsKey(key))
//					status.addAll(reports.get(key));
//				Collections.sort(status, 
//						(a,b) -> a.getOnboarding().getId() < b.getOnboarding().getId() ? -1 : a.getOnboarding().getId() > b.getOnboarding().getId() ? 1 : 0);
//				reports.put(key, status);
//			}
//
//		}
////		System.out.println(reports);
//		for(String key : reports.keySet()) {
////			System.out.println(key);
//			pdfGeneratorService.export(key, reports.get(key));
//		}
//	}
}