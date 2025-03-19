package com.grupo24.casas.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


import com.grupo24.casas.Models.Cerradura;
import com.grupo24.casas.Models.Gestor;


@Repository
public interface CerraduraRepository extends JpaRepository<Cerradura, String> {
    List<Cerradura> findByDireccion(String direccion);
    List<Cerradura> findByAbierto(Boolean abierto);
    List<Cerradura> findByGestor(Gestor gestor);
    Optional<Cerradura> findByClave(String clave);
}

