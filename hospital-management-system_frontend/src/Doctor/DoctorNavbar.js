import React from "react";
import { useNavigate, NavLink } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import { Navbar, Container, Nav, Button } from "react-bootstrap";
import { FaUserCircle, FaSignOutAlt } from "react-icons/fa";
import "react-toastify/dist/ReactToastify.css";
import "./Styles.css";

function DoctorNavbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    sessionStorage.clear();
    navigate("/");
  };

  return (
    <div>
      <Navbar expand="lg" className="px-5 custom-navbar">
        <Container fluid>
          <Navbar.Brand
            as={NavLink}
            to="/doctor/viewAppointments"
            className="d-flex align-items-center text-decoration-none"
          >
            <span className="logo-style white-logo">DocSlot</span>
          </Navbar.Brand>

          <Navbar.Toggle aria-controls="navbar-nav" />
          <Navbar.Collapse id="navbar-nav">
            <Nav className="ms-auto d-flex align-items-center gap-3">
              <span className="admin-badge">
                <FaUserCircle />
                {sessionStorage.getItem("userName")}
              </span>
              <Button variant="danger" className="logout-btn" onClick={handleLogout}>
                <FaSignOutAlt /> Logout
              </Button>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <ToastContainer />
    </div>
  );
}

export default DoctorNavbar;
