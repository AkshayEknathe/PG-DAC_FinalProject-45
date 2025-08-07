import React from 'react';
import { useNavigate, NavLink } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import { Navbar, Container, Nav, Button } from 'react-bootstrap';
import { FaUserCircle, FaSignOutAlt } from 'react-icons/fa';
import 'react-toastify/dist/ReactToastify.css';
import "./Styles.css";

function AdminNavbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    sessionStorage.clear();
    toast.success("Logged out successfully");
    navigate('/');
  };

  return (
    <>
      <Navbar variant="dark" expand="lg" className="px-4 custom-navbar">
        <Container fluid>
          <Navbar.Brand as={NavLink} to="/admin" className="text-white fw-bold">
            DocSlot
          </Navbar.Brand>

          <Navbar.Toggle aria-controls="navbar-nav" />
          <Navbar.Collapse id="navbar-nav">
            <Nav className="ms-auto align-items-center gap-3">
              <span className="admin-badge">
                <FaUserCircle />
                {sessionStorage.getItem("userName")}
              </span>
              <Button
                variant="danger"
                className="logout-btn"
                onClick={handleLogout}
              >
                <FaSignOutAlt /> Logout
              </Button>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <ToastContainer />
    </>
  );
}

export default AdminNavbar;
