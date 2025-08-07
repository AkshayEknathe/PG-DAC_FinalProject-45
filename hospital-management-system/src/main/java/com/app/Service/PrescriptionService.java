package com.app.Service;

import com.app.Entity.Prescription;

public interface PrescriptionService {

	Prescription savePrescription(Prescription prescription);

	Prescription findfile(String filename);

}