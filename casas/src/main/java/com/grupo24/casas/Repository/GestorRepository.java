package com.grupo24.casas.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo24.casas.Models.Acceso;
import com.grupo24.casas.Models.Cerradura;
import com.grupo24.casas.Models.Gestor;

@Repository
public interface GestorRepository extends JpaRepository<Gestor, String> {
    // Método personalizado para buscar gestores por dni
    Optional<Gestor> findByDNI(String dni);

    // Buscar un gestor por su contraseña (no recomendado en producción por seguridad)
    //Optional<Gestor> findByPassword(String password);
}
