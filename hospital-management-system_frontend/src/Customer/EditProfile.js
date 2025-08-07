import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function EditProfile() {
  const navigate = useNavigate();
  const { id } = useParams(); // Get user ID from the URL

  // Redirect based on role
  useEffect(() => {
    const userRole = sessionStorage.getItem("userRole");
    if (!sessionStorage.getItem("userName")) {
      navigate("/");
    } else if (userRole === "DOCTOR") {
      navigate("/doctor");
    } else if (userRole === "PATIENT") {
      navigate("/");
    } else if (userRole === "ADMIN") {
      navigate("/admin");
    } else if (userRole === "RECEPTIONIST") {
      navigate("/receptionist");
    }
  }, [navigate]);

  // State Variables (Updated based on correct fields)
  const [name, setName] = useState("");
  const [age, setAge] = useState("");
  const [gender, setGender] = useState("");
  const [weight, setWeight] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const editUrl = `http://localhost:8080/customer/getUserById/${id}`;
  const updateUrl = `http://localhost:8080/customer/updateUser/${id}`;

  // Fetch user details when the component mounts
  useEffect(() => {
    const config = {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
      },
    };

    axios
      .get(editUrl, config)
      .then((response) => {
        const { name, age, gender, weight, email, password } = response.data;
        setName(name || "");
        setAge(age || "");
        setGender(gender || "");
        setWeight(weight || "");
        setEmail(email || "");
        setPassword(password || "");
      })
      .catch((error) => {
        console.error("Error occurred getting user details:", error);
        toast.error("Failed to fetch user details");
      });
  }, [editUrl]);

  const handleSubmit = (e) => {
    e.preventDefault();

    const config = {
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
      },
    };

    // Prepare updated user details
    const userDetails = {
      name,
      age,
      gender,
      weight,
      email,
      password,
    };

    axios
      .put(updateUrl, userDetails, config)
      .then(() => {
        toast.success("Profile updated successfully!");
        setTimeout(() => {
          navigate("/"); // Redirect after success
        }, 2000);
      })
      .catch((error) => {
        console.error("Failed to update profile:", error);
        toast.error("Failed to update profile.");
      });
  };

  return (
    <div style={{ marginTop: "-22%" }}>
      <ToastContainer />
      <div className="d-flex justify-content-center align-items-center">
        <div
          className="shadow-lg p-4"
          style={{
            width: "35rem",
            marginTop: "25rem",
            border: "2px solid #f2ebb7",
            backgroundColor: "#f4f4f9",
            color: "black",
          }}
        >
          <h2 className="text-center mb-4">Edit Profile</h2>
          <form onSubmit={handleSubmit}>
            {/* Name */}
            <div className="mb-3">
              <label>Name:</label>
              <input
                type="text"
                className="form-control"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>

            {/* Age */}
            <div className="mb-3">
              <label>Age:</label>
              <input
                type="number"
                className="form-control"
                value={age}
                onChange={(e) => setAge(e.target.value)}
                required
              />
            </div>

            {/* Gender */}
            <div className="mb-3">
              <label>Gender:</label>
              <select
                className="form-control"
                value={gender}
                onChange={(e) => setGender(e.target.value)}
                required
              >
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
              </select>
            </div>

            {/* Weight */}
            <div className="mb-3">
              <label>Weight (kg):</label>
              <input
                type="number"
                className="form-control"
                value={weight}
                onChange={(e) => setWeight(e.target.value)}
                required
              />
            </div>

            {/* Email */}
            <div className="mb-3">
              <label>Email:</label>
              <input
                type="email"
                className="form-control"
                value={email}
                readOnly
              />
            </div>

            {/* Password */}
            <div className="mb-3">
              <label>Password:</label>
              <input
                type="password"
                className="form-control"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>

            {/* Submit Button */}
            <div className="mb-3 w-100">
              <button
                type="submit"
                className="btn btn-light w-100"
                style={{ backgroundColor: "#aeff00" }}
              >
                Update Profile
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default EditProfile;
