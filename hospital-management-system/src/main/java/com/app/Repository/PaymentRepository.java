package com.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	 
}
