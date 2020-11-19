package com.springsecurity.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecurity.modal.User;
import com.springsecurity.modal.UserRoles;
import com.springsecurity.repo.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

	
	private UserRepository userRepository; 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUserName(username).orElseThrow(() ->new UsernameNotFoundException("userName not found"));
		System.out.println("user object" +user);
		return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),true, true, true, true,getAuthorities(user.getUserRoles()));
	}
	private Collection<? extends GrantedAuthority> getAuthorities(Set<UserRoles> list) {
		return list.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toList());
    }
}
