import React from "react";
import { NavLink } from "react-router-dom";
import "@fortawesome/fontawesome-free/css/all.min.css";
import "./Footer.css";

function Footer() {
  return (
    <footer className="footer py-5">
      <div className="container1">
        <div className="row">
          {/* About DocSlot */}
          <div className="col-lg-4 col-md-6 mb-4">
            <h4>About DocSlot</h4>
            <p>
              DocSlot is your one-stop platform for hassle-free doctor
              appointment scheduling. Whether you need a general consultation or
              a specialist, DocSlot makes it easy to find trusted doctors, view
              their availability, and book appointments online — anytime,
              anywhere. We bring convenience and efficiency to your healthcare
              journey.
            </p>
          </div>

          {/* Quick Links */}
          <div className="col-lg-4 col-md-6 mb-4">
            <h4>Quick Links</h4>
            <ul className="list-unstyled">
              <li>
                <NavLink to="/">Home</NavLink>
              </li>
              <li>
                <NavLink to="/about">About</NavLink>
              </li>
              <li>
                <NavLink to="/services">Services</NavLink>
              </li>
              <li>
                <NavLink to="/contact">Contact</NavLink>
              </li>
            </ul>
          </div>

          {/* Contact Info */}
          <div className="col-lg-4 col-md-6 mb-4">
            <h4>Contact Us</h4>
            <ul className="list-unstyled">
              <li>123 Health Avenue</li>
              <li>Bengaluru, India</li>
              <li>Email: teamdocslot@gmail.com</li>
              <li>Phone: +91 98765 xxxx</li>
            </ul>
          </div>
        </div>

        {/* Social Media Icons */}
        <div className="d-flex justify-content-center mt-4 mb-2">
          <a
            href="https://www.linkedin.com/"
            className="btn btn-floating mx-2"
            style={{ backgroundColor: "#0082ca", color: "white" }}
          >
            <i className="fab fa-linkedin-in"></i>
          </a>
          <a
            href="https://github.com/"
            className="btn btn-floating mx-2"
            style={{ backgroundColor: "#333333", color: "white" }}
          >
            <i className="fab fa-github"></i>
          </a>
          <a
            href="https://www.facebook.com/"
            className="btn btn-floating mx-2"
            style={{ backgroundColor: "#3b5998", color: "white" }}
          >
            <i className="fab fa-facebook-f"></i>
          </a>
          <a
            href="https://x.com/"
            className="btn btn-floating mx-2"
            style={{ backgroundColor: "#55acee", color: "white" }}
          >
            <i className="fab fa-twitter"></i>
          </a>
          {/* <a href="#!" className="btn btn-floating mx-2" style={{ backgroundColor: "#dd4b39", color: "white" }}>
            <i className="fab fa-google"></i>
          </a> */}
          {/* <a href="#!" className="btn btn-floating mx-2" style={{ backgroundColor: "#ac2bac", color: "white" }}>
            <i className="fab fa-instagram"></i>
          </a> */}
        </div>

        {/* Copyright */}
        <div className="text-center mt-3 copyright">
          © 2025 DocSlot — Online Appointment System
        </div>
      </div>
    </footer>
  );
}

export default Footer;
