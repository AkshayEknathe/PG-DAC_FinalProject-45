import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./Contact.css";

function Contact() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    email: "",
    message: "",
  });

  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};

    if (!form.name.trim()) {
      newErrors.name = "Name is required";
    } else if (!/^[A-Za-z\s]+$/.test(form.name)) {
      newErrors.name = "Name must contain only letters";
    }

    if (!form.email.trim()) {
      newErrors.email = "Email is required";
    } else if (!/\S+@\S+\.\S+/.test(form.email)) {
      newErrors.email = "Email is invalid";
    }

    if (!form.message.trim()) {
      newErrors.message = "Message is required";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: "" });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!validate()) return;

    // Show success toast immediately
    toast.success("Message sent successfully!", {
      position: "top-center",
      autoClose: 2500,
    });

    // Clear the form immediately for UX
    setForm({ name: "", email: "", message: "" });

    // Submit form in background
    fetch("https://getform.io/f/bmdmzpea", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(form),
    }).catch((err) => {
      console.error("Error sending message to Getform:", err);
    });
  };

  return (
    <div className="contact-container">
      <ToastContainer />

      <div className="contact-header">
        <h1>Contact Us</h1>
        <p>We’re here to help you with your healthcare journey</p>
      </div>

      <div className="contact-content">
        <div className="contact-left">
          <h3>Reach Out</h3>
          <p>Email: teamdocslot@gmail.com</p>
          <p>Phone: +91 98765 xxxx</p>
          <p>Address: 123 Health Avenue, Bengaluru, India</p>
          <p>Support Hours: Mon - Sat, 9:00 AM to 6:00 PM</p>
        </div>

        <div className="contact-right">
          <h3>Send Us a Message</h3>
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              name="name"
              placeholder="Your Name"
              value={form.name}
              onChange={handleChange}
            />
            {errors.name && <span className="error-text">{errors.name}</span>}

            <input
              type="email"
              name="email"
              placeholder="Your Email"
              value={form.email}
              onChange={handleChange}
            />
            {errors.email && (
              <span className="error-text">{errors.email}</span>
            )}

            <textarea
              name="message"
              placeholder="Your Message"
              rows="5"
              value={form.message}
              onChange={handleChange}
            ></textarea>
            {errors.message && (
              <span className="error-text">{errors.message}</span>
            )}

            <button type="submit">Submit</button>
          </form>
        </div>
      </div>

      <div className="back-button-container">
        <button className="back-home-btn" onClick={() => navigate("/")}>
          ⬅ Back to Home
        </button>
      </div>
    </div>
  );
}

export default Contact;
