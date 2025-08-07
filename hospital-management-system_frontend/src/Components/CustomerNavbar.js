import React, { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./Styles.css";
import { BsPersonCircle, BsCalendarCheckFill } from "react-icons/bs";
import { toast } from "react-toastify";

function CustomerNavbar() {
  const [isProfileDropdownOpen, setIsProfileDropdownOpen] = useState(false);
  const [isAppointmentDropdownOpen, setIsAppointmentDropdownOpen] =
    useState(false);
  const navigate = useNavigate();
  const userId = sessionStorage.getItem("userId");

  // Toggle Profile Dropdown
  const handleProfileMouseEnter = () => setIsProfileDropdownOpen(true);
  const handleProfileMouseLeave = () => setIsProfileDropdownOpen(false);

  // Toggle Appointment Dropdown
  const handleAppointmentMouseEnter = () => setIsAppointmentDropdownOpen(true);
  const handleAppointmentMouseLeave = () => setIsAppointmentDropdownOpen(false);

  // Navigation Handlers
  const handleLoginClick = () => navigate("/login");
  const handleRegisterClick = () => navigate("/register");
  const handleLogoutClick = () => {
    sessionStorage.removeItem("userId");
    navigate("/");
  };

  return (
    <nav
      className="navbar navbar-expand-lg px-3"
      style={{ minHeight: "13vh", backgroundColor: "white" }}
    >
      <div className="container-fluid">
        {/* Brand Logo */}
        <NavLink
          className="navbar-brand text-white d-flex align-items-center"
          to="/"
          style={{ color: "#076cea" }}
        >
          <span className="logo-style1" style={{ color: "#076cea" }}>
            DocSlot
          </span>
        </NavLink>

        {/* Toggler for Mobile View */}
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
            {/* Profile Dropdown */}
            <li
              className="nav-item dropdown"
              onMouseEnter={handleProfileMouseEnter}
              onMouseLeave={handleProfileMouseLeave}
            >
              <button
                className="btn btn-black"
                style={{
                  color: "#076cea",
                  fontWeight: "bold",
                  border: "2px solid #076cea",
                  marginTop: "11%",
                }}
              >
                <BsPersonCircle size={20} style={{ marginRight: "8px" }} />
                {userId ? "Profile" : "Sign In"}
              </button>

              {isProfileDropdownOpen && (
                <div
                  className="dropdown-menu show"
                  style={{
                    position: "absolute",
                    top: "100%",
                    left: "50%",
                    transform: "translateX(-50%)",
                    minWidth: "160px",
                    backgroundColor: "#ffffff",
                    border: "1px solid #076cea",
                    borderRadius: "6px",
                    boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
                    padding: "8px 12px",
                    zIndex: 1000,
                  }}
                >
                  {userId ? (
                    <button
                      className="dropdown-item"
                      style={{
                        padding: "8px 12px",
                        borderRadius: "4px",
                      }}
                      onClick={handleLogoutClick}
                    >
                      Logout
                    </button>
                  ) : (
                    <>
                      <button
                        className="dropdown-item"
                        style={{
                          padding: "8px 12px",
                          borderRadius: "4px",
                        }}
                        onClick={handleLoginClick}
                      >
                        Login
                      </button>
                      <button
                        className="dropdown-item"
                        style={{
                          padding: "8px 12px",
                          borderRadius: "4px",
                        }}
                        onClick={handleRegisterClick}
                      >
                        Register
                      </button>
                    </>
                  )}
                </div>
              )}
            </li>

            <li className="nav-item">
              <NavLink
                className="nav-link fs-4 fw-semibold"
                to={`/viewappointments`}
              >
                <button
                  type="button"
                  className="btn btn-white"
                  style={{
                    color: "#076cea",
                    fontWeight: "bold",
                    border: "2px solid #076cea",
                  }}
                >
                  <BsCalendarCheckFill
                    size={20}
                    style={{ marginRight: "8px" }}
                  />
                  Appointments
                </button>
              </NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}

export default CustomerNavbar;
