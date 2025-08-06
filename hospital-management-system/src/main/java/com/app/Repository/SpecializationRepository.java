package com.app.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long>  {

}
