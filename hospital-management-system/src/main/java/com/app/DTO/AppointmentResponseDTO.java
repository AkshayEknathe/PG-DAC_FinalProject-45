package com.app.DTO;

import java.time.LocalDate;

public class AppointmentResponseDTO {
    private Long id;
    private LocalDate appointmentDate;
    private String diseaseDescription;
    private String status; // PENDING, ACCEPTED, REJECTED
    private String doctorName;
    private String doctorSpecialization;
    private String doctorPhone;
    private double amount;
    private String paymentStatus;

    // Constructors
    public AppointmentResponseDTO() {}

    public AppointmentResponseDTO(Long id, LocalDate appointmentDate, String diseaseDescription, String status,
                                  String doctorName, String doctorSpecialization, String doctorPhone,double amount,String paymentstatus) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.diseaseDescription = diseaseDescription;
        this.status = status;
        this.doctorName = doctorName;
        this.doctorSpecialization = doctorSpecialization;
        this.doctorPhone = doctorPhone;
        this.amount = amount;
        this.paymentStatus=paymentstatus;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getDiseaseDescription() { return diseaseDescription; }
    public void setDiseaseDescription(String diseaseDescription) { this.diseaseDescription = diseaseDescription; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getDoctorSpecialization() { return doctorSpecialization; }
    public void setDoctorSpecialization(String doctorSpecialization) { this.doctorSpecialization = doctorSpecialization; }

    public String getDoctorPhone() { return doctorPhone; }
    public void setDoctorPhone(String doctorPhone) { this.doctorPhone = doctorPhone; }

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	
    
    
}
