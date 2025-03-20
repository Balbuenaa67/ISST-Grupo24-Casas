package com.grupo24.casas.Models;

import javax.persistence.*;



@Entity
@Table(name = "CERRADURA")
public class Cerradura {

    @Id
    @Column(length = 4, unique = true, nullable = false)
    private String id;

    @Column(name = "DIRECCION", nullable = false)
    private String direccion;

    @Column(name = "ABIERTO", nullable = false)
    private Boolean abierto;

    @Column(name = "CLAVE", nullable = false)
    private String clave;

    @ManyToOne
    @JoinColumn(name = "gestor_dni")
    private Gestor gestor;

    // Constructor vacío
    public Cerradura() {
    }

    // Constructor con parámetros
    public Cerradura(String id, String direccion, Boolean abierto, String clave) {
        this.id = id;
        this.direccion = direccion;
        this.abierto = abierto;
        this.clave = clave;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getAbierto() {
        return abierto;
    }

    public void setAbierto(Boolean abierto) {
        this.abierto = abierto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
