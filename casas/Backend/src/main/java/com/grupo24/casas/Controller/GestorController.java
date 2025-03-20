package com.grupo24.casas.Controller;

import com.grupo24.casas.Models.Gestor;
import com.grupo24.casas.Repository.GestorRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/gestores")
@CrossOrigin(origins = "http://localhost:3000")
public class GestorController {

    @Autowired
    private GestorRepository gestorRepository;

    // Obtener todos los gestores
    @GetMapping
    public List<Gestor> getGestores() {
        return gestorRepository.findAll();
    }

    // Obtener un gestor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Gestor> getGestorById(@PathVariable Long id) {
        return gestorRepository.findById(id)
                .map(gestor -> ResponseEntity.ok().body(gestor))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener un gestor por su DNI
    @GetMapping("/dni/{dni}")
    public ResponseEntity<Gestor> getGestorByDni(@PathVariable String dni) {
        return gestorRepository.findByDni(dni)
                .map(gestor -> ResponseEntity.ok().body(gestor))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo gestor
    @PostMapping
    public ResponseEntity<Gestor> anadirGestor(@RequestBody Gestor nuevoGestor) throws URISyntaxException {
        Gestor result = gestorRepository.save(nuevoGestor);
        return ResponseEntity.created(new URI("/gestores/" + nuevoGestor.getId())).body(result);
    }

    // Actualizar un gestor
    @PutMapping("/{id}")
    public ResponseEntity<Gestor> actualizarGestor(@PathVariable Long id, @RequestBody Gestor gestorActualizado) {
        return gestorRepository.findById(id).map(gestor -> {
            gestor.setPassword(gestorActualizado.getPassword());
            gestorRepository.save(gestor);
            return ResponseEntity.ok().body(gestor);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un gestor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGestor(@PathVariable Long id) {
        return gestorRepository.findById(id).map(gestor -> {
            gestorRepository.delete(gestor);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
