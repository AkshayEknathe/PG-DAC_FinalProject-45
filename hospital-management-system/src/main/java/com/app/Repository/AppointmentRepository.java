package com.app.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.DTO.PatientDTO;
import com.app.Entity.Appointment;
import com.app.Entity.Patient;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	List<Appointment> findByPatient_Id(Long patientId);
	
	List<Appointment> findByDoctor_Id(Long doctorId);





}
