import React from "react";
import { useNavigate } from "react-router-dom";
import "./About.css";

function About() {
  const navigate = useNavigate();

  return (
    <div className="about-container">
      <div className="about-header">
        <h1>About DocSlot</h1>
        <p>Your trusted digital healthcare companion</p>
      </div>

      <div className="about-content">
        <p>
          DocSlot is an innovative online appointment platform built to simplify
          how patients connect with doctors. Whether you're looking for a
          specialist or a general consultation, DocSlot ensures you can schedule
          appointments conveniently — anytime, anywhere.
        </p>

        <p>
          Our mission is to modernize healthcare delivery by providing real-time
          doctor availability, secure patient data handling, digital medical
          records, and appointment reminders. With DocSlot, you avoid long wait
          times, manage your health more efficiently, and experience truly
          patient-first care.
        </p>

        <p>
          We empower clinics and hospitals with technology that enhances
          operations, reduces no-shows, and ensures better patient outcomes.
          Whether you're a small clinic or a multi-specialty hospital, DocSlot is
          the bridge to smarter healthcare.
        </p>
      </div>

      <div className="back-button-container">
        <button className="back-home-btn" onClick={() => navigate("/")}>
          ⬅ Back to Home
        </button>
      </div>
    </div>
  );
}

export default About;
