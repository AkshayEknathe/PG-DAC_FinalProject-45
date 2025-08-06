package com.app.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.Entity.Appointment;
import com.app.Service.EmailService;

@Aspect
@Component
public class EmailAspect {

	@Autowired
	private EmailService emailService;

	@AfterReturning(pointcut = "execution(* com.app.Service.AppointmentServiceImpl.updateAppointmentStatus(..))", returning = "updatedAppointment")
	public void mailSender(JoinPoint joinPoint, Appointment updatedAppointment) {

		if (updatedAppointment != null) {
			String patientEmail = updatedAppointment.getPatient().getEmail();

			String patientName = updatedAppointment.getPatient().getName();
			String appointmentDate = updatedAppointment.getAppointmentDate().toString();

			String doctorEmail = updatedAppointment.getDoctor().getEmail();

			String doctorName = updatedAppointment.getDoctor().getName();

			if ("ACCEPTED".equalsIgnoreCase(updatedAppointment.getStatus())) {
				// Email to Patient
				String subjectPatient = "Appointment Confirmed – " + appointmentDate;
				String bodyPatient = String.format(
						"Dear %s,\n\nYour appointment on %s has been successfully confirmed.\n\nThank you,\nHospital Team",
						patientName, appointmentDate);
				emailService.sendEmail(patientEmail, subjectPatient, bodyPatient);

				// Email to Doctor
				String subjectDoctor = "New Appointment Scheduled – " + appointmentDate;
				String bodyDoctor = String.format(
						"Dear Dr. %s,\n\nYou have a new confirmed appointment with patient %s on %s.\nPlease log in to your dashboard for more details.\n\nThank you,\nHospital Team",
						doctorName, patientName, appointmentDate);
				emailService.sendEmail(doctorEmail, subjectDoctor, bodyDoctor);

			} else if ("REJECTED".equalsIgnoreCase(updatedAppointment.getStatus())) {
				// Email to Patient only
				String subject = "Appointment Rejected";
				String body = String.format(
						"Dear %s,\n\nWe regret to inform you that your appointment on %s has been rejected due to unavailability.\nPlease try rescheduling.\n\nThank you,\nHospital Team",
						patientName, appointmentDate);
				emailService.sendEmail(patientEmail, subject, body);
			}
		}

	}

}