package com.springsecurity.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.dto.JWTTokenResponse;
import com.springsecurity.dto.LoginRequest;
import com.springsecurity.dto.RegistrationRequestDto;
import com.springsecurity.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path="/api/auth")
@AllArgsConstructor
public class AuthController {

	private AuthService authService;
	
	@PostMapping(path="/signUp",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> signUp(@RequestBody @Valid RegistrationRequestDto registrationRequestDto){
		String resp=authService.signUp(registrationRequestDto);
		return new ResponseEntity<String>(resp, HttpStatus.CREATED);
	}
	
	@PostMapping(path="/login",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JWTTokenResponse> login(@RequestBody @Valid LoginRequest loginRequest){
		JWTTokenResponse token=authService.login(loginRequest);
		return new ResponseEntity<JWTTokenResponse>(token,HttpStatus.OK);
	}
}
	
