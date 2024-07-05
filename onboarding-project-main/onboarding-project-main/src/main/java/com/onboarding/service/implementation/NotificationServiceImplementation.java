package com.onboarding.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.onboarding.DTO.NotificationDTO;
import com.onboarding.model.NotificationEntity;
import com.onboarding.model.OnboardingEntity;
import com.onboarding.model.UserEntity;
import com.onboarding.model.UserOnboarding;
import com.onboarding.repository.NotificationRepository;
import com.onboarding.repository.OnboardingRepository;
import com.onboarding.repository.TaskRepository;
import com.onboarding.repository.UserOnboardingRepository;
import com.onboarding.repository.UserRepository;
import com.onboarding.service.NotificationService;
import com.onboarding.util.ApiException;
import com.onboarding.util.DTOConversions;


@Service
public class NotificationServiceImplementation implements NotificationService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private NotificationRepository notiRepo;
	
	@Autowired
	private OnboardingRepository onbrRepo;
	
	@Autowired
	private UserOnboardingRepository uoRepo;
	
	@Autowired
	private TaskRepository taskRepo;
	
	@Override
	public NotificationEntity addNotification(NotificationEntity noti) {
		return notiRepo.save(noti);		
		
	}

	@Override
	public String sendNotification(Long onboardingId, String userId, String title, String text) {
	    UserOnboarding thisUonbr = uoRepo.findByOnboardingIdAndUserEmail(onboardingId, userId);
	    Boolean isMod = thisUonbr.getIsMentor();
	    
	    //Notification
	    Date newD = new Date();
	    if(isMod == true) {
		    NotificationEntity newNoti = new NotificationEntity(text, newD, title, userId, true, false);
		    addNotification(newNoti);
	    }
	    else {
		    NotificationEntity newNoti = new NotificationEntity(text, newD, title, userId, false, false);
		    addNotification(newNoti);
	    }
	    deleteUserNotis(userId);
	    
		return "Poslata je notifikacija";
	}
	
	@Override
	public List<NotificationDTO> getNotifications(String id) {
		UserEntity user = userRepo.findById(id).get();
		String userName = user.getEmail();
		 List<NotificationDTO> notiDTOs = new ArrayList<NotificationDTO>();
		for(NotificationEntity noti : (List<NotificationEntity>)notiRepo.findAll()) {
			if(noti.getUserRecever().equals(userName)) {
				notiDTOs.add((DTOConversions.convertToDTO(noti)));
			}
		}
		
		return notiDTOs;
	}

	@Override
	public NotificationDTO getNotification(long id) {
		NotificationEntity notification = notiRepo.findById(id).orElseThrow(() -> new ApiException("Notification not found!", HttpStatus.NOT_FOUND.value()));
		return DTOConversions.convertToDTO(notification);
	}

	@Override
	public NotificationEntity editNotification(long id, NotificationDTO noti) {
		NotificationEntity oldNoti = notiRepo.findById(id).get();	
		oldNoti.setId(noti.getId());
		oldNoti.setText(noti.getText());
		oldNoti.setTitle(noti.getTitle());
		oldNoti.setDate(noti.getDate());
		oldNoti.setIsMentor(noti.getIsMentor());
		oldNoti.setUserRecever(noti.getUserRecever());
		oldNoti.setOpened(noti.getOpened());
		
		return notiRepo.save(oldNoti);
	}

	@Override
	public String deleteNotification(long id) {
		NotificationEntity notification = notiRepo.findById(id).orElseThrow(() -> new ApiException("Notification not found!", HttpStatus.NOT_FOUND.value()));
		if(notification != null)
			notiRepo.deleteById(id);
		return "Notification deleted!";
	}

	@Override
	public String openAllNotifications(String id) {
		List<NotificationEntity> notis = notiRepo.findByUserRecever(id);
		for(NotificationEntity noti : notis) {
			if(noti.getOpened() == null) {
				noti.setOpened(false);
			}
			if(noti.getOpened() == false) {
				noti.setOpened(true);
				notiRepo.save(noti);
			}
		}
		
		return "Sve notifikacije su procitane.";
	}
	
	@Override
	public String openedNotification(Long id) {
		NotificationEntity notification = notiRepo.findById(id).get();
		if(notification.getOpened() == null) {
			notification.setOpened(false);
		}
		if(notification.getOpened() == false) {
			notification.setOpened(true);
			notiRepo.save(notification);
		}
		else {
			return "Notifikacija je vec bila procitana.";
		}
		
		return "Notifikacija je upravo procitana.";
	}
	
	@Override	
	public String deleteUserNotis(String userId) {

		List<NotificationEntity> notiz = notiRepo.findByUserRecever(userId);
		int number = notiz.size();
		if(number >= 20) {
			for(NotificationEntity notiEnt : notiz) {
				if(number == 20)
					break;
				else {
					number--;
					deleteNotification(notiEnt.getId());
				}
			}
		}
		
		return "Notifikacija je obrisana.";
	}
}
