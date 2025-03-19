package com.grupo24.casas.Repository;

import com.grupo24.casas.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

    // Buscar cliente por dirección
    List<Cliente> findByDireccion(String direccion);

    // Buscar cliente por DNI (asumiendo que el ID del Cliente es el DNI)
    Optional<Cliente> findById(String dni);

}
