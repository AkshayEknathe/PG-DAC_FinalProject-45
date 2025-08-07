package com.app.Service;

import java.util.List;

import com.app.DTO.PatientDTO;
import com.app.Entity.Patient;

public interface PatientService {

	void registerPatient(PatientDTO patientDTO);

	List<Patient> getAllPatients();

	
}
