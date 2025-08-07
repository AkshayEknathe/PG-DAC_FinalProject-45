import React, { useState, useEffect } from "react";
import axios from "axios";
import "./ViewReceiptionist.css";
import { useNavigate } from "react-router-dom";
import Admin from "./Admin";

function ViewReceiptionist() {
  const [receptionists, setReceptionists] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const role = sessionStorage.getItem("userRole");
    const username = sessionStorage.getItem("userName");

    if (!username) {
      navigate("/");
    } else if (role === "DOCTOR") {
      navigate("/doctor");
    } else if (role === "PATIENT") {
      navigate("/");
    } else if (role === "ADMIN") {
      navigate("/admin");
    } else if (role === "RECEPTIONIST") {
      navigate("/receiptionist");
    }
  }, [navigate]);

  useEffect(() => {
    fetchReceptionists();
  }, []);

  const fetchReceptionists = async () => {
    try {
      const config = {
        headers: { Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}` },
      };
      const response = await axios.get("http://localhost:8080/admin/getAllReceptionist", config);
      setReceptionists(response.data);
    } catch (error) {
      console.error("Error fetching receptionists:", error);
    }
  };

  const handleEdit = (id) => {
    navigate(`/admin/editreceptionist/${id}`);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this receptionist?")) return;
    try {
      const config = {
        headers: { Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}` },
      };
      await axios.delete(`http://localhost:8080/admin/deleteReceptionist/${id}`, config);
      alert("Receptionist deleted successfully!");
      setReceptionists(receptionists.filter((rec) => rec.id !== id));
    } catch (error) {
      console.error("Error deleting receptionist:", error);
      alert("Failed to delete receptionist!");
    }
  };

  return (
    <Admin>
      <div className="view-receptionist-container">
        <h2>View Receptionists</h2>
        <div className="table-responsive">
          <table className="receptionist-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Password</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {receptionists.length > 0 ? (
                receptionists.map((rec) => (
                  <tr key={rec.id}>
                    <td>{rec.name}</td>
                    <td>{rec.email}</td>
                    <td>{rec.phone}</td>
                    <td>{rec.password}</td>
                    <td>
                      <button className="edit-btn" onClick={() => handleEdit(rec.id)}>Edit</button>
                      <button className="delete-btn" onClick={() => handleDelete(rec.id)}>Delete</button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5">No receptionists available</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </Admin>
  );
}

export default ViewReceiptionist;
