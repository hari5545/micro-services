package com.springsecurity.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTProvider {


	// Based on jks file only Generating and Validating jwt token
	
	   private KeyStore keyStore;

	    @PostConstruct
	    public void init() {
	        try {
	            keyStore = KeyStore.getInstance("JKS");
	            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
	            keyStore.load(resourceAsStream, "secret".toCharArray());
	        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
	            throw new IllegalArgumentException("Exception occured while loading keystore");
	        }

	    }

	    public String generateToken(Authentication authentication) {
	        User principal = (User) authentication.getPrincipal();
	        return Jwts.builder()
	                .setSubject(principal.getUsername())
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
	                .signWith(getPrivateKey())
	                .compact();
	    }

	    private PrivateKey getPrivateKey() {
	        try {
	            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
	        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
	            throw new IllegalArgumentException("Exception occured while retrieving public key from keystore");
	        }
	    }

	    public Boolean validateJWTToken(String jwtToken,UserDetails userDetails) {
	    	String username =getUserNameFromJWTToken(jwtToken);
	        return (username.equals(userDetails.getUsername()));
	    }

	    private PublicKey getPublickey() {
	        try {
	            return keyStore.getCertificate("springblog").getPublicKey();
	        } catch (KeyStoreException e) {
	            throw new IllegalArgumentException("Exception occured while retrieving public key from keystore");
	        }
	    }

	    public String getUserNameFromJWTToken(String token) {
	        Claims claims = Jwts.parser()
	                .setSigningKey(getPublickey())
	                .parseClaimsJws(token)
	                .getBody();

	        return claims.getSubject();
	    }
	  
	    
	    /*
	// default key
	private Key keyStore;

	@PostConstruct
	public void init() {

		keyStore =Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}
	public String generate(Authentication authentication) {
		User user=(User) authentication.getPrincipal();
		return Jwts
				.builder()
				.setSubject(user.getUsername())
				.signWith(keyStore)
				.compact();
	}

	public boolean validateJWTToken(String jwtToken) {

		Jwts.parser().setSigningKey(keyStore).parseClaimsJws(jwtToken);

		return true;
	}
	public String getUserNameFromJWTToken(String jwtToken) {
		Claims claims=Jwts.parser().setSigningKey(keyStore).parseClaimsJws(jwtToken).getBody();
		return claims.getSubject();
	}*/
}
