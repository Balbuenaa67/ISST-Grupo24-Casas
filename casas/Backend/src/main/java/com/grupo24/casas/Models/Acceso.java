package com.grupo24.casas.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ACCESO")
public class Acceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Autogenerado
    @Column(length = 4, unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String clave;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaFin;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_gestor", nullable = false)
    private Gestor gestor;

    public Acceso(String id, String clave, Date fechaInicio, Date fechaFin, String direccion, Cliente cliente, Gestor gestor) {
        this.id = id;
        this.clave = clave;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.direccion = direccion;
        this.cliente = cliente;
        this.gestor = gestor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

}
