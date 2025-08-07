package com.app.AOP;

import java.time.format.DateTimeFormatter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.Entity.Appointment;
import com.app.Service.EmailService;

@Aspect
@Component
public class EmailAspect {

    private static final Logger logger = LoggerFactory.getLogger(EmailAspect.class);

    @Autowired
    private EmailService emailService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    @AfterReturning(
        pointcut = "execution(* com.app.Service.AppointmentServiceImpl.updateAppointmentStatus(..))",
        returning = "updatedAppointment"
    )
    public void sendEmailAfterStatusUpdate(JoinPoint joinPoint, Appointment updatedAppointment) {

        if (updatedAppointment == null) {
            logger.warn("Updated appointment is null, skipping email sending.");
            return;
        }

        String status = updatedAppointment.getStatus().toUpperCase();
        String appointmentDate = updatedAppointment.getAppointmentDate().format(formatter);

        String patientName = updatedAppointment.getPatient().getName();
        String patientEmail = updatedAppointment.getPatient().getEmail();

        String doctorName = updatedAppointment.getDoctor().getName();
        String doctorEmail = updatedAppointment.getDoctor().getEmail();

        logger.info("Triggering email for appointment status: {}", status);

        switch (status) {
            case "ACCEPTED":
                sendAcceptedEmails(patientName, patientEmail, doctorName, doctorEmail, appointmentDate);
                break;

            case "REJECTED":
                sendRejectedEmail(patientName, patientEmail, doctorName, appointmentDate);
                break;

            default:
                logger.info("No email template configured for status: {}", status);
        }
    }

    private void sendAcceptedEmails(String patientName, String patientEmail, String doctorName, String doctorEmail, String date) {
        // Email to patient
        String subjectPatient = "✅ Appointment Confirmed – " + date;
        String bodyPatient = String.format(
            "Dear %s,\n\nYour appointment with Dr. %s on %s has been confirmed.\n\nRegards,\nHospital Team",
            patientName, doctorName, date
        );
        emailService.sendEmail(patientEmail, subjectPatient, bodyPatient);

        // Email to doctor
        String subjectDoctor = " New Appointment Scheduled – " + date;
        String bodyDoctor = String.format(
            "Dear Dr. %s,\n\nYou have a new confirmed appointment with patient %s on %s.\nPlease login to your dashboard for more info.\n\nThanks,\nHospital Team",
            doctorName, patientName, date
        );
        emailService.sendEmail(doctorEmail, subjectDoctor, bodyDoctor);
    }

    private void sendRejectedEmail(String patientName, String patientEmail, String doctorName, String date) {
        String subject = "❌ Appointment Rejected – " + date;
        String body = String.format(
            "Dear %s,\n\nUnfortunately, your appointment with Dr. %s on %s has been rejected.\nPlease try booking another slot.\n\nRegards,\nHospital Team",
            patientName, doctorName, date
        );
        emailService.sendEmail(patientEmail, subject, body);
    }
}
