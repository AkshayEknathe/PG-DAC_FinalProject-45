package com.app.Service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.Entity.Doctor;
import com.app.Entity.Patient;

import lombok.ToString;

@SuppressWarnings("serial")
@ToString
public class PatientUserDetails implements UserDetails {
	 private Patient patient;

	 public PatientUserDetails(Patient patient) {
		    if (patient == null) {
		        throw new IllegalArgumentException("patient cannot be null");
		    }
		    this.patient = patient;
		}


	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return Arrays.asList(new SimpleGrantedAuthority(patient.getRole().name()));
	    }

	    @Override
	    public String getPassword() {
	        return patient.getPassword();
	    }

	    @Override
	    public String getUsername() {
	        return patient.getEmail();
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

	    public Long getId() {
	        return patient.getId();
	    }

	    public String getName() {
	        return patient.getName();
	    }

	    public String getRole() {
	        return patient.getRole().name();
	    }
}
