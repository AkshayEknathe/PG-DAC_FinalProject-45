package com.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.Entity.Prescription;

public interface PrescriptionRepo extends JpaRepository<Prescription,Long> {

	Prescription findByName(String filename);

}
