package com.app.Controller;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.DTO.DoctorAppointmentResponseDTO;
import com.app.DTO.DoctorDtoImage;
import com.app.DTO.UpdateAppointmentStatusDTO;
import com.app.Entity.Appointment;
import com.app.Entity.Doctor;
import com.app.Service.AppointmentService;
import com.app.Service.DoctorService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/doctor")
@CrossOrigin("http://localhost:3000")
@Slf4j
public class DoctorController {

	@Autowired
	private AppointmentService appointmentService;
	
	 private DoctorService doctorService;
	 
	 @GetMapping("/getDoctorAppointments/{doctorId}")
	 public ResponseEntity<List<DoctorAppointmentResponseDTO>> getDoctorAppointments(@PathVariable Long doctorId) {
	     List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);

	     if (appointments.isEmpty()) {
	         return ResponseEntity.noContent().build();
	     }

	     List<DoctorAppointmentResponseDTO> responseDTOs = appointments.stream().map(appointment -> 
	         new DoctorAppointmentResponseDTO(
	             appointment.getId(),
	             appointment.getAppointmentDate(),
	             appointment.getDiseaseDescription(),
	             appointment.getStatus(),
	             appointment.getPatient().getId(),
	             appointment.getPatient().getName(),
	             appointment.getPatient().getAge(),
	             appointment.getPatient().getGender(),
	             appointment.getPatient().getWeight(),
	             appointment.getPatient().getEmail()
	         )
	     ).collect(Collectors.toList());

	     return ResponseEntity.ok(responseDTOs);
	 }


	 
	 @PutMapping("/updateStatus")
	    public ResponseEntity<Appointment> updateAppointmentStatus(@RequestBody UpdateAppointmentStatusDTO dto) {
	        try {
	            Appointment updatedAppointment = appointmentService.updateAppointmentStatus(dto);
	            return ResponseEntity.ok(updatedAppointment);
	        } catch (RuntimeException e) {
	            e.printStackTrace(); // Logs the full stack trace to the console

	            return ResponseEntity.badRequest().body(null);
	        }
	    }
}

