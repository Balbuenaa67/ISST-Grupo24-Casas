import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsLoggedIn(!!token);
  }, [location]); // actualiza estado cada vez que cambia la ruta

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    navigate('/Login');
  };

  return (
    <nav className="navbar">
      <ul className="navbar-list">
        <li>
          <Link to="/" className="navbar-link">
            <a 
              className="navbar-logo"
            />
          </Link>
        </li>
        <li className="navbar-item">
          <Link to="/" className="navbar-link">Home</Link>
        </li>
        {!isLoggedIn ? (
          <>
            <li className="navbar-item">
              <Link to="/Login" className="navbar-link">Login</Link>
            </li>
            <li className="navbar-item">
              <Link to="/SignUp" className="navbar-link">Sign Up</Link>
            </li>
          </>
        ) : (
          <>
            <li className="navbar-item">
              <Link to="/ManagementPanel" className="navbar-link">Panel de control</Link>
            </li>
            <li className="navbar-item">
              <Link to="/HomeUser" className="navbar-link">Panel de usuario</Link>
            </li>
            <li className="navbar-item-logout">
              <button className="navbar-link logout-button" onClick={handleLogout}>
                Logout
              </button>
            </li>
          </>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
