package com.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.DTO.DoctorDTO;
import com.app.DTO.DoctorDTOResponse;
import com.app.DTO.RegisterUserDTO;
import com.app.DTO.SpecializationDTO;
import com.app.Entity.Specialization;
import com.app.Entity.User;
import com.app.Service.DoctorService;
import com.app.Service.SpecializationService;
import com.app.Service.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:3000")
public class AdminController {

	@Autowired
	private DoctorService doctorService;
	@Autowired
	private SpecializationService specializationService;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/addDoctor")
	public ResponseEntity<String> addDoctor(@ModelAttribute DoctorDTO doctorDTO) {
		doctorDTO.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
		String response = doctorService.addDoctor(doctorDTO);
		if (response.equals("Doctor and User added successfully!")) {
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	// Get all doctors
	@GetMapping("/getAllDoctors")
	public ResponseEntity<List<DoctorDTOResponse>> getAllDoctors() {
		List<DoctorDTOResponse> doctors = doctorService.getAllDoctors();
		return ResponseEntity.ok(doctors);
	}

	@GetMapping("/getDoctorById/{id}")
	public ResponseEntity<DoctorDTOResponse> getDoctorById(@PathVariable Long id) {
		DoctorDTOResponse doctorDTOResponse = doctorService.getDoctorById(id);
		return ResponseEntity.ok(doctorDTOResponse);
	}

	@PostMapping("/addSpecialization")
	public ResponseEntity<Specialization> addSpecialization(@RequestParam String name,
			@RequestParam("specializationImage") MultipartFile file) {
		try {
			SpecializationDTO dto = new SpecializationDTO();
			dto.setName(name);
			dto.setSpecializationImage(file.getBytes()); // Convert file to byte array

			Specialization savedSpecialization = specializationService.addSpecialization(dto);
			return new ResponseEntity<>(savedSpecialization, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllSpecialization")
	public ResponseEntity<List<SpecializationDTO>> getAllSpecializations() {
		List<SpecializationDTO> specializations = specializationService.getAllSpecializations();
		return ResponseEntity.ok(specializations);
	}

	@PostMapping("/addReceptionist")
	public ResponseEntity<?> addReceptionist(@RequestBody RegisterUserDTO dto) {
		try {
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
			User registeredUser = userService.registerReceptionist(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/getAllReceptionist")
	public ResponseEntity<List<User>> getAllReceptionists() {
		List<User> receptionists = userService.getAllReceptionists();
		return ResponseEntity.ok(receptionists);
	}

	@GetMapping("/getReceptionistById/{id}")
	public ResponseEntity<User> getReceptionistById(@PathVariable Long id) {
		User receptionist = userService.getReceptionistById(id);
		return ResponseEntity.ok(receptionist);
	}

	@PutMapping("/updateDoctor/{id}")
	public ResponseEntity<String> updateDoctor(@PathVariable("id") Long id, @RequestBody DoctorDTO doctorDTO) {
		if (id == null || id <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid doctor ID provided.");
		}

		String response = doctorService.updateDoctor(id, doctorDTO);

		if ("Doctor updated successfully!".equals(response)) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PutMapping("/updateReceptionist/{id}")
	public ResponseEntity<String> updateReceptionist(@PathVariable Long id,
			@RequestBody RegisterUserDTO receptionistDTO) {
		receptionistDTO.setPassword(passwordEncoder.encode(receptionistDTO.getPassword()));
		String response = userService.updateReceptionist(id, receptionistDTO);
		if (response.equals("Receptionist updated successfully!")) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@DeleteMapping("/deleteReceptionist/{id}")
	public ResponseEntity<String> deleteReceptionist(@PathVariable Long id) {
		String response = userService.deleteReceptionist(id);

		if (response.equals("Receptionist deleted successfully!")) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

}
