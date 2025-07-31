package com.app.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.Entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	List<Doctor> findBySpecializationId(Long specializationId);

	Optional<Doctor> findByEmail(String email);
	
//	 @Query("SELECT d.id FROM Doctor d WHERE d.user.id = :userId")
//	    Optional<Long> findDoctorIdByUserId(@Param("userId") Long userId);


	//Object findByUser_Id(Long userId);

}