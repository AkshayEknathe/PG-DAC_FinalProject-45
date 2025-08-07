import React, { useState } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import { useNavigate, Link } from "react-router-dom";
import "react-toastify/dist/ReactToastify.css";
import CustomerNavbar from "./CustomerNavbar";

function Login() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const togglePassword = () => {
    setShowPassword((prev) => !prev);
  };

  const formik = useFormik({
    initialValues: {
      email: "",
      password: "",
    },
    validationSchema: Yup.object({
      email: Yup.string().email("Invalid email").required("Email is required"),
      password: Yup.string().required("Password is required"),
    }),
    onSubmit: async (values) => {
      setLoading(true);
      try {
        const response = await axios.post("http://localhost:8080/login", values);
        toast.success("Login Successful!", { position: "top-right", autoClose: 1500 });

        const user = response.data;
        sessionStorage.setItem("userName", user.authenticatedDetails.principal.name);
        sessionStorage.setItem("userId", user.authenticatedDetails.principal.id);
        sessionStorage.setItem("userRole", user.authenticatedDetails.principal.role);
        sessionStorage.setItem("jwtToken", user.jwt);

        const role = user.authenticatedDetails.principal.role;
        if (role === "ROLE_PATIENT") navigate("/");
        else if (role === "ROLE_ADMIN") navigate("/admin");
        else if (role === "ROLE_DOCTOR") navigate("/doctor");
        else if (role === "ROLE_RECEPTIONIST") navigate("/receiptionist");
      } catch (error) {
        toast.error("Invalid email or password!", { position: "top-right", autoClose: 1500 });
      } finally {
        setLoading(false);
      }
    },
  });

  return (
    <div style={{ backgroundColor: "#f0f4f8", minHeight: "100vh" }}>
      <CustomerNavbar />
      <ToastContainer />
      <div className="d-flex justify-content-center align-items-center min-vh-100">
        <div
          className="card p-4 shadow-lg"
          style={{
            maxWidth: "420px",
            width: "100%",
            borderRadius: "1rem",
            backgroundColor: "#ffffff",
            border: "1px solid #dee2e6",
          }}
        >
          <h3 className="text-center text-primary fw-bold mb-4">Login</h3>
          <form onSubmit={formik.handleSubmit}>
            {/* Email Field with Icon */}
            <div className="mb-3">
              <label htmlFor="email" className="form-label fw-semibold">Email</label>
              <div className="input-group">
                <span className="input-group-text bg-light">
                  <i className="fas fa-envelope"></i>
                </span>
                <input
                  id="email"
                  type="email"
                  {...formik.getFieldProps("email")}
                  className={`form-control ${formik.touched.email && formik.errors.email ? "is-invalid" : ""}`}
                  placeholder="Enter your email"
                />
              </div>
              {formik.touched.email && formik.errors.email && (
                <div className="invalid-feedback d-block">{formik.errors.email}</div>
              )}
            </div>

            {/* Password Field with Eye Icon */}
            <div className="mb-3">
              <label htmlFor="password" className="form-label fw-semibold">Password</label>
              <div className="input-group">
                <span className="input-group-text bg-light">
                  <i className="fas fa-lock"></i>
                </span>
                <input
                  id="password"
                  type={showPassword ? "text" : "password"}
                  {...formik.getFieldProps("password")}
                  className={`form-control ${formik.touched.password && formik.errors.password ? "is-invalid" : ""}`}
                  placeholder="Enter your password"
                />
                <span className="input-group-text bg-light" onClick={togglePassword} style={{ cursor: "pointer" }}>
                  <i className={`fas ${showPassword ? "fa-eye-slash" : "fa-eye"}`}></i>
                </span>
              </div>
              {formik.touched.password && formik.errors.password && (
                <div className="invalid-feedback d-block">{formik.errors.password}</div>
              )}
            </div>

            {/* Submit Button with Spinner */}
            <div className="d-grid mt-4">
              <button type="submit" className="btn btn-primary btn-lg" disabled={loading}>
                {loading ? (
                  <>
                    <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                    Logging in...
                  </>
                ) : (
                  "Login"
                )}
              </button>
            </div>
          </form>

          {/* Register Link */}
          <div className="text-center mt-4">
            <p className="mb-0">Don't have an account?</p>
            <Link to="/register" className="text-decoration-none text-primary fw-semibold">
              Register here
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
