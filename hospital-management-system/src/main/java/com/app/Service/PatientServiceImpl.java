package com.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.DTO.PatientDTO;
import com.app.Entity.Patient;
import com.app.Entity.Role;
import com.app.Repository.PatientRepository;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public void registerPatient(PatientDTO patientDTO) {
		Patient patient = new Patient();
		patient.setName(patientDTO.getName());
		patient.setAge(patientDTO.getAge());
		patient.setGender(patientDTO.getGender());
		patient.setWeight(patientDTO.getWeight());
		patient.setEmail(patientDTO.getEmail());
		patient.setPassword(patientDTO.getPassword()); // Encrypt password
		patient.setRole(Role.ROLE_PATIENT); // Set default role as PATIENT

		patientRepository.save(patient);
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}
	
	

}
