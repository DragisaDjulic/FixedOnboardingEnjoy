package com.onboarding.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onboarding.DTO.UserLoginDTO;
import com.onboarding.model.UserEntity;
import com.onboarding.repository.UserRepository;
import com.onboarding.util.ApiException;
import com.onboarding.util.JwtTokenUtil;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
//	@Autowired 
//	private AuthenticationManager authManager;
//	
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepo.findById(username).orElseThrow(() -> new ApiException("User not found!", HttpStatus.NOT_FOUND.value()));
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().getPermission().getName()));
		return new User(user.getEmail(), user.getPassword(), authorities);
	}
	
//	
//	public String loginUser(UserLoginDTO user) throws Exception {
////		UserEntity userEnt = repo.findById(user.getUsername()).orElseThrow(() -> new ApiException("Invalid username!", HttpStatus.NOT_FOUND.value()));
////		if(!userEnt.getPassword().equals(user.getPassword()))
////			throw new ApiException("Incorrect password!", HttpStatus.BAD_REQUEST.value());
////		return DTOConversions.userToDTO(userEnt);
//		
//		try {
//			authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//		} catch (DisabledException e) {
//			throw new Exception("USER_DISABLED", e);
//		} catch (BadCredentialsException e) {
//			throw new Exception("INVALID_CREDENTIALS", e);
//		}
//		
//		final UserDetails userDetails = loadUserByUsername(user.getUsername());
//		final String token = jwtTokenUtil.generateToken(userDetails);
//		return token;
//	}

}
