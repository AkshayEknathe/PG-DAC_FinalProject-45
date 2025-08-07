import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Admin from "./Admin";

function EditReceptionist() {
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
  const { id } = useParams(); // Get the receptionist ID from the URL
  const [name, setReceptionistName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phone,setPhone] = useState("");

  const editUrl = `http://localhost:8080/admin/getReceptionistById/${id}`;
  const updateUrl = `http://localhost:8080/admin/updateReceptionist/${id}`;

  // Configuration for headers with JWT token
  const config = {
    headers: {
      Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
    },
  };

  // Fetch receptionist details
  useEffect(() => {
    axios
      .get(editUrl, config)
      .then((response) => {
        const { name, email, password,phone } = response.data;
        setReceptionistName(name);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
      })
      .catch((error) => {
        console.error("Error fetching receptionist details:", error);
        toast.error("Failed to fetch receptionist details");
      });
  }, [editUrl]);

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    const receptionistUpdateDTO = {
      name,
      email,
      password,
      phone,
    };

    axios
      .put(updateUrl, receptionistUpdateDTO, config)
      .then(() => {
        toast.success("Receptionist details updated successfully!");
        setTimeout(() => {
          navigate("/admin/viewReceptionist"); // Redirect to View Receptionists page
        }, 2000);
      })
      .catch((error) => {
        console.error("Failed to update receptionist details:", error);
        toast.error("Failed to update receptionist.");
      });
  };

  return (
    <Admin>
      <ToastContainer />
      <div className="d-flex justify-content-center align-items-center">
        <div
          className="shadow-lg p-4"
          style={{
            width: "35rem",
            border: "1px solid black",
            color: "black",
            display: "flex",
            flexDirection: "column",
            border: "2px solid rgb(161, 196, 239)",
            backgroundColor: "#f4f4f9",
          }}
        >
          <h2 className="text-center mb-4">Edit Receptionist</h2>
          <form onSubmit={handleSubmit}>
            {/* Receptionist Name - Non-editable */}
            <div className="mb-3">
              <label>Receptionist Name:</label>
              <input
                type="text"
                className="form-control"
                value={name}
                onChange={(e) => setReceptionistName(e.target.value)}
                required
                style={{ height: "30px"}}
              />
            </div>

            {/* Email */}
            <div className="mb-3">
              <label>Email:</label>
              <input
                type="email"
                className="form-control"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                style={{ height: "30px" }}
                required
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
                style={{ height: "30px" }}
                required
              />
            </div>

            {/* Password */}
            <div className="mb-3">
              <label>Phone:</label>
              <input
                type="number"
                className="form-control"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
                style={{ height: "30px" }}
                required
              />
            </div>

            {/* Submit Button */}
            <div className="mb-3 w-100">
              <button
                type="submit"
                className="btn btn-light w-100"
                style={{ backgroundColor: "#a9ab99" }}
              >
                Edit Receptionist
              </button>
            </div>
          </form>
        </div>
      </div>
    </Admin>
  );
}

export default EditReceptionist;
