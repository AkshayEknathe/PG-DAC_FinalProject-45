package com.app.Service;

import java.time.LocalDate;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.DTO.PaymentRequestDTO;
import com.app.Entity.Appointment;
import com.app.Entity.Payment;
import com.app.Repository.AppointmentRepository;
import com.app.Repository.PaymentRepository;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public Payment processPayment(PaymentRequestDTO dto) {
	    Optional<Appointment> optionalAppointment = appointmentRepository.findById(dto.getAppointmentId());

	    if (!optionalAppointment.isPresent()) {
	        throw new RuntimeException("Appointment not found with ID: " + dto.getAppointmentId());
	    }

	    Appointment appointment = optionalAppointment.get();

	    // Null check for appointment payment
	    if (appointment.getPayment() != null && "PAID".equals(appointment.getPayment().getPaymentstatus())) {
	        throw new IllegalStateException("Payment already done for this appointment.");
	    }

	    // Create a new Payment entity
	    Payment payment = new Payment();
	    payment.setAmount(dto.getAmount());
	    payment.setAppointment(appointment);
	    payment.setDate(LocalDate.now());
	    payment.setPaymentstatus("PAID");

	    // Save the payment entity
	    return paymentRepository.save(payment);
	}

}
