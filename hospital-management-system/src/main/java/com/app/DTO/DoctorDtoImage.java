package com.app.DTO;
import java.util.Base64;

public class DoctorDtoImage {
	private Long id;
    private String name;
    private String phone;
    private String degree;
    private double amount;
    private String specializationName;
    private String doctorImageBase64;

    // Constructors
    public DoctorDtoImage() {}

    public DoctorDtoImage(Long id, String name, String phone, String degree, double amount, String specializationName, byte[] doctorImage) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.degree = degree;
        this.amount = amount;
        this.specializationName = specializationName;
        this.doctorImageBase64 = doctorImage != null ? Base64.getEncoder().encodeToString(doctorImage) : null;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getSpecializationName() { return specializationName; }
    public void setSpecializationName(String specializationName) { this.specializationName = specializationName; }

    public String getDoctorImageBase64() { return doctorImageBase64; }
    public void setDoctorImageBase64(String doctorImageBase64) { this.doctorImageBase64 = doctorImageBase64; }
}
