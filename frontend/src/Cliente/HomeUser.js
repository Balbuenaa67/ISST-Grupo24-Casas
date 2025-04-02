import React from "react";
import "./HomeUser.css"; // Archivo CSS para los estilos
import { useNavigate } from "react-router-dom";

const HomeUser = ({ nombre }) => {
  const navigate = useNavigate();

  const handleViewReservations = () => {
    navigate("/reservas"); // Redirige a la pantalla de reservas
  };

  return (
    <div className="home-user-container">
      <div className="overlay">
        <div className="welcome-box">
          <p>
            Buenas <strong>{nombre}</strong>, ¿quieres acceder a <strong>tus reservas</strong>?
          </p>
            <a href="/HomeUser/ClientAccess">
                  🔑 Ver accesos
            </a>
        </div>
      </div>
    </div>
  );
};

export default HomeUser;

