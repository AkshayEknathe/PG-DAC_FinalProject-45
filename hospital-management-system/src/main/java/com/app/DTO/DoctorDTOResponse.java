package com.app.DTO;

public class DoctorDTOResponse {
	 private Long id;
	    private String name;
	    private String email;
	    private String password;
	    private String phone;
	    private String degree;
	    private double amount;
	    private String specializationName;

	    // Default constructor
	    public DoctorDTOResponse() {
	    }

	    // Parameterized constructor
	    public DoctorDTOResponse(Long id, String name, String email, String password, String phone, 
	                             String degree, double amount, String specializationName) {
	        this.id = id;
	        this.name = name;
	        this.email = email;
	        this.password = password;
	        this.phone = phone;
	        this.degree = degree;
	        this.amount = amount;
	        this.specializationName = specializationName;
	    }

	    // Getters and Setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

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

	    public double getAmount() {
	        return amount;
	    }

	    public void setAmount(double amount) {
	        this.amount = amount;
	    }

	    public String getSpecializationName() {
	        return specializationName;
	    }

	    public void setSpecializationName(String specializationName) {
	        this.specializationName = specializationName;
	    }
}
