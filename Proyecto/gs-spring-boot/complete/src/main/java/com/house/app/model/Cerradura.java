package com.house.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cerradura {

    @Id
    @Column(length = 4, unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private Boolean abierto;

    @Column(nullable = false)
    private String clave;
}
