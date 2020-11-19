package com.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestingController {
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(path="/get")
	public String getUser() {
		return "user";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(path="/admin")
	public String getAdmin() {
		return "admin";
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping(path="/all")
	public String all() {
		return "all";
	}
	

}
