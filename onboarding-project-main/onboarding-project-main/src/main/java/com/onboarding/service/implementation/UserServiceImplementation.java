package com.onboarding.service.implementation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onboarding.DTO.ResetPasswordDTO;
import com.onboarding.DTO.UserCreateDTO;
import com.onboarding.DTO.UserDTO;
import com.onboarding.DTO.UserEditDTO;
import com.onboarding.DTO.UserLoginDTO;
import com.onboarding.model.PasswordTokenEntity;
import com.onboarding.model.UserEntity;
import com.onboarding.repository.NotificationRepository;
import com.onboarding.repository.PasswordTokenRepository;
import com.onboarding.repository.RoleRepository;
import com.onboarding.repository.UserOnboardingRepository;
import com.onboarding.repository.UserRepository;
import com.onboarding.repository.UserTaskRepository;
import com.onboarding.service.EmailSenderService;
import com.onboarding.service.PDFGeneratorService;
import com.onboarding.service.TaskService;
import com.onboarding.service.UserService;
import com.onboarding.util.ApiException;
import com.onboarding.util.DTOConversions;
import com.onboarding.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private UserRepository repo;
	@Autowired
	
	private RoleRepository roleRepo;
	
	@Autowired 
	private TaskService taskService;
	
	@Autowired 
	private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private UserDetailsService userDetailsService;
	
//	@Autowired
//	AuthenticationConfiguration authConfig;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserOnboardingRepository uoRepo;
	
	@Autowired
	private UserTaskRepository utRepo;
	
	@Autowired
	private NotificationRepository notiRepo;
	
	@Autowired
	private PasswordTokenRepository pwTokenRepo;
	
	@Autowired
	private EmailSenderService mailSender;
	
	@Autowired
	private PDFGeneratorService pdfGeneratorService;
	

	
	@Override
		public UserDTO addUser(UserCreateDTO user) {
			try {
				UserEntity userEnt = DTOConversions.userCreateDTOToUser(user);
				userEnt.setRole((roleRepo.findById(user.getRole())).get());
				userEnt.setPassword(passwordEncoder.encode(user.getPassword()));
				return DTOConversions.userToDTO(repo.save(userEnt));
			}
			catch(Exception e) {
				log.error("User not added " + new Date());
				throw e;
			}
				
		}

	@Override
	public List<UserDTO> getUsers() {
		
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for(UserEntity user : (List<UserEntity>)repo.findAll()) {
			userDTOs.add(DTOConversions.userToDTO(user));
		}
		return userDTOs;
		
	}

	@Override
	public UserDTO editUser(String userId, UserEditDTO user){
		try {
			System.out.println(user.getAvatar());
			UserEntity userEnt = repo.findById(userId).orElseThrow(() -> new ApiException("User not found!", HttpStatus.NOT_FOUND.value()));
			userEnt.setFirstName(user.getFirstName());
			userEnt.setLastName(user.getLastName());
			userEnt.setPhone(user.getPhone());
			userEnt.setAvatar(user.getAvatar());
		
			
//			String fileName=StringUtils.cleanPath(user.getAvatar().getOriginalFilename());
//			if(fileName.contains("..")) {
//				System.out.println("not a valid file");
//			}
//			try {
//				String avatar = userEnt.getAvatar();
//				String newAvatar = Base64.getEncoder().encodeToString(user.getAvatar().getBytes());
//				if(!avatar.equals(newAvatar))
//					userEnt.setAvatar(newAvatar);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			
			return DTOConversions.userToDTO(repo.save(userEnt));
		}
		catch(Exception e) {
			log.error("User not edited " + new Date());
			throw e;
		}
		
	}

	@Override
	@Transactional
	public String deleteUser(String userId) {
		try {
			UserEntity user = repo.findById(userId).orElseThrow(() -> new ApiException("User not found!", HttpStatus.NOT_FOUND.value()));
			notiRepo.deleteAllByUserRecever(user.getEmail());
			uoRepo.deleteAllByUserEmail(user.getEmail());
			utRepo.deleteAllByUserEmail(user.getEmail());
			pwTokenRepo.deleteAllByUserEmail(user.getEmail());
			if(user != null)
				repo.deleteById(userId);
			return "User deleted!";
		}
		catch(Exception e) {
			log.error("User not deleted " + new Date());
			throw e;
		}
		
	}

	@Override
	public UserDTO getUser(String id) {
		UserEntity user = repo.findById(id).orElseThrow(() -> new ApiException("User not found!", HttpStatus.NOT_FOUND.value()));
		return DTOConversions.userToDTO(user);
	}

	@Override
	public String loginUser(UserLoginDTO user) throws Exception { 
		try {
//			UserEntity userEnt = repo.findById(user.getUsername()).orElseThrow(() -> new ApiException("Invalid username!", HttpStatus.NOT_FOUND.value()));
//			if(!userEnt.getPassword().equals(user.getPassword()))
//				throw new ApiException("Incorrect password!", HttpStatus.BAD_REQUEST.value());
//			return DTOConversions.userToDTO(userEnt);
			
			try {
				authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			} catch (DisabledException e) {
				throw new ApiException("User disabled!", HttpStatus.BAD_REQUEST.value());
			} catch (BadCredentialsException e) {
				throw new ApiException("Invalid credentials!", HttpStatus.BAD_REQUEST.value());
			}
			
			final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
			final String token = jwtTokenUtil.generateToken(userDetails);
//			log.error("OVO JE ERROR");
			return token;
		}
		catch(Exception e) {
			log.error("Logging unsuccessful " + new Date() );
			throw e;
		}
	}

	@Override
	public String resetPassword(ResetPasswordDTO resetDTO) {
		String uuid = resetDTO.getToken();
		System.out.println(resetDTO.getPassword());
		if(uuid != null) {
//			String res =  validatePwToken(uuid);
//			if(res != null)
//				return res;
			PasswordTokenEntity token = pwTokenRepo.findByToken(uuid).orElseThrow(() -> new ApiException("Invalid token!", HttpStatus.NOT_FOUND.value()));
			Boolean expired = tokenExpired(token);
			if(token != null && !expired) {
				UserEntity user = token.getUser();
				if(!resetDTO.getPassword().equals("")) {
					user.setPassword(passwordEncoder.encode(resetDTO.getPassword()));
					repo.save(user);
					return "Password changed successfully!";
				}
				throw new ApiException("Invalid password!", HttpStatus.BAD_REQUEST.value());
			}
			if(expired)
				throw new ApiException("Token expired!", HttpStatus.BAD_REQUEST.value());
			throw new ApiException("Invalid token!", HttpStatus.BAD_REQUEST.value());
		}else {
			generateToken(resetDTO.getEmail());
			return "Reset email sent";
		}
	}
	
	private void generateToken(String email) {
		UserEntity user = repo.findById(email).orElseThrow(() -> new ApiException("User not found!", HttpStatus.NOT_FOUND.value()));
		String uuid = UUID.randomUUID().toString();
		PasswordTokenEntity token = new PasswordTokenEntity(uuid, user, new Date(System.currentTimeMillis() + 3600 * 1000));
		pwTokenRepo.save(token);
		mailSender.sendEmail(user.getEmail(), "Reset password link", "http://localhost:3000/reset/" + uuid);
	}
	
//	private String validatePwToken(String uuid) {
//		PasswordTokenEntity token = pwTokenRepo.findByToken(uuid);
//		Boolean expired = tokenExpired(token);
//		if(token != null && !expired) return null;
//		if(expired) return "Token Expired!";
//		return "Invalid token";
//	}
	
	private Boolean tokenExpired(PasswordTokenEntity token) {
		Calendar cal = Calendar.getInstance();
		return token.getExpired().before(cal.getTime());
	}
	
	
//	@Scheduled(cron = "0 * * * * ?")
//	public void weeklyReport() throws IOException {
//		System.out.println(new Date() + "Scheduled print");
//		pdfGeneratorService.export();
//	}
	
	
//	private UserDTO convertToDTO(UserEntity user) {
//		UserDTO userDTO = new UserDTO();
//		
//		userDTO.setId(user.getId());
//		userDTO.setFirstName(user.getFirstName());
//		userDTO.setLastName(user.getLastName());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setRole(user.getRole());
//		userDTO.setPhone(user.getPhone());
//		
//				
////		userDTO.setTasks(user.getTasks());
//		if(!user.getTasks().isEmpty()) {
//			for(UserTask task : user.getTasks()) {
//				userDTO.addTask(convertToUToTDTO(task));
//			}
//		}
//		
////		userDTO.setOnboarding(user.getOnBoardings());
//		if(!user.getOnboardings().isEmpty()) {
//			for(UserOnboarding onboarding : user.getOnboardings()) {
//				userDTO.addOnboarding(convertUToOnBDTO(onboarding));
//			}
//		}
//		
//		return userDTO;
//	}
//	
//	private UserToTaskDTO convertToUToTDTO(UserTask userTask) {
//		UserToTaskDTO utotDTO = new UserToTaskDTO();
//		
//		utotDTO.setUser(userTask.getUser().getId());
//		utotDTO.setTask(userTask.getTask());
//		utotDTO.setCompleted(userTask.completed());
//		return utotDTO;
//	}
	
	
//	private UserToOnBoardingDTO convertUToOnBDTO(UserOnboarding userOb){	    
//		UserToOnBoardingDTO useronb = new UserToOnBoardingDTO();
//	    
//	    useronb.setUser(userOb.getUser().getId());
//		useronb.setOnboarding(userOb.getOnboarding());
//		useronb.setPercentage(userOb.getPercentage());
//		useronb.setCompleted(userOb.completed());
//	    
//	    return useronb;
//	
//	}
	 
	 
	 
//   ModelMapper modelMapper = new ModelMapper();
//
//	public UserDTO UserToUserDTO(UserEntity user) {
//		
//		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
//		
////		Type listType = new TypeToken<List<PostDTO>>(){}.getType();
////		List<PostDTO> postDtoList = modelMapper.map(postEntityList,listType);
//		
////		List<TaskDTO> listOfDTO = modelMapper.map(user.getTasks(), TaskDTO.class);
//		
//			
//		System.out.println(user.getTasks().get(0).getTask().getName());
//		System.out.println(userDTO.getFirstName());
//		
//		return userDTO;
//	}
	 
	 
	
}
