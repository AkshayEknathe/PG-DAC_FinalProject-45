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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EmailScheduler {

    private static final Logger logger = LoggerFactory.getLogger(EmailScheduler.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppointmentService appointmentService;

    // Run every day at 7:00 AM
    @Scheduled(cron = "0 0 7 * * ?")
    public void fetchUserAndSendMail() {
        logger.info("Starting email scheduler job...");

        List<Appointment> appointments = appointmentService.getAppointmentsByDate(LocalDate.now());

        if (appointments == null || appointments.isEmpty()) {
            logger.info("No appointments scheduled for today.");
            return;
        }

        List<Appointment> acceptedAppointments = appointments.stream()
                .filter(appointment -> appointment.getStatus() != null &&
                        "ACCEPTED".equalsIgnoreCase(appointment.getStatus()))
                .collect(Collectors.toList());

        for (Appointment appointment : acceptedAppointments) {
            try {
                if (appointment.getDoctor() == null || appointment.getPatient() == null) {
                    logger.warn("Skipping appointment with missing doctor or patient info. ID: {}", appointment.getId());
                    continue;
                }

                String patientName = appointment.getPatient().getName();
                String patientEmail = appointment.getPatient().getEmail();
                String doctorName = appointment.getDoctor().getName();
                String doctorEmail = appointment.getDoctor().getEmail();

                if (patientEmail == null || doctorEmail == null) {
                    logger.warn("Skipping appointment due to missing email. ID: {}", appointment.getId());
                    continue;
                }

                // Email to patient
                String subjectPatient = "Reminder: Appointment with Dr. " + doctorName + " Today";
                String bodyPatient = String.format(
                        "Dear %s,\n\nThis is a reminder that you have a scheduled appointment with Dr. %s today (%s).\n\nRegards,\nHospital Team",
                        patientName, doctorName, LocalDate.now());

                // Email to doctor
                String subjectDoctor = "Reminder: Appointment with " + patientName + " Today";
                String bodyDoctor = String.format(
                        "Dear Dr. %s,\n\nYou have a scheduled appointment with patient %s today (%s).\n\nRegards,\nHospital Team",
                        doctorName, patientName, LocalDate.now());

                emailService.sendEmail(patientEmail, subjectPatient, bodyPatient);
                emailService.sendEmail(doctorEmail, subjectDoctor, bodyDoctor);

                logger.info("Emails sent successfully for appointment ID: {}", appointment.getId());

            } catch (Exception e) {
                logger.error("Failed to send email for appointment ID: " + appointment.getId(), e);
            }
        }

        logger.info("Email scheduler job completed.");
    }
}

