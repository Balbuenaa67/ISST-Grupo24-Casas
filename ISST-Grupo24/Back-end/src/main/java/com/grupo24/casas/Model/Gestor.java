package com.house.app.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;

@Entity
@Table(name = "GESTOR")
public class Gestor {

    @Id
    @Column(length = 9, unique = true, nullable = false)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private String fechaNacimiento;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private Integer telefono;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "gestor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Acceso> accesos;

    @OneToMany(mappedBy = "gestor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cerradura> cerraduras;

    public Gestor() {
    }

    public Gestor(String dni, String nombre, String fechaNacimiento, String email, Integer telefono, String password, List<Acceso> accesos, List<Cerradura> cerraduras) {
        this.dni = dni;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.accesos = accesos;
        this.cerraduras = cerraduras;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Acceso> getAccesos() {
        return accesos;
    }

    public void setAccesos(List<Acceso> accesos) {
        this.accesos = accesos;
    }

    public List<Cerradura> getCerraduras() {
        return cerraduras;
    }

    public void setCerraduras(List<Cerradura> cerraduras) {
        this.cerraduras = cerraduras;
    }
}
