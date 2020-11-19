package com.springsecurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springsecurity.service.JWTProvider;

@Component
public class JWTValidationFilter extends OncePerRequestFilter{

	@Autowired
	private JWTProvider jwtProvider;

	@Autowired
	private UserDetailsService userDetailService;

	@Override 
	protected void doFilterInternal(HttpServletRequest request,	HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
		// get JWT token from request 
		String	jwtToken=getJwtToken(request);
		System.out.println(jwtToken);

		if(StringUtils.hasText(jwtToken) && SecurityContextHolder.getContext().getAuthentication() == null ) { 
			//get username from token 
			String userName=jwtProvider.getUserNameFromJWTToken(jwtToken);
			System.out.println(userName); 
			UserDetails userDetails=userDetailService.loadUserByUsername(userName);
			System.out.println(userDetails); 
			if (jwtProvider.validateJWTToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				System.out.println(authentication);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		}
		filterChain.doFilter(request, response);
	}

	private String getJwtToken(HttpServletRequest request) {
		String	jwtToken=request.getHeader("Authorization");
		System.out.println("jwtToken"+jwtToken);
		if(jwtToken!=null && jwtToken.startsWith("Bearer ")){ 
			jwtToken=jwtToken.substring(7);
		} return	jwtToken;
	}

}