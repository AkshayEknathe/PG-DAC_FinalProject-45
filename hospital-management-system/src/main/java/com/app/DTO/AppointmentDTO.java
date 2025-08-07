package com.app.DTO;

import java.time.LocalDate;

public class AppointmentDTO {
    private Long patientId;
    private Long doctorId;
    private LocalDate appointmentDate;
    private String diseaseDescription;
    
    // Getters and Setters
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getDiseaseDescription() { return diseaseDescription; }
    public void setDiseaseDescription(String diseaseDescription) { this.diseaseDescription = diseaseDescription; }
}
