package com.grupo24.cerraduras_casas.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCESOS")
public class Acceso {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty private String clave;
    
    @NotEmpty private String cerradura;

    @Temporal(TemporalType.TIMESTAMP) private Date fechainicio;

    @Temporal(TemporalType.TIMESTAMP) private Date fechafin;

    @Column(length = 255, nullable = false)
    private String direccion;
    
    // Relación Many-to-One con Gestor
    @ManyToOne
    @JoinColumn(name = "gestor_dni", referencedColumnName = "dni") // Foreign key
    private Gestor gestor;

    // Relación Many-to-One con Gestor
    @ManyToOne
    @JoinColumn(name = "cliente_dni", referencedColumnName = "dni")
    private Cliente cliente;  

}