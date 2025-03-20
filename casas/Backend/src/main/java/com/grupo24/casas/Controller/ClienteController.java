package com.grupo24.casas.Controller;

import com.grupo24.casas.Models.Cliente;
import com.grupo24.casas.Repository.ClienteRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtener todos los clientes
    @GetMapping
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    // Obtener un cliente por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok().body(cliente))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener un cliente por su nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Cliente> getClienteByNombre(@PathVariable String nombre) {
        return clienteRepository.findByNombre(nombre)
                .map(cliente -> ResponseEntity.ok().body(cliente))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> anadirCliente(@RequestBody Cliente nuevoCliente) throws URISyntaxException {
        Cliente result = clienteRepository.save(nuevoCliente);
        return ResponseEntity.created(new URI("/clientes/" + nuevoCliente.getId())).body(result);
    }

    // Actualizar la información de un cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setDireccion(clienteActualizado.getDireccion());
            clienteRepository.save(cliente);
            return ResponseEntity.ok().body(cliente);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        return clienteRepository.findById(id).map(cliente -> {
            clienteRepository.delete(cliente);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
