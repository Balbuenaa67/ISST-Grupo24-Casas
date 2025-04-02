import React, { useState } from 'react';
import { useAuth } from "../context/AuthContext";
import './AddAccess.css';

const AddAccess = () => {
    // Estado para los datos del formulario
    const { user } = useAuth();
    const [formData, setFormData] = useState({
        clave: '',
        fechaInicio: '',
        fechaFinal: '',
        direccion: '',
        cerradura: '',
        gestor: { dni: user?.dni || ""  },
        cliente: { dni: '' }
    });
    
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState("");
    
    // Manejo de cambios en los inputs
    const handleChange = (e) => {
        const { name, value } = e.target;
    
        if (name === "gestor.dni") {
            setFormData({ ...formData, gestor: { ...formData.gestor, dni: value } });
        } else if (name === "cliente.dni") {
            setFormData({ ...formData, cliente: { ...formData.cliente, dni: value } });
        } else {
            setFormData({ ...formData, [name]: value });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccessMessage("");

        // Validaciones
        const currentDate = new Date();
        const fechaInicio = new Date(formData.fechaInicio);
        const fechaFinal = new Date(formData.fechaFinal);

        console.log('fechaInicio:', formData.fechaInicio);
        console.log('fechaFinal:', formData.fechaFinal);


        // Validar que fechaInicio sea mayor que la fecha actual
        if (fechaInicio <= currentDate) {
            setError("La fecha de inicio debe ser mayor que la fecha actual.");
            return;
        }

        // Validar que fechaFinal sea mayor que fechaInicio
        if (fechaFinal <= fechaInicio) {
            setError("La fecha final debe ser mayor que la fecha de inicio.");
            return;
        }
    
        try {
            const payload = {
                ...formData,
                fechaInicio: fechaInicio.toISOString(),
                fechaFinal: fechaFinal.toISOString()
            };
    
            const response = await fetch("http://localhost:8080/api/accesos", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });
    
            if (response.ok) {
                setSuccessMessage("Acceso añadido.");
                setFormData({
                    clave: "",
                    fechaInicio: "",
                    fechaFinal: "",
                    direccion: "",
                    cerradura: "",
                    gestor: { dni: user?.dni || ""  },
                    cliente: { dni: "" }
                });
            } else {
                setError("Error en el registro del acceso.");
            }
    
        } catch (error) {
            console.error('Error al registrar el acceso:', error);
            alert('Hubo un error al registrar el acceso');
        }
    };

    return (
        <div className="add-access-container">
            <div className="background-overlay">
                <div className="add-access-content">
                    <h2>Registrar Acceso</h2>

                    {/* Muestra los mensajes de error o éxito */}
                    {error && <p className="error-message">{error}</p>}
                    {successMessage && <p className="success-message">{successMessage}</p>}


                    <form onSubmit={handleSubmit}>
                        <label>Clave</label>
                        <input type="text" name="clave" value={formData.clave} onChange={handleChange} required />
                        
                        <label>Fecha Inicio:</label>
                        <input type="datetime-local" name="fechaInicio" value={formData.fechaInicio} onChange={handleChange} required />

                        <label>Fecha Fin:</label>
                        <input type="datetime-local" name="fechaFinal" value={formData.fechaFinal} onChange={handleChange} required />

                        <label>Dirección</label>
                        <input type="text" name="direccion" value={formData.direccion} onChange={handleChange} required />
                        
                        <label>ID Cerradura</label>
                        <input type="text" name="cerradura" value={formData.cerradura} onChange={handleChange} required />
                        
                        <label>DNI del Cliente</label>
                        <input type="text" name="cliente.dni" value={formData.cliente.dni} onChange={handleChange} required />
                        
                        <button type="submit" className="submit-button">Registrar Acceso</button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default AddAccess;