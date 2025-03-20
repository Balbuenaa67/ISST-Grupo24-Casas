package com.grupo24.casas.Controller;

import com.grupo24.casas.Models.Acceso;
import com.grupo24.casas.Repository.AccesoRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/accesos")
@CrossOrigin(origins = "http://localhost:3000")
public class AccesoController {

    @Autowired
    private AccesoRepository accesoRepository;

    // Obtener todos los accesos
    @GetMapping
    public List<Acceso> getAccesos() {
        return accesoRepository.findAll();
    }

    // Obtener un acceso por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Acceso> getAccesoById(@PathVariable Long id) {
        return accesoRepository.findById(id)
                .map(acceso -> ResponseEntity.ok().body(acceso))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener accesos por cliente
    @GetMapping("/cliente/{idCliente}")
    public List<Acceso> getAccesosByCliente(@PathVariable Long idCliente) {
        return accesoRepository.findByIdCliente(idCliente);
    }

    // Obtener accesos por gestor
    @GetMapping("/gestor/{idGestor}")
    public List<Acceso> getAccesosByGestor(@PathVariable Long idGestor) {
        return accesoRepository.findByIdGestor(idGestor);
    }

    // Crear un nuevo acceso
    @PostMapping
    public ResponseEntity<Acceso> anadirAcceso(@RequestBody Acceso nuevoAcceso) throws URISyntaxException {
        Acceso result = accesoRepository.save(nuevoAcceso);
        return ResponseEntity.created(new URI("/accesos/" + nuevoAcceso.getId())).body(result);
    }

    // Actualizar un acceso
    @PutMapping("/{id}")
    public ResponseEntity<Acceso> actualizarAcceso(@PathVariable Long id, @RequestBody Acceso accesoActualizado) {
        return accesoRepository.findById(id).map(acceso -> {
            acceso.setDireccion(accesoActualizado.getDireccion());
            acceso.setClave(accesoActualizado.getClave());
            acceso.setFechaInicio(accesoActualizado.getFechaInicio());
            acceso.setFechaFin(accesoActualizado.getFechaFin());
            accesoRepository.save(acceso);
            return ResponseEntity.ok().body(acceso);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un acceso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAcceso(@PathVariable Long id) {
        return accesoRepository.findById(id).map(acceso -> {
            accesoRepository.delete(acceso);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
