package com.app.Service;

import java.util.List;

import com.app.DTO.AppointmentDTO;
import com.app.DTO.UpdateAppointmentStatusDTO;
import com.app.Entity.Appointment;

public interface AppointmentService {

	// Appointment bookAppointment(BookAppointmentDTO dto);

	// List<Patient> getAppointmentsByDoctor(Long doctorId);

	Appointment updateAppointmentStatus(UpdateAppointmentStatusDTO dto);

	// List<AppointmentResponseDTO> getAppointmentsByUserId(Long userId);

	void bookAppointment(AppointmentDTO appointmentDTO);

	List<Appointment> findByPatientId(Long patientId);

	List<Appointment> getAppointmentsByDoctor(Long doctorId);

}
