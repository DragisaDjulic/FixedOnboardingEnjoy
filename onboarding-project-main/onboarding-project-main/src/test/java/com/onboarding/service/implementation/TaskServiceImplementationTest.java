package com.onboarding.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.onboarding.DTO.*;
import com.onboarding.model.*;
import com.onboarding.repository.*;
import com.onboarding.service.EmailSenderService;
import com.onboarding.service.NotificationService;
import com.onboarding.util.ApiException;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplementationTest {

    @InjectMocks
    private TaskServiceImplementation taskService;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private OnboardingRepository onboardingRepository;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private UserTaskRepository userTaskRepository;

    @Mock
    private UserOnboardingRepository userOnboardingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendHelp() {
        UserEntity user = new UserEntity();
        user.setEmail("user@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        TaskEntity task = new TaskEntity();
        task.setId(1L);
        task.setName("Sample Task");

        OnboardingEntity onboarding = new OnboardingEntity();
        onboarding.setName("Sample Onboarding");
        task.setOnboarding(onboarding);

        UserOnboarding mentorUserOnboarding = new UserOnboarding();
        mentorUserOnboarding.setIsMentor(true);
        UserEntity mentor = new UserEntity();
        mentor.setEmail("mentor@example.com");
        mentorUserOnboarding.setUser(mentor);

        List<UserOnboarding> users = Arrays.asList(mentorUserOnboarding);
        onboarding.setUsers(users);

        when(userRepository.findById("user@example.com")).thenReturn(Optional.of(user));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        String response = taskService.sendHelp(1L, "user@example.com");

        assertEquals("Postao si mail mentoru za pomoc.", response);
        verify(emailSenderService, times(1)).sendEmail(eq("mentor@example.com"), anyString(), anyString());
    }

    @Test
    void testAddTask() {
        TaskCreateDTO taskCreateDTO = new TaskCreateDTO();
        taskCreateDTO.setCreatedBy("creator@example.com");
        taskCreateDTO.setName("Sample Task");
        taskCreateDTO.setDescription("Sample Description");

        OnboardingEntity onboarding = new OnboardingEntity();
        onboarding.setId(1L);
        onboarding.setName("Sample Onboarding");

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setCreatedBy("creator@example.com");
        taskEntity.setOnboarding(onboarding);

        when(onboardingRepository.findById(1L)).thenReturn(Optional.of(onboarding));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        TaskEntity result = taskService.addTask(taskCreateDTO, 1L);

        assertNotNull(result);
        assertEquals("creator@example.com", result.getCreatedBy());
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void testGetTask() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        taskEntity.setName("Sample Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

        TaskDTO taskDTO = taskService.getTask(1L);

        assertNotNull(taskDTO);
        assertEquals("Sample Task", taskDTO.getName());
    }

    @Test
    void testDeleteTask() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

        String response = taskService.deleteTask(1L);

        assertEquals("Task je obrisan", response);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
