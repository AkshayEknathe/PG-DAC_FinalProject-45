import React, { useEffect, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { FaCalendarAlt } from "react-icons/fa";
import DoctorNavbar from "./DoctorNavbar";
import "./Styles.css";

function Doctor({ children }) {
  const navigate = useNavigate();
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  useEffect(() => {
    const role = sessionStorage.getItem("userRole");
    if (!sessionStorage.getItem("userName")) {
      navigate("/");
    } else if (role === "PATIENT") {
      navigate("/");
    } else if (role === "ADMIN") {
      navigate("/admin");
    } else if (role === "RECEPTIONIST") {
      navigate("/receiptionist");
    }
  }, [navigate]);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  return (
    <div>
      <DoctorNavbar />
      <div className="layout-container">
        {/* Toggle Button for Small Screens */}
        <button
          className="sidebar-toggle"
          onClick={toggleSidebar}
          aria-label={isSidebarOpen ? "Close sidebar" : "Open sidebar"}
        >
          ☰
        </button>

        {/* Sidebar */}
        <aside className={`sidebar glass-sidebar ${isSidebarOpen ? "open" : ""}`}>
          <div className="sidebar-header">Doctor</div>
          <nav className="sidebar-nav">
            <NavLink
              to="/doctor/viewAppointments"
              className={({ isActive }) =>
                isActive ? "sidebar-link active" : "sidebar-link"
              }
              onClick={() => setIsSidebarOpen(false)}
            >
              <FaCalendarAlt className="icon" />
              View Appointments
            </NavLink>
          </nav>
        </aside>

        <main className="main-content">{children}</main>
      </div>
    </div>
  );
}

export default Doctor;