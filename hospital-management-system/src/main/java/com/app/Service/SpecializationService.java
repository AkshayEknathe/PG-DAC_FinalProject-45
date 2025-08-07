package com.app.Service;

import java.util.List;

import com.app.DTO.SpecializationDTO;
import com.app.Entity.Specialization;

public interface SpecializationService {

	Specialization addSpecialization(SpecializationDTO specializationDTO);

	List<SpecializationDTO> getAllSpecializations();

}
