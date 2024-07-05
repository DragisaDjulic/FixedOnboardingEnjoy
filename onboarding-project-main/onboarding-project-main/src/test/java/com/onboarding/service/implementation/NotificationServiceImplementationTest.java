package com.onboarding.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.onboarding.DTO.NotificationDTO;
import com.onboarding.model.NotificationEntity;
import com.onboarding.model.UserEntity;
import com.onboarding.model.UserOnboarding;
import com.onboarding.repository.NotificationRepository;
import com.onboarding.repository.OnboardingRepository;
import com.onboarding.repository.TaskRepository;
import com.onboarding.repository.UserOnboardingRepository;
import com.onboarding.repository.UserRepository;
import com.onboarding.util.ApiException;
import com.onboarding.util.DTOConversions;

public class NotificationServiceImplementationTest {

    @InjectMocks
    private NotificationServiceImplementation notificationService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private NotificationRepository notiRepo;

    @Mock
    private OnboardingRepository onbrRepo;

    @Mock
    private UserOnboardingRepository uoRepo;

    @Mock
    private TaskRepository taskRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNotification() {
        NotificationEntity notification = new NotificationEntity();
        when(notiRepo.save(any(NotificationEntity.class))).thenReturn(notification);

        NotificationEntity savedNotification = notificationService.addNotification(notification);

        verify(notiRepo, times(1)).save(notification);
        assertEquals(notification, savedNotification);
    }

    @Test
    public void testSendNotification() {
        Long onboardingId = 1L;
        String userId = "admin@gmail.com";
        String title = "Title";
        String text = "Text";
        UserOnboarding userOnboarding = new UserOnboarding();
        userOnboarding.setIsMentor(true);
        when(uoRepo.findByOnboardingIdAndUserEmail(anyLong(), anyString())).thenReturn(userOnboarding);

        String result = notificationService.sendNotification(onboardingId, userId, title, text);

        verify(notiRepo, times(1)).save(any(NotificationEntity.class));
        assertEquals("Poslata je notifikacija", result);
    }

    @Test
    public void testGetNotifications() {
        String userId = "admin@gmail.com";
        UserEntity user = new UserEntity();
        user.setEmail(userId);
        List<NotificationEntity> notifications = new ArrayList<>();
        NotificationEntity notification = new NotificationEntity();
        notification.setUserRecever(userId);
        notifications.add(notification);
        when(userRepo.findById(anyString())).thenReturn(Optional.of(user));
        when(notiRepo.findAll()).thenReturn(notifications);

        List<NotificationDTO> result = notificationService.getNotifications(userId);

        verify(userRepo, times(1)).findById(userId);
        verify(notiRepo, times(1)).findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetNotification() {
        long notificationId = 1L;
        NotificationEntity notification = new NotificationEntity();
        when(notiRepo.findById(anyLong())).thenReturn(Optional.of(notification));

        NotificationDTO result = notificationService.getNotification(notificationId);

        verify(notiRepo, times(1)).findById(notificationId);
        assertEquals(notification.getId(), result.getId());
    }

    @Test
    public void testEditNotification() {
        long notificationId = 1L;
        NotificationDTO dto = new NotificationDTO();
        NotificationEntity notification = new NotificationEntity();
        when(notiRepo.findById(anyLong())).thenReturn(Optional.of(notification));
        when(notiRepo.save(any(NotificationEntity.class))).thenReturn(notification);

        NotificationEntity result = notificationService.editNotification(notificationId, dto);

        verify(notiRepo, times(1)).findById(notificationId);
        verify(notiRepo, times(1)).save(notification);
        assertEquals(notification, result);
    }

    @Test
    public void testDeleteNotification() {
        long notificationId = 1L;
        NotificationEntity notification = new NotificationEntity();
        when(notiRepo.findById(anyLong())).thenReturn(Optional.of(notification));

        String result = notificationService.deleteNotification(notificationId);

        verify(notiRepo, times(1)).deleteById(notificationId);
        assertEquals("Notification deleted!", result);
    }

    @Test
    public void testOpenAllNotifications() {
        String userId = "admin@gmail.com";
        List<NotificationEntity> notifications = new ArrayList<>();
        NotificationEntity notification = new NotificationEntity();
        notification.setOpened(false);
        notifications.add(notification);
        when(notiRepo.findByUserRecever(anyString())).thenReturn(notifications);

        String result = notificationService.openAllNotifications(userId);

        verify(notiRepo, times(1)).findByUserRecever(userId);
        verify(notiRepo, times(1)).save(notification);
        assertEquals("Sve notifikacije su procitane.", result);
    }

    @Test
    public void testOpenedNotification() {
        Long notificationId = 1L;
        NotificationEntity notification = new NotificationEntity();
        notification.setOpened(false);
        when(notiRepo.findById(anyLong())).thenReturn(Optional.of(notification));

        String result = notificationService.openedNotification(notificationId);

        verify(notiRepo, times(1)).save(notification);
        assertEquals("Notifikacija je upravo procitana.", result);
    }

    @Test
    public void testDeleteUserNotis() {
        String userId = "admin@gmail.com";
        List<NotificationEntity> notifications = new ArrayList<>();
        NotificationEntity notification = new NotificationEntity();
        notifications.add(notification);
        when(notiRepo.findByUserRecever(anyString())).thenReturn(notifications);

        String result = notificationService.deleteUserNotis(userId);

        verify(notiRepo, times(1)).findByUserRecever(userId);
        verify(notiRepo, times(1)).deleteById(anyLong());
        assertEquals("Notifikacija je obrisana.", result);
    }
}