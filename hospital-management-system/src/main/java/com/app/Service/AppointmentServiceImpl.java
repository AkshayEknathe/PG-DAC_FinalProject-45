package com.app.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.DTO.AppointmentDTO;
import com.app.DTO.UpdateAppointmentStatusDTO;
import com.app.Entity.Appointment;
import com.app.Entity.Doctor;
import com.app.Entity.Patient;
import com.app.Repository.AppointmentRepository;
import com.app.Repository.DoctorRepository;
import com.app.Repository.PatientRepository;
import com.app.Repository.UserRepository;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private UserRepository userRepository;

	public void bookAppointment(AppointmentDTO appointmentDTO) {
		Optional<Patient> patientOpt = patientRepository.findById(appointmentDTO.getPatientId());
		Optional<Doctor> doctorOpt = doctorRepository.findById(appointmentDTO.getDoctorId());

		if (patientOpt.isPresent() && doctorOpt.isPresent()) {
			Appointment appointment = new Appointment();
			appointment.setPatient(patientOpt.get());
			appointment.setDoctor(doctorOpt.get());
			appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
			appointment.setDiseaseDescription(appointmentDTO.getDiseaseDescription());
			appointment.setStatus("PENDING");

			appointmentRepository.save(appointment);
		} else {
			throw new RuntimeException("Invalid Patient or Doctor ID");
		}
	}

	public List<Appointment> findByPatientId(Long patientId) {
		return appointmentRepository.findByPatient_Id(patientId);
	}

	public Appointment updateAppointmentStatus(UpdateAppointmentStatusDTO dto) {
		Optional<Appointment> optionalAppointment = appointmentRepository.findById(dto.getAppointmentId());

//		if (!optionalAppointment.isPresent()) {
//			throw new RuntimeException("Appointment not found with ID: " + dto.getAppointmentId());
//		}

		Appointment appointment = optionalAppointment.get();

//		// Validate the status
//		if (!dto.getStatus().equalsIgnoreCase("ACCEPTED") && !dto.getStatus().equalsIgnoreCase("REJECTED")) {
//			throw new RuntimeException("Invalid status! Allowed values: ACCEPTED, REJECTED");
//		}

		// Update status
		appointment.setStatus(dto.getStatus().toUpperCase());
		return appointmentRepository.save(appointment);
	}

	public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
		return appointmentRepository.findByDoctor_Id(doctorId);
	}

}
