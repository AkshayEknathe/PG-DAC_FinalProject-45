import React, { useEffect, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import {
  FaUserMd,
  FaUserTie,
  FaHospitalSymbol,
  FaStethoscope,
  FaClipboardList,
  FaBars,
} from "react-icons/fa";
import AdminNavbar from "./AdminNavbar";
import "./Styles.css";

function Admin({ children }) {
  const navigate = useNavigate();
  const [showSidebar, setShowSidebar] = useState(false);

  useEffect(() => {
    const role = sessionStorage.getItem("userRole");
    if (!sessionStorage.getItem("userName")) {
      navigate("/");
    } else if (role === "DOCTOR") {
      navigate("/doctor");
    } else if (role === "PATIENT") {
      navigate("/");
    } else if (role === "RECEPTIONIST") {
      navigate("/receiptionist");
    }
  }, [navigate]);

  return (
    <>
      <AdminNavbar />
      <div className="layout-container">
        {/* Sidebar Toggle Button (visible only on small screens) */}
        <button
          className="btn btn-outline-light d-md-none sidebar-toggle"
          onClick={() => setShowSidebar(!showSidebar)}
        >
          <FaBars /> Menu
        </button>

        {/* Sidebar */}
        <div
          className={`sidebar bg-light ${showSidebar ? "d-block" : "d-none"} d-md-flex`}
        >
          <div className="sidebar-header">Admin</div>
          <nav className="sidebar-nav">
            <NavLink
              to="/admin/addSpeciality"
              className={({ isActive }) =>
                isActive ? "sidebar-link active" : "sidebar-link"
              }
              onClick={() => setShowSidebar(false)}
            >
              <FaHospitalSymbol className="icon" /> Add Speciality
            </NavLink>
            <NavLink
              to="/admin/addDoctor"
              className={({ isActive }) =>
                isActive ? "sidebar-link active" : "sidebar-link"
              }
              onClick={() => setShowSidebar(false)}
            >
              <FaUserMd className="icon" /> Add Doctor
            </NavLink>
            <NavLink
              to="/admin/addReceptionist"
              className={({ isActive }) =>
                isActive ? "sidebar-link active" : "sidebar-link"
              }
              onClick={() => setShowSidebar(false)}
            >
              <FaUserTie className="icon" /> Add Receptionist
            </NavLink>
            <NavLink
              to="/admin/viewDoctors"
              className={({ isActive }) =>
                isActive ? "sidebar-link active" : "sidebar-link"
              }
              onClick={() => setShowSidebar(false)}
            >
              <FaStethoscope className="icon" /> View Doctors
            </NavLink>
            <NavLink
              to="/admin/viewReceptionist"
              className={({ isActive }) =>
                isActive ? "sidebar-link active" : "sidebar-link"
              }
              onClick={() => setShowSidebar(false)}
            >
              <FaClipboardList className="icon" /> View Receptionist
            </NavLink>
          </nav>
        </div>

        {/* Main Content */}
        <div className="main-content">{children}</div>
      </div>
    </>
  );
}

export default Admin;
