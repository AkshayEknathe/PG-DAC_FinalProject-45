import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { useFormik } from "formik";
import * as Yup from "yup";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify"; 
import "react-toastify/dist/ReactToastify.css";
import Receiptionist from "./Receiptionist";

function AddPatient() {
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

  const formik = useFormik({
    initialValues: {
      name: "",
      age: "",
      gender: "",
      weight: "",
      email: "",
      phone: "",
      password: "",
    },
    validationSchema: Yup.object({
      name: Yup.string().required("Name is required"),
      age: Yup.number().required("Age is required").positive("Age must be positive").integer("Age must be an integer"),
      gender: Yup.string().required("Gender is required"),
      weight: Yup.number().required("Weight is required").positive("Weight must be positive"),
      email: Yup.string().email("Invalid email").required("Email is required"),
      phone: Yup.string()
        .matches(/^\d{10}$/, "Phone number must be 10 digits")
        .required("Phone number is required"),
      password: Yup.string().required("Password is required"),
    }),
    onSubmit: (values, { resetForm }) => {
      const userData = {
        name: values.name,
        age: values.age,
        gender: values.gender,
        weight: values.weight,
        email: values.email,
        phone: values.phone,
        password: values.password,
      };

      const config = {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem("jwtToken")}`,
          "Content-Type": "application/json",
        },
      };
      
      axios
        .post("http://localhost:8080/receptionist/registerPatient", userData, config) // Passing config
        .then(() => {
          toast.success("Patient added successfully!");
          resetForm();
          navigate("/receiptionist/viewPatients"); // Redirect after success
        })
        .catch(() => {
          toast.error("Error adding patient. Please try again.");
        });
      
    },
  });

  return (
    <Receiptionist>
      <ToastContainer />
      <div className="d-flex justify-content-center align-items-center vh-40 mt-5">
        <div
          className="shadow-lg p-4"
          style={{
            width: "35rem",
            backgroundColor: "white",
            color: "black",
            display: "flex",
            flexDirection: "column",
            border: "3px solid #076cea",
          }}
        >
          <h3 className="text-center mb-2">Add Patient</h3>
          <form onSubmit={formik.handleSubmit}>
            {/* Name Input */}
            <div className="mb-3">
              <label>Name:</label>
              <input
                type="text"
                {...formik.getFieldProps("name")}
                className="form-control"
                style={{ height: "4vh" }}
              />
              {formik.touched.name && formik.errors.name && (
                <div className="text-danger">{formik.errors.name}</div>
              )}
            </div>

            {/* Age Input */}
            <div className="mb-3">
              <label>Age:</label>
              <input
                type="number"
                {...formik.getFieldProps("age")}
                className="form-control"
                style={{ height: "4vh" }}
              />
              {formik.touched.age && formik.errors.age && (
                <div className="text-danger">{formik.errors.age}</div>
              )}
            </div>

            {/* Gender Input */}
            <div className="mb-3">
              <label>Gender:</label>
              <select
                {...formik.getFieldProps("gender")}
                className="form-control"
                style={{ height: "4vh" }}
              >
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
              </select>
              {formik.touched.gender && formik.errors.gender && (
                <div className="text-danger">{formik.errors.gender}</div>
              )}
            </div>

            {/* Weight Input */}
            <div className="mb-3">
              <label>Weight:</label>
              <input
                type="number"
                {...formik.getFieldProps("weight")}
                className="form-control"
                style={{ height: "4vh" }}
              />
              {formik.touched.weight && formik.errors.weight && (
                <div className="text-danger">{formik.errors.weight}</div>
              )}
            </div>

            {/* Email Input */}
            <div className="mb-3">
              <label>Email:</label>
              <input
                type="email"
                {...formik.getFieldProps("email")}
                className="form-control"
                style={{ height: "4vh" }}
              />
              {formik.touched.email && formik.errors.email && (
                <div className="text-danger">{formik.errors.email}</div>
              )}
            </div>

            {/* Phone Input */}
            <div className="mb-3">
              <label>Phone:</label>
              <input
                type="text"
                {...formik.getFieldProps("phone")}
                className="form-control"
                style={{ height: "4vh" }}
              />
              {formik.touched.phone && formik.errors.phone && (
                <div className="text-danger">{formik.errors.phone}</div>
              )}
            </div>

            {/* Password Input */}
            <div className="mb-3">
              <label>Password:</label>
              <input
                type="password"
                {...formik.getFieldProps("password")}
                className="form-control"
                style={{ height: "4vh" }}
              />
              {formik.touched.password && formik.errors.password && (
                <div className="text-danger">{formik.errors.password}</div>
              )}
            </div>

            <div className="mb-2 w-100">
              <button
                type="submit"
                className="btn btn-light w-100"
                style={{ backgroundColor: "#076cea" }}
              >
                Add Patient
              </button>
            </div>
          </form>
        </div>
      </div>
    </Receiptionist>
  );
}

export default AddPatient;
