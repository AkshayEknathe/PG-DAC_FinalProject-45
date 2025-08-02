package com.app.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Payment {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private double  amount;
	    
	    private String paymentstatus;
	    
	    @OneToOne
	    private Appointment appointment;  // Storing patient ID instead of foreign key reference

	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public Appointment getAppointment() {
			return appointment;
		}

		public void setAppointment(Appointment appointment) {
			this.appointment = appointment;
		}

		public LocalDate getDate() {
			return date;
		}

		public void setDate(LocalDate date) {
			this.date = date;
		}

		public String getPaymentstatus() {
			return paymentstatus;
		}

		public void setPaymentstatus(String paymentstatus) {
			this.paymentstatus = paymentstatus;
		}

		private LocalDate date;

	  

}
