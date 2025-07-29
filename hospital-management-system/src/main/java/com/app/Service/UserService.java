package com.app.Service;

import java.util.List;

import com.app.DTO.RegisterUserDTO;
import com.app.Entity.User;

public interface UserService {

	

	List<User> getAllReceptionists();

	User registerReceptionist(RegisterUserDTO dto);

	User getReceptionistById(Long id);

	String updateReceptionist(Long id, RegisterUserDTO receptionistDTO);

	String deleteReceptionist(Long id);

}
