package com.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.PatientDTO;
import com.app.Entity.Patient;
import com.app.Service.PatientService;

@RestController
@RequestMapping("/receptionist")
@CrossOrigin("http://localhost:3000")
public class ReceptionistController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PatientService  patientService;

	@PostMapping("/registerPatient")
	public ResponseEntity<String> registerPatient(@RequestBody PatientDTO patientDTO) {
		patientDTO.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
		patientService.registerPatient(patientDTO);
		return ResponseEntity.ok("Patient registered successfully!");
	}
	
	@GetMapping("/getAllPatients")
	public ResponseEntity<List<Patient>> getAllPatients() {
	    List<Patient> patients = patientService.getAllPatients();
	    
	    if (patients.isEmpty()) {
	        return ResponseEntity.noContent().build(); // Return 204 No Content if no patients found
	    }
	    
	    return ResponseEntity.ok(patients);
	}

}
