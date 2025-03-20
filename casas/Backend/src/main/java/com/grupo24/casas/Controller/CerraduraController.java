package com.grupo24.casas.Controller;

import com.grupo24.casas.Models.Cerradura;
import com.grupo24.casas.Repository.CerraduraRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/cerraduras")
@CrossOrigin(origins = "http://localhost:3000")
public class CerraduraController {

    @Autowired
    private CerraduraRepository cerraduraRepository;

    // Obtener todas las cerraduras
    @GetMapping
    public List<Cerradura> getCerraduras() {
        return cerraduraRepository.findAll();
    }

    // Obtener una cerradura por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Cerradura> getCerraduraById(@PathVariable Long id) {
        return cerraduraRepository.findById(id)
                .map(cerradura -> ResponseEntity.ok().body(cerradura))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear una nueva cerradura
    @PostMapping
    public ResponseEntity<Cerradura> anadirCerradura(@RequestBody Cerradura nuevaCerradura) throws URISyntaxException {
        Cerradura result = cerraduraRepository.save(nuevaCerradura);
        return ResponseEntity.created(new URI("/cerraduras/" + nuevaCerradura.getId())).body(result);
    }

    // Actualizar el estado de una cerradura (por ejemplo, abierta/cerrada)
    @PutMapping("/{id}")
    public ResponseEntity<Cerradura> actualizarCerradura(@PathVariable Long id, @RequestBody Cerradura cerraduraActualizada) {
        return cerraduraRepository.findById(id).map(cerradura -> {
            cerradura.setAbierto(cerraduraActualizada.getAbierto());
            cerraduraRepository.save(cerradura);
            return ResponseEntity.ok().body(cerradura);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar una cerradura
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCerradura(@PathVariable Long id) {
        return cerraduraRepository.findById(id).map(cerradura -> {
            cerraduraRepository.delete(cerradura);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
