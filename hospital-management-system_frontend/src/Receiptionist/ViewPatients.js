import React, { useState, useEffect } from "react";
import axios from "axios";
import "./ViewPatients.css"; // Ensure this file exists
import { useNavigate } from "react-router-dom";
import Receiptionist from "./Receiptionist"; // Assuming this is your layout component

function ViewPatients() {
  const [patients, setPatients] = useState([]);
  const navigate = useNavigate();

          useEffect(() => {
            if (!sessionStorage.getItem("userName")) {
              navigate("/");
            } else if (sessionStorage.getItem("userRole") === "DOCTOR") {
              navigate("/doctor");
            } else if (sessionStorage.getItem("userRole") === "PATIENT") {
              navigate("/");
            } else if (sessionStorage.getItem("userRole") === "ADMIN") {
              navigate("/admin");
            }
            else if (sessionStorage.getItem("userRole") === "RECEPTIONIST") {
              navigate("/receiptionist");
            }
          }, [navigate]);

  // Fetch patients from the API on component mount
  useEffect(() => {
    const fetchPatients = async () => {
      try {
        const config = {
          headers: {
            Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
          },
        };

        const response = await axios.get(
          "http://localhost:8080/receptionist/getAllPatients",
          config
        );
        setPatients(response.data);
      } catch (error) {
        console.error("Error fetching patients:", error);
      }
    };

    fetchPatients();
  }, []);

  return (
    <Receiptionist>
      <div className="view-patients-container">
        <h2>View Patients</h2>
        <table className="patients-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Age</th>
              <th>Gender</th>
              <th>Weight</th>
              <th>Email</th>
            </tr>
          </thead>
          <tbody>
            {patients.length > 0 ? (
              patients.map((patient) => (
                <tr key={patient.id}>
                  <td>{patient.name}</td>
                  <td>{patient.age}</td>
                  <td>{patient.gender}</td>
                  <td>{patient.weight} kg</td>
                  <td>{patient.email}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5">No patients available</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </Receiptionist>
  );
}

export default ViewPatients;
