package com.onboarding.service.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.onboarding.DTO.MentorOnBoardingDTO;
import com.onboarding.DTO.ModOnboardingDTO;
import com.onboarding.DTO.OnboardingCreateDTO;
import com.onboarding.DTO.OnboardingDTO;
import com.onboarding.DTO.TaskCreateDTO;
import com.onboarding.model.ActionEntity;
import com.onboarding.model.OnboardingEntity;
import com.onboarding.model.PermissionEntity;
import com.onboarding.model.UserEntity;
import com.onboarding.model.UserOnboarding;
import com.onboarding.repository.OnboardingRepository;
import com.onboarding.repository.PermissionRepository;
import com.onboarding.repository.TaskRepository;
import com.onboarding.repository.UserOnboardingRepository;
import com.onboarding.repository.UserRepository;
import com.onboarding.repository.UserTaskRepository;
import com.onboarding.service.PDFGeneratorService;
import com.onboarding.service.TaskService;

public class OnboardingServiceImplementationTest {

    @Mock
    private OnboardingRepository repo;
    
    @Mock
    private UserRepository userRepo;
    
    @Mock
    private UserOnboardingRepository uoRepo;
    
    @Mock
    private TaskRepository taskRepo;
    
    @Mock
    private TaskService taskService;
    
    @Mock
    private UserTaskRepository utRepo;
    
    @Mock
    private PermissionRepository permRepo;
    
    @Mock
    private PDFGeneratorService pdfGeneratorService;
    
    @Mock
    private NotificationServiceImplementation notiService;
    
    @InjectMocks
    private OnboardingServiceImplementation onboardingService;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testThisPermissionIs() {
        List<ActionEntity> actions = new ArrayList<>();
        ActionEntity action = new ActionEntity();
        action.setType("action1");
        actions.add(action);
        
        PermissionEntity permission = new PermissionEntity();
        permission.setActions(actions);
        
        when(permRepo.findAll()).thenReturn(Collections.singletonList(permission));
        
        PermissionEntity result = onboardingService.thisPermissionIs(Collections.singletonList("action1"));
        assertEquals(permission, result);
    }
    
    @Test
    public void testAddAdminOnbr() {
        OnboardingEntity onbr = new OnboardingEntity();
        UserEntity admin = new UserEntity();
        admin.setEmail("admin@example.com");
        
        when(userRepo.findAllByRoleName("Admin")).thenReturn(Collections.singletonList(admin));
        when(permRepo.findByName("AllTaskPerm")).thenReturn(new PermissionEntity());
        
        String result = onboardingService.addAdminOnbr(onbr);
        assertEquals("Admin is added on onboarding.", result);
    }
    
    @Test
    public void testAddOnboarding() {
        OnboardingCreateDTO onboardingCreateDTO = new OnboardingCreateDTO();
        onboardingCreateDTO.setName("Test Onboarding");
        onboardingCreateDTO.setMentors(Collections.singletonList(new MentorOnBoardingDTO("mentor@example.com", Arrays.asList("create_task", "edit_task", "delete_task"))));
        
        UserEntity user = new UserEntity();
        user.setEmail("admin@gmail.com");
        
        when(userRepo.findById("admin@gmail.com")).thenReturn(Optional.of(user));
        when(repo.save(any(OnboardingEntity.class))).thenReturn(new OnboardingEntity());
        when(permRepo.findByName("AllTaskPerm")).thenReturn(new PermissionEntity());
        when(permRepo.findAll()).thenReturn(Collections.singletonList(new PermissionEntity()));
        
        ModOnboardingDTO result = onboardingService.addOnboarding("admin@gmail.com", onboardingCreateDTO);
        assertNotNull(result);
    }
    
    @Test
    public void testGetOnboardings() {
        UserEntity user = new UserEntity();
        user.setEmail("admin@gmail.com");
        
        when(repo.findAll()).thenReturn(Collections.singletonList(new OnboardingEntity()));
        
        List<? extends OnboardingDTO> result = onboardingService.getOnboardings("admin@gmail.com");
        assertNotNull(result);
    }
    
    @Test
    public void testGetOnboardingsForUser() {
        UserOnboarding userOnboarding = new UserOnboarding();
        userOnboarding.setIsMentor(true);
        userOnboarding.setOnboarding(new OnboardingEntity());
        
        when(uoRepo.findAllByUserEmail("admin@gmail.com")).thenReturn(Collections.singletonList(userOnboarding));
        
        List<? extends OnboardingDTO> result = onboardingService.getOnboardingsForUser("admin@gmail.com");
        assertNotNull(result);
    }
    
    @Test
    public void testGetOnboarding() {
        OnboardingEntity onboardingEntity = new OnboardingEntity();
        when(repo.findById(1L)).thenReturn(Optional.of(onboardingEntity));
        
        ModOnboardingDTO result = onboardingService.getOnboarding(1L);
        assertNotNull(result);
    }
    
    @Test
    public void testEditOnboarding() {
        OnboardingCreateDTO onboardingCreateDTO = new OnboardingCreateDTO();
        onboardingCreateDTO.setName("Updated Onboarding");
        
        OnboardingEntity onboardingEntity = new OnboardingEntity();
        when(repo.findById(1L)).thenReturn(Optional.of(onboardingEntity));
        when(repo.save(any(OnboardingEntity.class))).thenReturn(onboardingEntity);
        
        ModOnboardingDTO result = onboardingService.editOnboarding(1L, onboardingCreateDTO);
        assertNotNull(result);
    }
    
    @Test
    public void testDeleteOnboarding() {
        OnboardingEntity onboardingEntity = new OnboardingEntity();
        when(repo.findById(1L)).thenReturn(Optional.of(onboardingEntity));
        
        String result = onboardingService.deleteOnboarding(1L);
        assertEquals("valjda se obrisao OnBr", result);
    }
    
    @Test
    public void testAddUsersToOnboarding() {
        OnboardingEntity onboardingEntity = new OnboardingEntity();
        PermissionEntity permissionEntity = new PermissionEntity();
        
        when(repo.findById(1L)).thenReturn(Optional.of(onboardingEntity));
        when(permRepo.findById(6)).thenReturn(Optional.of(permissionEntity));
        when(userRepo.findById("admin@gmail.com")).thenReturn(Optional.of(new UserEntity()));
        
        ModOnboardingDTO result = onboardingService.addUsersToOnboarding("currentUser", 1L, Arrays.asList("admin@gmail.com"));
        assertNotNull(result);
    }
    
    public void testAddTasksToOnboarding() {
        OnboardingEntity onboardingEntity = new OnboardingEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("admin@gmail.com");
        
        when(repo.findById(1L)).thenReturn(Optional.of(onboardingEntity));
        when(userRepo.findById("admin@gmail.com")).thenReturn(Optional.of(userEntity));
        
        ModOnboardingDTO result = onboardingService.addTasksToOnboarding("admin@gmail.com", 1L, Collections.singletonList(new TaskCreateDTO()));
        assertNotNull(result);
    }
}