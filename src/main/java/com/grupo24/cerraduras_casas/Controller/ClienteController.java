package com.grupo24.cerraduras_casas.Controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo24.cerraduras_casas.Model.Cliente;
import com.grupo24.cerraduras_casas.Repository.ClienteRepository;
import com.grupo24.cerraduras_casas.Service.TokenService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final TokenService tokenService;
    public static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    public ClienteController(ClienteRepository clienteRepository, TokenService tokenService) {
        this.clienteRepository = clienteRepository;
        this.tokenService = tokenService;
    }

    /**
     * Obtiene el listado de todos los clientes, o los clientes filtrados por nombre.
     * @param nombre El nombre del cliente (opcional).
     * @return La lista de clientes.
     */
    @GetMapping
    public List<Cliente> readAll(@RequestParam(name = "nombre", required = false) String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            return clienteRepository.findByNombre(nombre);
        } else {
            return (List<Cliente>) clienteRepository.findAll();
        }
    }

    /**
     * Crea un nuevo cliente.
     * @param newCliente El cliente a crear.
     * @return La respuesta HTTP con el cliente creado.
     * @throws URISyntaxException Si la URI no es válida.
     */
    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente newCliente) throws URISyntaxException {
        // Comprobamos si ya existe un cliente con el mismo DNI.
        if (clienteRepository.findById(newCliente.getDni()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
            
        // Generar el token a partir de la clave
        String tokenGenerado = tokenService.generateToken(newCliente.getPassword());
        
        // Asignar el token al acceso en lugar de la clave original
        newCliente.setPassword(tokenGenerado);

        Cliente result = clienteRepository.save(newCliente);
        return ResponseEntity.created(new URI("/clientes/" + result.getDni())).body(result);
    }

    /**
     * Obtiene un cliente por su DNI.
     * @param dni El DNI del cliente.
     * @return El cliente con el DNI especificado.
     */
    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> readOne(@PathVariable String dni) {
        Optional<Cliente> cliente = clienteRepository.findById(dni);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza un cliente existente.
     * @param newCliente El cliente con los nuevos datos.
     * @param dni El DNI del cliente a actualizar.
     * @return El cliente actualizado.
     */
    @PutMapping("/{dni}")
    public ResponseEntity<Cliente> update(@RequestBody Cliente newCliente, @PathVariable String dni) {
        return clienteRepository.findById(dni).map(cliente -> {
            cliente.setNombre(newCliente.getNombre());
            cliente.setApellido(newCliente.getApellido());
            cliente.setFechaNacimiento(newCliente.getFechaNacimiento());
            cliente.setTelefono(newCliente.getTelefono());
            cliente.setEmail(newCliente.getEmail());
            cliente.setDireccion(newCliente.getDireccion());
            cliente.setPassword(newCliente.getPassword());
            clienteRepository.save(cliente);
            return ResponseEntity.ok().body(cliente);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza parcialmente un cliente existente.
     * @param newCliente El cliente con los datos a actualizar.
     * @param dni El DNI del cliente.
     * @return El cliente parcialmente actualizado.
     */
    @PatchMapping("/{dni}")
    public ResponseEntity<Cliente> partialUpdate(@RequestBody Cliente newCliente, @PathVariable String dni) {
        return clienteRepository.findById(dni).map(cliente -> {
            if (newCliente.getNombre() != null) cliente.setNombre(newCliente.getNombre());
            if (newCliente.getApellido() != null) cliente.setApellido(newCliente.getApellido());
            if (newCliente.getFechaNacimiento() != null) cliente.setFechaNacimiento(newCliente.getFechaNacimiento());
            if (newCliente.getTelefono() != null) cliente.setTelefono(newCliente.getTelefono());
            if (newCliente.getEmail() != null) cliente.setEmail(newCliente.getEmail());
            if (newCliente.getDireccion() != null) cliente.setDireccion(newCliente.getDireccion());
            if (newCliente.getPassword() != null) cliente.setPassword(newCliente.getPassword());
            clienteRepository.save(cliente);
            return ResponseEntity.ok().body(cliente);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina un cliente por su DNI.
     * @param dni El DNI del cliente a eliminar.
     * @return Respuesta de eliminación.
     */
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> delete(@PathVariable String dni) {
        if (clienteRepository.existsById(dni)) {
            clienteRepository.deleteById(dni);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
