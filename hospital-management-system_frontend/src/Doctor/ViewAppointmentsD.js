import React, { useEffect, useState } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";
import Doctor from "./Doctor";

function ViewAppointmentsD() {
  const [appointments, setAppointments] = useState([]);
  const userId = sessionStorage.getItem("userId"); 
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
    } else if (sessionStorage.getItem("userRole") === "RECEPTIONIST") {
      navigate("/receiptionist");
    }
  }, [navigate]);

  useEffect(() => {
    async function fetchAppointments() {
      try {
        const response = await axios.get(
          `http://localhost:8080/doctor/getDoctorAppointments/${userId}`,
          {
            headers: { Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}` },
          }
        );
        setAppointments(response.data);
      } catch (error) {
        console.error("Error fetching appointments:", error);
        toast.error("Failed to load appointments.", { autoClose: 3000 });
      }
    }
    fetchAppointments();
  }, [userId]);

  const updateStatus = async (appointmentId, newStatus) => {
    try {
      await axios.put(
        `http://localhost:8080/doctor/updateStatus`,
        { appointmentId:Number(appointmentId), 
          status: newStatus },
        {
          headers: {
            Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
            "Content-Type": "application/json",
          },
        }
      );

      toast.success(`Appointment ${appointmentId} marked as ${newStatus}`, { autoClose: 3000 });

      setAppointments((prevAppointments) =>
        prevAppointments.map((appt) =>
          appt.appointmentId === appointmentId
            ? { ...appt, status: newStatus }
            : appt
        )
      );
    } catch (error) {
      console.error("Error updating appointment status:", error);
      toast.error("Failed to update status.", { autoClose: 3000 });
    }
  };

  const handleFileUpload = async (file, appointmentId) => {
    if (!file) {
      toast.error("Please select a file before uploading.", { autoClose: 3000 });
      return;
    }

    const allowedExtensions = ["application/pdf", "application/msword"];
    if (!allowedExtensions.includes(file.type)) {
      toast.error("Only .pdf and .doc files are allowed.", { autoClose: 3000 });
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      const config = {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
          "Content-Type": "multipart/form-data",
        },
      };

      const response = await axios.post(
        `http://localhost:8080/doctor/uploadPrescription/${appointmentId}`,
        formData,
        config
      );

      if (response.status === 200) {
        toast.success(`Prescription uploaded successfully for appointment ${appointmentId}`, { autoClose: 3000 });
      } else {
        toast.error(`Failed to upload prescription for appointment ${appointmentId}`, { autoClose: 3000 });
      }
    } catch (error) {
      console.error("Error uploading prescription:", error);
      toast.error("Failed to upload prescription.", { autoClose: 3000 });
    }
  };

  return (
    <Doctor>
      <div className="container mt-5">
        <ToastContainer position="top-right" autoClose={3000} hideProgressBar={false} />
        <h2 className="text-center mb-4 text-primary">Appointment Details</h2>
        <div className="table-responsive">
          <table className="table table-bordered table-striped">
            <thead className="table-primary">
              <tr>
                <th>Appointment ID</th>
                <th>Appointment Date</th>
                <th>Disease Description</th>
                <th>Patient Name</th>
                <th>Patient Age</th>
                <th>Patient Gender</th>
                <th>Patient Weight</th>
                <th>Patient Email</th>
                <th>Status</th>
                <th>Action</th>
                <th>Prescription</th>
              </tr>
            </thead>
            <tbody>
              {appointments.length === 0 ? (
                <tr>
                  <td colSpan="11" className="text-center">
                    No appointments found.
                  </td>
                </tr>
              ) : (
                appointments.map((appt) => (
                  <tr key={appt.appointmentId}>
                    <td>{appt.appointmentId}</td>
                    <td>{appt.appointmentDate}</td>
                    <td>{appt.diseaseDescription}</td>
                    <td>{appt.patientName}</td>
                    <td>{appt.patientAge}</td>
                    <td>{appt.patientGender}</td>
                    <td>{appt.patientWeight}</td>
                    <td>{appt.patientEmail}</td>
                    <td>{appt.status}</td>
                    <td>
                      {appt.status === "PENDING" && (
                        <>
                          <button
                            className="btn btn-success me-2"
                            onClick={() => updateStatus(appt.appointmentId, "ACCEPTED")}
                          >
                            ACCEPT
                          </button>
                          <button
                            className="btn btn-danger"
                            onClick={() => updateStatus(appt.appointmentId, "DECLINED")}
                          >
                            DECLINE
                          </button>
                        </>
                      )}
                      {appt.status === "ACCEPTED" && <span className="text-success">Accepted</span>}
                      {appt.status === "DECLINED" && <span className="text-danger">Declined</span>}
                    </td>
                    <td>
                      {appt.status === "ACCEPTED" && (
                        <>
                          <label className="upload-label" htmlFor={`file-${appt.appointmentId}`}>
                            Choose File
                          </label>
                          <input
                            id={`file-${appt.appointmentId}`}
                            type="file"
                            accept=".pdf,.doc"
                            onChange={(e) => handleFileUpload(e.target.files[0], appt.appointmentId)}
                          />
                        </>
                      )}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>

      <style jsx>{`
        .upload-label {
          display: inline-block;
          background-color: #007bff;
          color: white;
          padding: 5px 10px;
          border-radius: 5px;
          cursor: pointer;
          font-size: 14px;
          margin-right: 10px;
        }
        .upload-label:hover {
          background-color: #0056b3;
        }
        input[type="file"] {
          display: none;
        }
      `}</style>
    </Doctor>
  );
}

export default ViewAppointmentsD;
