package com.grupo24.cerraduras_casas.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "CERRADURAS")
public class Cerradura {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 4, unique = true, nullable = false)
    private Long id;
    
    @NotEmpty private String direccion;
    
    @NotEmpty private String nombre;

    @NotEmpty private Boolean abierto;

    @NotEmpty private String clave;

    // Relación Many-to-One con Gestor
    @ManyToOne
    @JoinColumn(name = "gestor_dni", referencedColumnName = "dni") // Foreign key
    private Gestor gestor;

}