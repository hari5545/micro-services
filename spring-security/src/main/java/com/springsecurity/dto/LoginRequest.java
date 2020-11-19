package com.springsecurity.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginRequest {
	@NotEmpty(message = "username required")
	@NotNull(message = "username required") 
	protected String userName;
	
	
	@NotEmpty(message = "password required")
	@NotNull(message = "password required") 
	protected String password;
}
