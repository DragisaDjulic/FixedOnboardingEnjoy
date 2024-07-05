package com.onboarding.service.implementation;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.onboarding.DTO.EmployeeOnboardingDTO;
import com.onboarding.DTO.LinkDTO;
import com.onboarding.DTO.TaskCreateDTO;
import com.onboarding.DTO.TaskDTO;
import com.onboarding.model.LinkEntity;
import com.onboarding.model.OnboardingEntity;
import com.onboarding.model.PermissionEntity;
import com.onboarding.model.TaskEntity;
import com.onboarding.model.UserEntity;
import com.onboarding.model.UserOnboarding;
import com.onboarding.model.UserTask;
import com.onboarding.repository.LinkRepository;
import com.onboarding.repository.NotificationRepository;
import com.onboarding.repository.OnboardingRepository;
import com.onboarding.repository.PermissionRepository;
import com.onboarding.repository.TaskRepository;
import com.onboarding.repository.UserOnboardingRepository;
import com.onboarding.repository.UserRepository;
import com.onboarding.repository.UserTaskRepository;
import com.onboarding.service.EmailSenderService;
import com.onboarding.service.NotificationService;
import com.onboarding.service.PDFGeneratorService;
import com.onboarding.service.TaskService;
import com.onboarding.util.ApiException;
import com.onboarding.util.DTOConversions;
import com.onboarding.util.UserOnboardingId;
import com.onboarding.util.UserTaskId;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TaskServiceImplementation implements TaskService {
	
	@Autowired
	private EmailSenderService service;
	
	@Autowired
	private TaskRepository repo;
	
	@Autowired
	private OnboardingRepository onboardRepo;
	
	@Autowired
	private LinkRepository linkRepo;
	
	@Autowired
	private UserTaskRepository utRepo;
	
	@Autowired 
	private UserOnboardingRepository uoRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PermissionRepository permRepo;
	
	@Autowired
	private NotificationRepository notiRepo;
	
	@Autowired
	private EmailSenderService mailSender;
	
	@Autowired
	private NotificationService notiService;
	
    public String sendHelp(Long id, String userName){
		UserEntity thisUser = userRepo.findById(userName).get();
    	
		TaskEntity task = repo.findById(id).get();
		OnboardingEntity onbr = task.getOnboarding();
		
		List<UserOnboarding> users = onbr.getUsers();
		List<String> mentors = new ArrayList<String>();
		
		for(UserOnboarding user : users) {
			if(user.getIsMentor() == true) {
				mentors.add(user.getUser().getEmail());
			}
		}
		
		for(String mentor : mentors) {
			service.sendEmail(mentor, "User-u " + thisUser.getFirstName() + " " + thisUser.getLastName() + " je potrebna pomoc na tasku: " + task.getName() + "(onbr: "+ onbr.getName() + ")", "Pao sam na glavu kad sam bio mali :( i sada tesko ide");
		}
		return "Postao si mail mentoru za pomoc.";
	}
	
	public void findLinks(TaskEntity taskEnt, TaskCreateDTO subtask) {
        for(LinkDTO link : subtask.getLinks()) {
        	
        	LinkEntity linkEnt = DTOConversions.DTOtoLink(link);
        	
        	linkEnt.setId(link.getId());
        	linkEnt.setLink(link.getLink());
        	
        	linkRepo.save(linkEnt);
        	taskEnt.addLink(linkEnt);
        }
	}
	
	public void deleteSubTasks(List<TaskEntity> children) {
		if(children != null) {
			for(TaskEntity subtask:children) {
				List<UserTask> users = utRepo.findByTaskId(subtask.getId());
				for(UserTask user:users) {
					utRepo.delete(user);
				}
				repo.delete(subtask);
			}		
		}
	}	
	
    public void editLinks(TaskEntity taskEnt, TaskCreateDTO taskCreateDTO) {
		
		List<LinkEntity> oldLinks = taskEnt.getLinks();
		if(oldLinks.size() != 0) {
			for(LinkEntity oldLink : oldLinks) {
//				List<TaskLink> links = linkRepo.findByLinkId(oldLink.getId());
				//treba da obrisem ovo, ali mi ni relaciju fizicku nemamo
				linkRepo.delete(oldLink);
			}
		}
		
		List<LinkDTO> newLinks = taskCreateDTO.getLinks();
        for(LinkDTO link : newLinks) {
        	
        	LinkEntity linkEnt = DTOConversions.DTOtoLink(link);
        	
        	linkEnt.setId(link.getId());
        	linkEnt.setLink(link.getLink());
        	
        	linkRepo.save(linkEnt);
        	taskEnt.addLink(linkEnt);
        }
	}
	
	public TaskEntity settingTaskUp(Long id, TaskCreateDTO task) {
		TaskEntity taskEnt = repo.findById(id).orElseThrow(() -> new ApiException("Task not found!", HttpStatus.NOT_FOUND.value()));
		
		taskEnt.setName(task.getName());
		taskEnt.setDescription(task.getDescription());
		taskEnt.setCreatedBy(task.getCreatedBy());
		taskEnt.setOnboarding(onboardRepo.findById(task.getOnboarding()).orElseThrow(() -> new ApiException("Onboarding not found!", HttpStatus.NOT_FOUND.value())));
		
		return taskEnt;
	}
		
    @Override
	public TaskEntity addTask(TaskCreateDTO task, Long OBid) {	
		try {
			TaskEntity taskEnt = DTOConversions.taskcreateToEnt(task);
			
			taskEnt.setCreatedBy(task.getCreatedBy()); //ko mi daje ovo ????
			taskEnt.setOnboarding(onboardRepo.findById(OBid).orElseThrow(() -> new ApiException("Onboarding not found!", HttpStatus.NOT_FOUND.value())));
//			taskEnt.setOnboarding(onboardRepo.findById(2l).orElseThrow(() -> new ApiException("Onboarding not found!", HttpStatus.NOT_FOUND.value())));
//			taskEnt.setSerialNo(1);
			repo.save(taskEnt);

//			repo.saveAndFlush(taskEnt);
			
//			taskEnt.setParentTask(repo.findById(task.getParentTask()).get());
//			System.out.println(task.getChildren());
			
			for(TaskCreateDTO subtask : task.getChildren()) {
				
				TaskEntity sTask = new TaskEntity(subtask.getDescription(), taskEnt, taskEnt.getCreatedBy());
				sTask.setOnboarding(onboardRepo.findById(OBid).get());
				
//				findLinks(sTask, subtask);
				repo.save(sTask);
				
				taskEnt.addChild(sTask);
			}
			
//			findLinks(taskEnt, task);
			repo.save(taskEnt);
			
//	      -----------------------------------------		
//			if(!task.getChildren().isEmpty()) {
//				TaskEntity parent = new TaskEntity();
//				taskEnt.setParentTask(parent);
//			}
//			else
//				taskEnt.setParentTask(null);		
//			-----------------------------------------
			PermissionEntity perm = permRepo.findById(5).orElseThrow(() -> new ApiException("", HttpStatus.NOT_FOUND.value()));
			
			for(String mentor : task.getMentors()) {
				
				UserEntity mentorUser = userRepo.findById(mentor)
	.orElseThrow(() ->new ApiException("Mentor not found in data base!", HttpStatus.NOT_FOUND.value()));
				
				UserTask saveMentorOnTask = DTOConversions.UserEntToUserTask(mentorUser, taskEnt, perm, true);		
//				taskEnt.addMentor(mentorUser);		
//				mentorUser.addMentoringTask(taskEnt);
				
				(taskEnt.getUsers()).add(utRepo.save(saveMentorOnTask));
			}
//			taskEnt.setMentors(users);
			return taskEnt;
		}
		catch(Exception e) {
			log.error("Task not added " + new Date());
			throw e;
		}
    	
	}

	@Override
	public List<TaskDTO> getTasks() {
		List<TaskDTO> taskDTOs = new ArrayList<TaskDTO>();
		for(TaskEntity task : (List<TaskEntity>)repo.findAll()) {
			taskDTOs.add(DTOConversions.taskToDTO(task));
		}
		return taskDTOs;
	}

	@Override
	public TaskDTO getTask(Long id) {
		TaskEntity task = repo.findById(id).orElseThrow(() -> new ApiException("Task not found!", HttpStatus.NOT_FOUND.value()));
		return DTOConversions.taskToDTO(task);
	}
	
	@Override
	public TaskDTO editTask(Long id, TaskCreateDTO task) {
		try {
			//Task{/id} menjam sa TaskCreateDTO task
			TaskEntity EditedTask = settingTaskUp(id, task);
			
			List<UserTask> usersOnTask = utRepo.findByTaskId(id);
			List<UserEntity> users = new ArrayList<UserEntity>();
			for(UserTask ut : usersOnTask) {
				users.add(ut.getUser());
			}
			
			List<TaskEntity> oldSubTasks = EditedTask.getChildren();//old
			List<TaskEntity> newChildren = new ArrayList<TaskEntity>();
			PermissionEntity perm = permRepo.findById(5).orElseThrow(() -> new ApiException("There is no permisson with this ID", HttpStatus.NOT_FOUND.value()));
			//creating new subtasks
			//postojeci subtaskovi koje menjam ce imati ID u preview modu
			List<TaskCreateDTO> children = task.getChildren(); // novi + neki stari(mogucnost)
			//da li je oldSubTasks lista = null ?? , proveris ovde i ne ides kroz petljanje nego odmah kreiranje
			
			for(TaskCreateDTO child : children) {
				Boolean added = false;
				//ima li starih
				for(TaskEntity oldSubTask : oldSubTasks) {
					Boolean status1 = (oldSubTask.getDescription()).equals(child.getDescription());
			        
					if(status1) {
						added = true;
						newChildren.add(oldSubTask);
						//editLinks(EditedTask, child);
						break;
					}				
				}
				
				if(added == false) { //kreiraj ga kao new Object(TaskEntity)
					child.setParentTask(EditedTask.getId());
					Long ParentTaskId = child.getParentTask();
					TaskEntity tEnt = repo.findById(ParentTaskId).orElseThrow(() -> new ApiException("Parent not found!", HttpStatus.NOT_FOUND.value()));			
					
					OnboardingEntity ObEnt = onboardRepo.findById(task.getOnboarding()).orElseThrow(() -> new ApiException("Onboaring not found!", HttpStatus.NOT_FOUND.value()));
					TaskEntity childEnt = new TaskEntity(child.getDescription(), tEnt, ObEnt);
					
//					editLinks(childEnt, child);
					
		            repo.save(childEnt);		
		            newChildren.add(childEnt);
		            
					//dodaj i userTask(task deo je novi sub) za sve usere kojima je promenjen ovaj subtask
					for(UserEntity user : users) {
						utRepo.save(new UserTask(user, childEnt, perm, false, false));
					}
		            
				}
			}
			EditedTask.setChildren(newChildren);
			//deleting old subtasks that are not used
			for(TaskEntity t1 : oldSubTasks) { //old
				Boolean isHere = false;
				for(TaskEntity t2 : newChildren) { //new
					if((t1.getDescription()).equals(t2.getDescription())){
						isHere = true;
						break;
					}
				}
				if(isHere == false)
					deleteTask(t1.getId());
			}
				
//			set links - LinkDTO but only for Task, subtasks links was set in lines above
//			editLinks(EditedTask, task);
			
			List<UserTask> usersE = EditedTask.getUsers(); //stari mentori, sa mogucnoscu da neki od njih, ili svi, budu i novi
			List<UserTask> newEmp = new ArrayList<UserTask>();

			List<UserTask> oldUserTaskM = new ArrayList<UserTask>(); //stari mentori na tasku
			for(UserTask user : usersE) {
				if(user.getIsMentor() == true)
					oldUserTaskM.add(user);
			}
			
			List<String> menotrs = task.getMentors();
			
			for(String mentor : menotrs) { //novi
				Boolean exist = false;
				for(UserTask oldMentor : oldUserTaskM) { //stari
					Boolean isMentorNew = (oldMentor.getUser().getEmail()).equals(mentor);
					Boolean isTaskOk = (oldMentor.getTask().getId().equals(id));
					if(isMentorNew && isTaskOk) {//UserTask postoji vec za njega i nema potrebe za kreiranjem
						exist = true;
						newEmp.add(oldMentor);
						break;
					}			
				}
				if(exist == false) {
					UserEntity mentorUser = userRepo.findById(mentor).orElseThrow(() ->new ApiException("Mentor not found in data base!", HttpStatus.NOT_FOUND.value()));		
					UserTask saveMentorOnTask = DTOConversions.UserEntToUserTask(mentorUser, EditedTask, perm, true);
					
					newEmp.add(saveMentorOnTask);
					EditedTask.addUser(saveMentorOnTask);
					utRepo.save(saveMentorOnTask);
				}
				
			}		
			//moram da obiresem mentore, mentor(user)_task kolona UserEmail + TaskID
			for(UserTask ut1 : oldUserTaskM) { //old
				Boolean isHere = false;
				for(UserTask ut2 : newEmp) { //new
					if((ut1.getUser().getEmail()).equals(ut2.getUser().getEmail())){
						isHere = true;
						break;
					}
				}
				if(isHere == false)
					utRepo.delete(ut1);
			}
				
			return DTOConversions.taskToDTO(repo.save(EditedTask));
		}
		catch(Exception e) {
			log.error("Task not edited " + new Date());
			throw e;
		}
	}
	
	@Override
	public String  deleteTask(Long id) {
		
		try {
			TaskEntity task = repo.findById(id).orElseThrow(() -> new ApiException("Task not found!", HttpStatus.NOT_FOUND.value()));
			List<UserTask> mUsers = utRepo.findByTaskId(id);
			
			List<TaskEntity> subtasks = task.getChildren();
			
			//moram da obrise mi listu podtaskova taska koji brisem		
			deleteSubTasks(subtasks);
					
			//notification -> userTask -> taskID
			for(UserTask user:mUsers) {
				utRepo.delete(user);
			}	

			
			if(task != null)
				repo.deleteById(id);
			return "Task je obrisan";
		}
		catch(Exception e) {
			log.error("Task not deleted " + new Date());
			throw e;
		}
	}

	@Override
	public EmployeeOnboardingDTO completeTask(Long id, String userId) {
		//TODO SET USERTASK ZA TOG USERA NA COMPLETED AKO SU SVI SUBTASKOVI COMPLETED -> PARENT COMPLETED 
		//TODO AKO SU SVI PARENTI COMPLETED -> ONB COMPLETED ONB COMPLETED U ZASEBAN POZIV
		//TODO PROVERE DA LI SU SVI PRETHODNI ZAVRSENI
		//TODO RETURN STA ???? RETURN PARENT TASK 
		UserTask task = utRepo.findById(new UserTaskId(id, userId)).orElseThrow(() -> new ApiException("Task not found!", HttpStatus.NOT_FOUND.value()));
		if(task.getCompleted() == true) {
			throw new ApiException("Task already completed!", HttpStatus.BAD_REQUEST.value());
		}
		OnboardingEntity onboarding = task.getTask().getOnboarding();
		UserOnboarding userOnboarding = uoRepo.findById(new UserOnboardingId(onboarding.getId(), userId))
				.orElseThrow(() -> new ApiException("User not assigned to onboarding!", HttpStatus.NOT_FOUND.value()));
		/////////
//		Optional<UserTask> parentTaskOpt = utRepo.findById(new UserTaskId(task.getTask().getParentTask().getId(), userId));
		UserTask parentTask = task.getTask().getParentTask() == null ? task 
				: utRepo.findById(new UserTaskId(task.getTask().getParentTask().getId(), userId))
					.orElseThrow(() -> new ApiException("Task not found!", HttpStatus.NOT_FOUND.value()));
		UserEntity currUser = parentTask.getUser();
		UserTask prevTask = null;
		if(parentTask.getTask().getSerialNo() > 1) {
			prevTask = utRepo.findById(new UserTaskId(onboarding.getTask(parentTask.getTask().getSerialNo()-1).getId(), userId))
					.orElseThrow(() -> new ApiException("Task not found!", HttpStatus.NOT_FOUND.value()));
		}
		if(prevTask == null || (prevTask != null && prevTask.getCompleted())) {
			if(task.getTask().getParentTask() == null) {
				parentTask.setCompleted(true);
			}else {
				task.setCompleted(true);
//				UserTask parentTask = onboarding.getTasks().stream().filter(t -> t.getId().equals(task.getTask().getParentTask().getId())).findFirst().get();
				
				if(parentTask.getCompleted() == true) {
					throw new ApiException("Parent task already completed!", HttpStatus.BAD_REQUEST.value());
				}
//				List<TaskEntity> subtasks = repo.findByParentTaskId(parentTask.getTask().getId());
				List<TaskEntity> subtasks = onboarding.getTasks().stream()
						.filter(t -> t.getParentTask() != null && t.getParentTask().getId().equals(parentTask.getTask().getId())).collect(Collectors.toList());
				List<Long> subtaskIds = subtasks.stream().map(subtask -> subtask.getId()).collect(Collectors.toList());
				List<UserTask> userSubtasks = utRepo.findByTaskIdInAndUserEmail(subtaskIds, userId);
				if(userSubtasks.stream().reduce((a,b) -> new UserTask(a.getCompleted() && b.getCompleted())).get().getCompleted()) {
					parentTask.setCompleted(true);
					utRepo.save(parentTask);
				}
			}
			List<Long> parentTaskIds = onboarding.getTasks().stream()
					.filter(t -> t.getParentTask() == null).map(t -> t.getId()).collect(Collectors.toList());
			
			List<UserTask> userTasks = utRepo.findByTaskIdInAndUserEmail(parentTaskIds, userId);
			if(userTasks.stream().reduce((a,b) -> new UserTask(a.getCompleted() && b.getCompleted())).get().getCompleted()) {
				userOnboarding.setCompleted(true);
				log.info("User : " + userId + " completed : " + onboarding.getName() + " onboarding.");
				userOnboarding.setEndDate(new Date());
				userOnboarding = uoRepo.save(userOnboarding);
				
				//posalji user-u koji je zavrsio onbr noti da je odradio sve i da povisica stize
				notiService.sendNotification(onboarding.getId(), userId, "Mnogo si dobar seko, odradio si: " + onboarding.getName(), "Bravo za tebe.");
				
				List<UserOnboarding> mentors = userOnboarding.getOnboarding().getUsers()
						.stream().filter(user -> user.getIsMentor().equals(true)).collect(Collectors.toList());
				String title = currUser.getFirstName() + " " + currUser.getLastName() + " completed " + onboarding.getName();
				String body = "Started on " + userOnboarding.getStartDate() + ", ended on " + userOnboarding.getEndDate();
				for(UserOnboarding mentor : mentors) {
					System.out.println(mentor.getUser().getEmail());
					mailSender.sendEmail(mentor.getUser().getEmail(), title , body);
					//posalji mentorima na onbr da ga je jadan user zavrsio
					notiService.sendNotification(onboarding.getId(), mentor.getUser().getEmail(), userId + " je zavrsion onbr:" + onboarding.getName(), "Cestitke na uspesnom mentorovanju user-a na onbarding-u.");
				}
				
			}
			
			utRepo.save(task);
//			onboardRepo.save(task.getTask().getOnboarding()); //TODO KRENI ODAVDE I VUCI TASKOVE
//			return DTOConversions.userTaskToEMPUTDTO(parentTask);
			return DTOConversions.userOnboardingtoOnboardingDTO(userOnboarding);
		}else {
			throw new ApiException("Previous task not completed!", HttpStatus.NOT_FOUND.value());
		}
	}

	
	
	
	
//	@Scheduled(cron = "0 * * * * ?")
//	public void weeklyReport() throws IOException {
//		System.out.println(new Date() + "Scheduled print");
//		List<UserTask> userTasks = utRepo.findAll();
//		pdfGeneratorService.export();
//	}
	
	
	
	
	
	
	
	
	
	
}
