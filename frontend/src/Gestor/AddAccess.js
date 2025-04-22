import React, { useState } from 'react';
import { useAuth } from "../context/AuthContext";
import './AddAccess.css';

const AddAccess = () => {
    // Estado para los datos del formulario
    const { user } = useAuth();
    const [formData, setFormData] = useState({
        clave: '',
        fechainicio: '',
        fechafinal: '',
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

        const parseDateTimeLocal = (value) => new Date(value + ":00");

        // Validaciones
        const currentDate = new Date();
        const fechainicio = parseDateTimeLocal(formData.fechainicio);
        const fechafinal = parseDateTimeLocal(formData.fechafinal);

        console.log('fechainicio:', formData.fechainicio);
        console.log('fechafinal:', formData.fechafinal);


        // Validar que fechainicio sea mayor que la fecha actual
        if (fechainicio <= currentDate) {
            setError("La fecha de inicio debe ser mayor que la fecha actual.");
            return;
        }

        // Validar que fechafinal sea mayor que fechainicio
        if (fechafinal <= fechainicio) {
            setError("La fecha final debe ser mayor que la fecha de inicio.");
            return;
        }
    
        try {
            const googleToken = localStorage.getItem('googleToken'); // 🟡 Captura del token de Google

            const payload = {
                ...formData,
                fechainicio: fechainicio.toISOString(),
                fechafin: fechafinal.toISOString(),
                googleToken: googleToken // 🟢 Añadimos el token dentro del JSON que enviamos
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
                    fechainicio: "",
                    fechafinal: "",
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
                        <input type="datetime-local" name="fechainicio" value={formData.fechainicio} onChange={handleChange} required />

                        <label>Fecha Fin:</label>
                        <input type="datetime-local" name="fechafinal" value={formData.fechafinal} onChange={handleChange} required />

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