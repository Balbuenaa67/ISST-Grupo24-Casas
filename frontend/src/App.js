import React, { useEffect, useState } from "react";
import axios from "axios";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";

import HomePage from './SignUp/HomePage';
import LoginPage from './SignUp/Login';
import RegisterPage from './SignUp/SignUp';

import ManagementPanel from './Gestor/ManagementPanel';
import AddAccess from './Gestor/AddAccess';
import Access from './Gestor/Access';
import EditAccess from './Gestor/EditAccess';
import AddLock from './Gestor/AddLock';
import Lock from './Gestor/Lock';
import EditLock from './Gestor/EditLock';
import Client from './Gestor/Client';

import HomeUser from './Cliente/HomeUser';
import ClientAccess from './Cliente/ClientAccess';
import AccessDetails from './Cliente/AccessDetails';

import Navbar from './main/Navbar';  
import Footer from './main/Footer';  

import ProtectedRoute from "./context/ProtectedRoute";

function App() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8080/api/mensaje")
      .then(response => setMessage(response.data))
      .catch(error => console.error("Error:", error));
  }, []);

  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/Login" element={<LoginPage />} />
          <Route path="/SignUp" element={<RegisterPage />} />

          {/* Rutas protegidas */}
          <Route element={<ProtectedRoute />}>
            <Route path="/ManagementPanel" element={<ManagementPanel />} />
            <Route path="/ManagementPanel/AddAccess" element={<AddAccess />} />
            <Route path="/ManagementPanel/Access" element={<Access />} />
            <Route path="/ManagementPanel/EditAccess/:id" element={<EditAccess />} />
            <Route path="/ManagementPanel/AddLock" element={<AddLock />} />
            <Route path="/ManagementPanel/Lock" element={<Lock />} />
            <Route path="/ManagementPanel/EditLock/:id" element={<EditLock />} />
            <Route path="/ManagementPanel/Clients" element={<Client />} />

            <Route path="/HomeUser" element={<HomeUser />} />
            <Route path="/HomeUser/ClientAccess" element={<ClientAccess />} />
            <Route path="/HomeUser/AccessDetails/:id" element={<AccessDetails />} />
          </Route>

        </Routes>
        <Footer />
      </Router>
    </AuthProvider>
  );
}

export default App;

