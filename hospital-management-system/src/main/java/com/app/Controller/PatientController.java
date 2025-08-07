package com.app.Controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.AppointmentDTO;
import com.app.DTO.AppointmentResponseDTO;
import com.app.DTO.DoctorDtoImage;
import com.app.DTO.PatientDTO;
import com.app.DTO.PaymentRequestDTO;
import com.app.Entity.Appointment;
import com.app.Entity.Payment;
import com.app.Service.AppointmentService;
import com.app.Service.DoctorService;
import com.app.Service.PatientService;
import com.app.Service.PaymentService;
import com.app.Service.UserService;

@RestController
@RequestMapping("/patient")
@CrossOrigin("http://localhost:3000")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private UserService userService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/registerPatient")
	public ResponseEntity<String> registerPatient(@RequestBody PatientDTO patientDTO) {
		patientDTO.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
		patientService.registerPatient(patientDTO);
		return ResponseEntity.ok("Patient registered successfully!");
	}

	@PostMapping("/bookAppointment")
	public ResponseEntity<String> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		appointmentService.bookAppointment(appointmentDTO);
		return ResponseEntity.ok("Appointment booked successfully!");
	}

	@GetMapping("/getDoctorsBySpecialization/{specializationId}")
	public ResponseEntity<List<DoctorDtoImage>> getDoctorsBySpecialization(@PathVariable Long specializationId) {
		List<DoctorDtoImage> doctors = doctorService.getDoctorsBySpecializationId(specializationId);

		if (doctors.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}

		return ResponseEntity.ok(doctors);
	}

	@GetMapping("/getAppointmentsByPatientId/{patientId}")
	public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatientId(@PathVariable Long patientId) {
		List<Appointment> appointments = appointmentService.findByPatientId(patientId);

		if (appointments.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<AppointmentResponseDTO> responseDTOs = appointments.stream()
				.map(appointment -> new AppointmentResponseDTO(appointment.getId(), appointment.getAppointmentDate(),
						appointment.getDiseaseDescription(), appointment.getStatus(),
						(appointment.getDoctor() != null) ? appointment.getDoctor().getName() : null,
						(appointment.getDoctor() != null && appointment.getDoctor().getSpecialization() != null)
								? appointment.getDoctor().getSpecialization().getName()
								: null,
						(appointment.getDoctor() != null) ? appointment.getDoctor().getPhone() : null,
						(appointment.getDoctor() != null) ? appointment.getDoctor().getAmount() : null,
						(appointment.getPayment() != null) ? appointment.getPayment().getPaymentstatus() : null))
				.collect(Collectors.toList());

		return ResponseEntity.ok(responseDTOs);
	}

	@PostMapping("/makePayment")
	public ResponseEntity<Payment> makePayment(@RequestBody PaymentRequestDTO dto) {
		try {
			Payment payment = paymentService.processPayment(dto);
			return ResponseEntity.ok(payment);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
