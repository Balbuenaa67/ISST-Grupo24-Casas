import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import './AccessDetails.css';

const AccessDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [access, setAccess] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAccessDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/cerraduras/${id}`);
        if (response.ok) {
          const data = await response.json();
          setAccess(data);
        } else {
          setError("No se pudo obtener la información del acceso.");
        }
      } catch (error) {
        setError("Hubo un error en la conexión.");
      }
    };

    fetchAccessDetails();
  }, [id]);

  const abrirCerradura = async () => {
    if (!access) return;

    console.log(access);
    
    const requestData = {
      clave: access.clave,
      cerradura: access.nombre
    };

    try {
      const response = await fetch('http://localhost:8080/api/apertura/abrir', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestData),
      });

      const resultText = await response.text();

      if (response.ok) {
        alert(`✅ ${resultText}`);
      } else {
        alert(`❌ ${resultText}`);
      }
    } catch (error) {
      alert("❌ Error al conectar con el servidor");
    }
  };

    const cerrarCerradura = async () => {
      if (!access) return;
  
      console.log(access);
      
      const requestData = {
        clave: access.clave,
        cerradura: access.nombre
      };
  
      try {
        const response = await fetch('http://localhost:8080/api/apertura/cerrar', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(requestData),
        });
  
        const resultText = await response.text();
  
        if (response.ok) {
          alert(`✅ ${resultText}`);
        } else {
          alert(`❌ ${resultText}`);
        }
      } catch (error) {
        alert("❌ Error al conectar con el servidor");
      }
  };

  if (error) return <p className="error-message">{error}</p>;
  if (!access) return <p>Cargando detalles...</p>;

  return (
    <div className="access-details-container">
      <h2>Detalles del Acceso</h2>
      <p><strong>Fecha Inicio:</strong> {new Date(access.fechainicio).toLocaleString()}</p>
      <p><strong>Fecha Fin:</strong> {new Date(access.fechafin).toLocaleString()}</p>
      <p><strong>Dirección:</strong> {access.direccion}</p>
      <p><strong>Gestor:</strong> +34 {access.gestor.telefono} - {access.gestor.nombre}</p>
      
      <button className="open-button" onClick={abrirCerradura}>
        🔓 Abrir Cerradura
      </button>
      <button className="close-button" onClick={cerrarCerradura}>
        🔒 Cerrar Cerradura
      </button>
      <button className="back-button" onClick={() => navigate(-1)}>
        ⬅ Volver
      </button>
    </div>
  );
};

export default AccessDetails;
