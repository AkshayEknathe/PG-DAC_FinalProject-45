package com.app.DTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecializationDTO {
	private Long id;
    private String name;
    private byte[] specializationImage; // Keep image as byte array
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
	public byte[] getSpecializationImage() {
		return specializationImage;
	}
	public void setSpecializationImage(byte[] specializationImage) {
		this.specializationImage = specializationImage;
	}
}
