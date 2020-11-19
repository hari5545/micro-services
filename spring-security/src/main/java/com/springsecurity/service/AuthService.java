package com.springsecurity.service;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.dto.JWTTokenResponse;
import com.springsecurity.dto.LoginRequest;
import com.springsecurity.dto.RegistrationRequestDto;
import com.springsecurity.modal.User;
import com.springsecurity.modal.UserRoles;
import com.springsecurity.repo.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JWTProvider jwtProvider;
	private AuthenticationManager authenticationManager;

	public String signUp( RegistrationRequestDto registrationRequestDto) {
		try{
			Set<UserRoles> roleSet=new HashSet<>();
			UserRoles userRoles=new UserRoles();
			userRoles.setRoleName("USER");
			userRoles.setRoleType("app");
			roleSet.add(userRoles);
			User user=new User();
			user.setUserName(registrationRequestDto.getUserName());
			user.setPassword(passwordEncode(registrationRequestDto.getPassword()));
			user.setStatus("active");
			user.setUserRoles(roleSet);
			userRepository.save(user);
			return "user created sucessfully";
		}catch (Exception e) {
			return "user not created";
		}
	}
	private String passwordEncode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	public JWTTokenResponse login(@Valid LoginRequest loginRequest) {
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token=jwtProvider.generateToken(authentication);
		return new JWTTokenResponse(loginRequest.getUserName(), token);
	}

	public org.springframework.security.core.userdetails.User getCurrentUser(){
		org.springframework.security.core.userdetails.User currentUser=(org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return currentUser;
	}
}
