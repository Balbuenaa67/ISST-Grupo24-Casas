package com.grupo24.cerraduras_casas.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GESTORES")
public class Gestor {

    @Id private String dni;
    
    @NotEmpty private String nombre;

    @NotEmpty private String apellido;

    @Temporal(TemporalType.TIMESTAMP) private Date fechaNacimiento;

    @Email @Column(unique = true, nullable = false)
    private String  email;

    @Pattern(regexp = "^[0-9]{9,15}$")
    @Column(unique = true, nullable = false)
    private String telefono; // Cambiado de Integer a String

    @NotEmpty private String password;


}
