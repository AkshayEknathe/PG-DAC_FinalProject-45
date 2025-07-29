package com.app.Service;

import java.util.List;
import java.util.Optional;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.DTO.RegisterUserDTO;
import com.app.Entity.User;
import com.app.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

//	    @Autowired
//	    private PasswordEncoder passwordEncoder; // For password encryption

	public User registerReceptionist(RegisterUserDTO dto) {
		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword()); // Ideally, encrypt this using BCrypt
		user.setPhone(dto.getPhone());
		user.setRole(Role.ROLE_RECEPTIONIST);

		return userRepository.save(user);
	}

	public List<User> getAllReceptionists() {
		return userRepository.findByRole(Role.ROLE_RECEPTIONIST);
	}
	
	public User getReceptionistById(Long id) {
	    return userRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Receptionist not found with ID: " + id));
	}
	public String updateReceptionist(Long id, RegisterUserDTO receptionistDTO) {
	    Optional<User> optionalReceptionist = userRepository.findById(id);

	    if (!optionalReceptionist.isPresent()) {
	        return "Receptionist not found with ID: " + id;
	    }

	    User receptionist = optionalReceptionist.get();

	    // Ensure the user has the role of Receptionist before updating
	    if (!receptionist.getRole().equals(Role.ROLE_RECEPTIONIST)) {
	        return "User is not a Receptionist!";
	    }

	    // Update receptionist details with null checks
	    if (receptionistDTO.getName() != null) {
	        receptionist.setName(receptionistDTO.getName());
	    }
	    if (receptionistDTO.getEmail() != null) {
	        receptionist.setEmail(receptionistDTO.getEmail());
	    }
	    if (receptionistDTO.getPhone() != null) {
	        receptionist.setPhone(receptionistDTO.getPhone());
	    }
	   receptionist.setPassword(receptionistDTO.getPassword());
	   receptionist.setRole(Role.ROLE_RECEPTIONIST);
	   receptionist.setId(id);
	    userRepository.save(receptionist);

	    return "Receptionist updated successfully!";
	}

	public String deleteReceptionist(Long id) {
	    Optional<User> optionalReceptionist = userRepository.findById(id);

	    if (!optionalReceptionist.isPresent()) {
	        return "Receptionist not found with ID: " + id;
	    }

	    User receptionist = optionalReceptionist.get();

	    // Ensure the user has the role of Receptionist before deleting
	    if (!receptionist.getRole().equals(Role.ROLE_RECEPTIONIST)) {
	        return "User is not a Receptionist!";
	    }

	    userRepository.delete(receptionist);
	    return "Receptionist deleted successfully!";
	}


}
