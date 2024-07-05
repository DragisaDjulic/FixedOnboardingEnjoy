package com.onboarding.service;

import java.util.List;
import com.onboarding.DTO.NotificationDTO;
import com.onboarding.model.NotificationEntity;


public interface NotificationService {

    public NotificationEntity addNotification(NotificationEntity noti);
	
	public List<? extends NotificationDTO> getNotifications(String string);
	
	public NotificationDTO getNotification(long id);
	
	public NotificationEntity editNotification(long id, NotificationDTO noti);
	
	public String deleteNotification(long id);
	
	public String deleteUserNotis(String userId);
	
	public String sendNotification(Long onboardingId, String userId, String title, String text);

	public String openedNotification(Long id);

	public String openAllNotifications(String id);
}
