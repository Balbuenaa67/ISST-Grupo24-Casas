package com.house.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
public class Acceso {

    @Id
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

}
