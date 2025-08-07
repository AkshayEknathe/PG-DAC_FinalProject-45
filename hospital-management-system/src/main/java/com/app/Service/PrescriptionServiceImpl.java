package com.app.Service;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.Entity.Prescription;
import com.app.Repository.PrescriptionRepo;



@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	private PrescriptionRepo drepo;

	@Override
	public Prescription savePrescription(Prescription prescription) {
		return drepo.save(prescription);
		
	}

	@Override
	public Prescription findfile(String filename) {
		
		return drepo.findByName(filename);
	}
}
