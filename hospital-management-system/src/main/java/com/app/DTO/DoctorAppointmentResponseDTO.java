package com.app.DTO;
import java.time.LocalDate;

public class DoctorAppointmentResponseDTO {
	private Long appointmentId;
    private LocalDate appointmentDate;
    private String diseaseDescription;
    private String status; // PENDING, ACCEPTED, REJECTED
    private Long patientId;
    private String patientName;
    private int patientAge;
    private String patientGender;
    private double patientWeight;
    private String patientEmail;

    // Constructors
    public DoctorAppointmentResponseDTO() {}

    public DoctorAppointmentResponseDTO(Long appointmentId, LocalDate appointmentDate, String diseaseDescription, 
                                        String status, Long patientId, String patientName, int patientAge, 
                                        String patientGender, double patientWeight, String patientEmail) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.diseaseDescription = diseaseDescription;
        this.status = status;
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
        this.patientWeight = patientWeight;
        this.patientEmail = patientEmail;
    }

    // Getters and Setters
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getDiseaseDescription() { return diseaseDescription; }
    public void setDiseaseDescription(String diseaseDescription) { this.diseaseDescription = diseaseDescription; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public int getPatientAge() { return patientAge; }
    public void setPatientAge(int patientAge) { this.patientAge = patientAge; }

    public String getPatientGender() { return patientGender; }
    public void setPatientGender(String patientGender) { this.patientGender = patientGender; }

    public double getPatientWeight() { return patientWeight; }
    public void setPatientWeight(double patientWeight) { this.patientWeight = patientWeight; }

    public String getPatientEmail() { return patientEmail; }
    public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }
}
