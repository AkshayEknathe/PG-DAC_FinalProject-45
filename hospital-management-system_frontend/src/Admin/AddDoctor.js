import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./AddDoctor.css";
import Admin from "./Admin";

function AddDoctor() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [degree, setDegree] = useState("");
  const [phone, setPhone] = useState("");
  const [doctorImage, setDoctorImage] = useState(null);
  const [specializations, setSpecializations] = useState([]);
  const [specializationId, setSpecializationId] = useState("");
  const [password, setPassword] = useState("");
  const [amount, setAmount] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const role = sessionStorage.getItem("userRole");
    if (!sessionStorage.getItem("userName")) {
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
    const fetchSpecializations = async () => {
      try {
        const config = {
          headers: {
            Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
            "Content-Type": "application/json",
          },
        };
        const response = await axios.get("http://localhost:8080/admin/getAllSpecialization", config);
        setSpecializations(response.data);
      } catch (error) {
        console.error("Error fetching specializations:", error);
      }
    };
    fetchSpecializations();
  }, []);

  const handleImageChange = (e) => {
    setDoctorImage(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!name || !email || !degree || !phone || !specializationId || !doctorImage || !password || !amount) {
      setError("All fields are required.");
      return;
    }

    const formData = new FormData();
    formData.append("name", name);
    formData.append("email", email);
    formData.append("degree", degree);
    formData.append("phone", phone);
    formData.append("specializationId", specializationId);
    formData.append("doctorImage", doctorImage);
    formData.append("password", password);
    formData.append("amount", amount);

    try {
      const config = {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
          "Content-Type": "multipart/form-data",
        },
      };

      const response = await axios.post("http://localhost:8080/admin/addDoctor", formData, config);

      if (response.status === 201) {
        alert("Doctor added successfully!");
        setName("");
        setEmail("");
        setDegree("");
        setPhone("");
        setSpecializationId("");
        setDoctorImage(null);
        setPassword("");
        setAmount("");
        setError("");
        window.location.reload();
      } else {
        alert("Failed to add doctor.");
      }
    } catch (error) {
      console.error("Error:", error);
      alert("Something went wrong.");
    }
  };

  return (
    <Admin>
      <div className="add-doctor-container">
        <h3>Add New Doctor</h3>
        {/* ✅ FIXED: added enctype for file upload */}
        <form onSubmit={handleSubmit} className="doctor-form" encType="multipart/form-data">
          <div className="form-group">
            <label>Doctor Name</label>
            <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
          </div>

          <div className="form-group">
            <label>Email</label>
            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </div>

          <div className="form-group">
            <label>Degree</label>
            <input type="text" value={degree} onChange={(e) => setDegree(e.target.value)} required />
          </div>

          <div className="form-group">
            <label>Phone</label>
            <input type="tel" value={phone} onChange={(e) => setPhone(e.target.value)} required />
          </div>

          <div className="form-group">
            <label>Specialization</label>
            <select value={specializationId} onChange={(e) => setSpecializationId(e.target.value)} required>
              <option value="">Select Specialization</option>
              {specializations.map((spec) => (
                <option key={spec.id} value={spec.id}>{spec.name}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Doctor Image</label>
            <input type="file" accept="image/*" onChange={handleImageChange} required />
          </div>

          <div className="form-group">
            <label>Amount</label>
            <input type="number" value={amount} onChange={(e) => setAmount(e.target.value)} required />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
          </div>

          {error && <p className="error-message">{error}</p>}

          <button type="submit" className="submit-btn">Submit</button>
        </form>
      </div>
    </Admin>
  );
}

export default AddDoctor;
