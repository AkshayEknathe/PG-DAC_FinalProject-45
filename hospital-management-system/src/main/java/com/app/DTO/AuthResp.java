package com.app.DTO;


import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@NoArgsConstructor
//@AllArgsConstructor
public class AuthResp {
	private String message;
	private String jwt;
	private Authentication authenticatedDetails;
	
	
	public AuthResp() {
		
	}
	public AuthResp(String message, String jwt, Authentication authenticatedDetails) {
	
		this.message = message;
		this.jwt = jwt;
		this.authenticatedDetails = authenticatedDetails;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public Authentication getAuthenticatedDetails() {
		return authenticatedDetails;
	}
	public void setAuthenticatedDetails(Authentication authenticatedDetails) {
		this.authenticatedDetails = authenticatedDetails;
	}
	
	
}
