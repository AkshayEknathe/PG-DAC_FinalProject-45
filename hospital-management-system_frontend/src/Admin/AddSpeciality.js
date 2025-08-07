import React, { useEffect, useState } from "react";
import axios from "axios";
import "./AddSpeciality.css";
import { useNavigate } from "react-router-dom";
import Admin from "./Admin";

function AddSpeciality() {
  const [specialityName, setSpecialityName] = useState("");
  const [specialityImage, setSpecialityImage] = useState(null);
  const [error, setError] = useState("");

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

  const handleImageChange = (e) => {
    setSpecialityImage(e.target.files[0]);
  };

  const handleNameChange = (e) => {
    setSpecialityName(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!specialityName || !specialityImage) {
      setError("Both fields are required.");
      return;
    }

    const formData = new FormData();
    formData.append("name", specialityName);
    formData.append("specializationImage", specialityImage);

    try {
      const config = {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
          "Content-Type": "multipart/form-data", // Combine headers into one object
        },
      };
    
      const response = await axios.post("http://localhost:8080/admin/addSpecialization", formData, config);
    
      if (response.status === 201) {
        alert("Speciality added successfully!");
        setSpecialityName("");
        setSpecialityImage(null);
        setError("");
        window.location.reload();
      }
    } catch (error) {
      console.error("Error:", error);
      setError("Failed to add Speciality. Please try again.");
    }
  }

  return (
    <Admin>
      <div className="add-category-container">
        <h3>Add New Speciality</h3>
        <form onSubmit={handleSubmit} className="category-form">
          <div className="form-group">
            <label htmlFor="categoryName">Speciality Name</label>
            <input
              type="text"
              id="categoryName"
              value={specialityName}
              onChange={handleNameChange}
              placeholder="Enter speciality"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="categoryImage">Image</label>
            <input
              type="file"
              id="categoryImage"
              accept="image/*"
              onChange={handleImageChange}
              required
            />
          </div>

          {error && <p className="error-message">{error}</p>}

          <button type="submit" className="submit-btn">
            Submit
          </button>
        </form>
      </div>
    </Admin>
  );
}

export default AddSpeciality;
