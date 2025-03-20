package com.grupo24.casas.Repository;

import com.grupo24.casas.Models.Acceso;
import com.grupo24.casas.Models.Cliente;
import com.grupo24.casas.Models.Gestor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccesoRepository extends JpaRepository<Acceso, String> {

    // Buscar accesos por Cliente
    List<Acceso> findByCliente(Cliente cliente);

    // Buscar accesos por Gestor
    List<Acceso> findByGestor(Gestor gestor);

    // Buscar accesos activos (dentro del rango de fechas)
    List<Acceso> findByFechaInicioBeforeAndFechaFinAfter(java.util.Date ahoraInicio, java.util.Date ahoraFin);

}
