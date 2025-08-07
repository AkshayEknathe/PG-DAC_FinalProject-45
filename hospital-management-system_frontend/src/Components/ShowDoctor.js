import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./ShowDoctor.css";
import CustomerNavbar from "./CustomerNavbar";

function ShowDoctor() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDoctors = async () => {
      const config = {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
          "Content-Type": "application/json",
        },
      };

      try {
        const response = await axios.get(
          `http://localhost:8080/patient/getDoctorsBySpecialization/${id}`,
          config
        );
        setDoctors(response.data);
      } catch (err) {
        setError("Failed to load doctors. Please try again later.");
      } finally {
        setLoading(false);
      }
    };
    fetchDoctors();
  }, [id]);

  if (loading) return <p>Loading doctors...</p>;
  if (error) return <p>{error}</p>;

  const handleAppointmentBooking = (doctor) => {
    const userId = sessionStorage.getItem("userId");


    if (!userId) {
      toast.warn("Please login to book an appointment.", {
        position: "top-right",
        autoClose: 2000,
      });

      setTimeout(() => {
        navigate("/login");
      }, 2000);
      return;
    }

    navigate("/bookappointment", { state: doctor });
  };

  return (
    <div>
      <CustomerNavbar />
      <ToastContainer />
      <div className="product-list-container">
        {doctors.length > 0 ? (
          doctors.map((doctor) => (
            <div className="doctor-card" key={doctor.id}>
              {/* Display doctor image only if available */}
              {doctor.doctorImageBase64 && (
                <img
                  src={`data:image/png;base64,${doctor.doctorImageBase64}`}
                  alt={doctor.name}
                  className="doctor-image"
                />
              )}
              <div className="doctor-details">
                <h3 className="doctor-name">{doctor.name}</h3>
                <p className="doctor-degree"><strong>Degree:</strong> {doctor.degree}</p>
                <p className="doctor-phone"><strong>Phone:</strong> {doctor.phone}</p>
                <p className="doctor-specialization">
                  <strong>Specialization:</strong> {doctor.specializationName}
                </p>
                <p className="doctor-amount"><strong>Consultation Fee:</strong> ₹{doctor.amount}</p>
              </div>

              <button
                className="book-appointment-button"
                onClick={() => handleAppointmentBooking(doctor)}
              >
                Book Appointment
              </button>
            </div>
          ))
        ) : (
          <p>No doctors found for this specialization.</p>
        )}
      </div>
    </div>
  );
}

export default ShowDoctor;
