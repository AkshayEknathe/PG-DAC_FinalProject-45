package com.app.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Specialization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name; // Example: Cardiology, Neurology

	@Lob
	@Column(columnDefinition = "LONGBLOB") // To store image as BLOB
	private byte[] specializationimage;

	@OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL)
	private List<Doctor> doctors;

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

	public byte[] getSpecializationimage() {
		return specializationimage;
	}

	public void setSpecializationimage(byte[] specializationimage) {
		this.specializationimage = specializationimage;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	
}
