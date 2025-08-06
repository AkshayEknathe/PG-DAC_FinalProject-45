package com.app.scheduler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.Entity.Appointment;
import com.app.Service.AppointmentService;
import com.app.Service.EmailService;

@Component
public class EmailScheduler {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	//@Scheduled(cron = "0 0/1 * * * ?") // Run every min(Demo)
	
	@Scheduled(cron = "0 0 7 * * ?") // Run every day at 7:00 am.
	public void fetchUserAndSendMail() {
		List<Appointment> appointments = appointmentService.getAppointmentsByDate(LocalDate.now());
		
		List<Appointment> acceptedAppointments = appointments.stream().
				filter(appointment -> "ACCEPTED".equalsIgnoreCase(appointment.getStatus())).
				collect(Collectors.toList());
		
		for(Appointment users : acceptedAppointments) {
			String patientName = users.getPatient().getName();
			String patientEmail = users.getPatient().getEmail();
			
			String docName = users.getDoctor().getName();
			String docEmail = users.getDoctor().getEmail();
			
			String subjectPatient = "Reminder for today's Appointment with Dr." + docName;
			String bodyPatinet = String.format("Dear %s\n\n You have a scheduled appointment with Dr. %s today. \n\nThank you,\nHospital Team", patientName, docName);
			
			String subjectDoc = "Reminder for your today's appointment with " + patientName;
			String bodyDoc = String.format("Dear Dr. %s,\n\n You have a scheduled appointment with %s today. \n\nThank you,\nHospital Team", docName, patientName);
			
			emailService.sendEmail(patientEmail, subjectPatient, bodyPatinet);
			emailService.sendEmail(docEmail, subjectDoc, bodyDoc);	
		}
	}

}
