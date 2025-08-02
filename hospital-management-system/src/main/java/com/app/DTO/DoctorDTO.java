package com.app.DTO;
import org.springframework.web.multipart.MultipartFile;
public class DoctorDTO {
	private String email;
    private String password;
    private String name;
    private String phone;
    private String degree;
    private Long specializationId;
    private double amount;
    private MultipartFile doctorImage;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Long getSpecializationId() {
		return specializationId;
	}
	public void setSpecializationId(Long specializationId) {
		this.specializationId = specializationId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public MultipartFile getDoctorImage() {
		return doctorImage;
	}
	public void setDoctorImage(MultipartFile doctorImage) {
		this.doctorImage = doctorImage;
	}
}
