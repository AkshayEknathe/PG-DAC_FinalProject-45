package com.app.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.Entity.Doctor;
import com.app.Entity.Patient;
import com.app.Entity.User;
import com.app.Repository.DoctorRepository;
import com.app.Repository.PatientRepository;
import com.app.Repository.UserRepository;

@Service // or @Component also works!
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
	// dep : user repository : based upon spring data JPA
//	@Autowired
//	private CustomerReopository userRepo;
//
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		System.out.println("in load by user nm " + email);
//
//		Customer user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid Email ID "));
//		System.out.println(user.getName());
//		System.out.println("lifted user dtls from db " + user);
//		return new CustomerUserDetails(user);
//	}
	
	 @Autowired
	    private UserRepository customerRepository;

	    @Autowired
	    private DoctorRepository doctorRepository;
	    
	    @Autowired
	    private PatientRepository patientRepository;

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        // Check in Customer entity
	        Optional<User> customer = customerRepository.findByEmail(email);
	        if (customer.isPresent()) {
	        	System.out.println(customer.get().getName());
	            return new CustomerUserDetails(customer.get());
	        }

	        // Check in Doctor entity
	        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
	        if (doctor.isPresent()) {
	        	System.out.println(doctor.get().getName());
	            return new DoctorUserDetails(doctor.get());
	        }
	        
	        // Check in Patient entity
	        Optional<Patient> patient = patientRepository.findByEmail(email);
	        if (patient.isPresent()) {
	        	System.out.println(patient.get().getName());
	            return new PatientUserDetails(patient.get());
	        }

	        // If neither found
	        throw new UsernameNotFoundException("User not found with email: " + email);
	    }

}
