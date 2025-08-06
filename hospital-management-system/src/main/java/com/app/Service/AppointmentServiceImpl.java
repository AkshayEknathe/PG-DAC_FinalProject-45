package com.app.Service;

@Autowired
private MailService mailService;

public class AppointmentServiceImpl implements AppointmentService {


public Appointment updateAppointmentStatus(UpdateAppointmentStatusDTO dto) {
	Optional<Appointment> optionalAppointment = appointmentRepository.findById(dto.getAppointmentId());

    if (!optionalAppointment.isPresent()) {
        throw new RuntimeException("Appointment not found with ID: " + dto.getAppointmentId());
    }

    Appointment appointment = optionalAppointment.get();
    String status = dto.getStatus().toUpperCase();
    appointment.setStatus(status);
    Appointment updatedAppointment = appointmentRepository.save(appointment);

    // 🔁 Extract common details
    Patient patient = appointment.getPatient();
    Doctor doctor = appointment.getDoctor();

    String patientEmail = patient.getEmail();
    String patientName = patient.getName();
    String doctorEmail = doctor.getEmail();
    String doctorName = doctor.getName();
    String date = appointment.getAppointmentDate().toString();

    //  Email to patient
    if (status.equals("ACCEPTED")) {
        mailService.sendSimpleEmail(
            patientEmail,
            "Appointment Accepted",
            "Dear " + patientName + ",\n\nYour appointment with Dr. " + doctorName +
            " on " + date + " has been ACCEPTED.\n\nThank you!"
        );

        //  Email to doctor
        mailService.sendSimpleEmail(
            doctorEmail,
            "Appointment Confirmed",
            "Dear Dr. " + doctorName + ",\n\nYou have accepted an appointment with patient " +
            patientName + " on " + date + ".\n\nPlease be available on time."
        );
    } else if (status.equals("REJECTED")) {
        mailService.sendSimpleEmail(
            patientEmail,
            "Appointment Rejected",
            "Dear " + patientName + ",\n\nWe regret to inform you that your appointment with Dr. " +
            doctorName + " on " + date + " has been REJECTED.\n\nPlease try booking another slot."
        );
    }

    return updatedAppointment;
}
}
