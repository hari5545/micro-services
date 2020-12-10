package com.authservices.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public PasswordEncoder passwordEncoder;
	@Autowired
	public UserDetailsService userDetailsService;
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.csrf().disable().exceptionHandling()
		.authenticationEntryPoint(
				(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
		.and()
		 .authorizeRequests()
		 .antMatchers("/api/auth/**")
		 .authenticated()
		 .anyRequest().permitAll().and().httpBasic();
	}
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// In memory authentication 
		//auth.inMemoryAuthentication().withUser("hari").password(passwordEncoder.encode("hari")).roles("USER");
		// Database authentication
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
}
