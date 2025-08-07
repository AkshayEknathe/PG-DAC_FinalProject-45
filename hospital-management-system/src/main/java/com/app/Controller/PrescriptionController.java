package com.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.Entity.Prescription;
import com.app.Service.PrescriptionService;


@RestController
@CrossOrigin("http://localhost:3000")
public class PrescriptionController {

	@Autowired
	private PrescriptionService prescriptionService;
	
	@PostMapping("/doctor/uploadPrescription/{appointmentId}")
	public ResponseEntity<String> uploadFile(@PathVariable long appointmentId, @RequestParam("file") MultipartFile file) {
	    try {
	        // Set the file name to the customerId
	        String fileName = String.valueOf(appointmentId);

	        // Create a new DietPlan object with the customerId as the file name
	        Prescription prescription = new Prescription(
	            fileName,
	            file.getContentType(),
	            file.getBytes()
	        );

	        // Save the diet plan
	        Prescription d = prescriptionService.savePrescription(prescription);

	        return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	 
	 
	
	@GetMapping("/patient/download/{appointmentId}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable long appointmentId) {
	    try {
	        // Use customerId as the file name
	        String fileName = String.valueOf(appointmentId);

	        // Retrieve the DietPlan associated with the customerId (as the filename)
	        Prescription pres = prescriptionService.findfile(fileName);

	        // If the file exists, return it with the correct headers for download
	        if (pres != null) {
	            // Determine the file extension based on the file type
	            String extension = "";
	            if ("application/pdf".equals(pres.getType())) {
	                extension = ".pdf";
	            } else if ("application/msword".equals(pres.getType()) || "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(pres.getType())) {
	                extension = ".docx";
	            }

	            // Set the full file name with the extension
	            String fullFileName = pres.getName() + extension;

	            // Set the correct content type for the response
	            HttpHeaders headers = new HttpHeaders();
	            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fullFileName);
	            headers.add(HttpHeaders.CONTENT_TYPE, pres.getType());  // Ensure correct MIME type

	            return new ResponseEntity<>(pres.getPrescriptionPdf(), headers, HttpStatus.OK);
	        } else {
	            // If the file is not found, return a 404 Not Found status
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        // Log and handle any unexpected errors
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
