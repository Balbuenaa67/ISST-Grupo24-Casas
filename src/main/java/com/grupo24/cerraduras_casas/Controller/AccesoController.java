package com.grupo24.cerraduras_casas.Controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.grupo24.cerraduras_casas.Model.Acceso;
import com.grupo24.cerraduras_casas.Model.Cliente;
import com.grupo24.cerraduras_casas.Model.Gestor;
import com.grupo24.cerraduras_casas.Repository.AccesoRepository;
import com.grupo24.cerraduras_casas.Repository.ClienteRepository;
import com.grupo24.cerraduras_casas.Repository.GestorRepository;
import com.grupo24.cerraduras_casas.Service.TokenService;

import com.grupo24.cerraduras_casas.payload.request.AccesoRequest;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/accesos")
public class AccesoController {

    private final AccesoRepository accesoRepository;
    private final GestorRepository gestorRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    private final TokenService tokenService;

    public static final Logger log = LoggerFactory.getLogger(AccesoController.class);

    @Autowired
    public AccesoController(AccesoRepository accesoRepository, GestorRepository gestorRepository,
            ClienteRepository clienteRepository, TokenService tokenService) {
        this.accesoRepository = accesoRepository;
        this.gestorRepository = gestorRepository;
        this.clienteRepository = clienteRepository;
        this.tokenService = tokenService;
    }

    // @Autowired
    // private GoogleCalendarService googleCalendarService;

    @GetMapping
    public List<Acceso> readAll() {
        return (List<Acceso>) accesoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Acceso> create(@RequestBody Acceso newAcceso) throws URISyntaxException {
        log.info("Intentando crear acceso");

        if (newAcceso.getClave() == null || newAcceso.getClave().isEmpty() ||
                newAcceso.getDireccion() == null || newAcceso.getDireccion().isEmpty() ||
                newAcceso.getCerradura() == null || newAcceso.getCerradura().isEmpty() ||
                newAcceso.getGestor() == null || newAcceso.getGestor().getDni() == null ||
                newAcceso.getCliente() == null || newAcceso.getCliente().getDni() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Gestor> gestor = gestorRepository.findById(newAcceso.getGestor().getDni());
        Optional<Cliente> cliente = clienteRepository.findById(newAcceso.getCliente().getDni());

        if (gestor.isEmpty() || cliente.isEmpty()) {
            log.error("Gestor o Cliente no encontrados en la base de datos.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String tokenGenerado = tokenService.generateToken(newAcceso.getClave());
        newAcceso.setClave(tokenGenerado);
        newAcceso.setGestor(gestor.get());
        newAcceso.setCliente(cliente.get());

        Acceso result = accesoRepository.save(newAcceso);
        log.info("Acceso creado con éxito: {}", result);

        return ResponseEntity.created(new URI("/accesos/" + result.getId())).body(result);
    }

    // @PostMapping("/register-access")
    // public ResponseEntity<?> registerAccess(@RequestBody AccesoRequest accesoRequest) {
    //     try {
    //         log.info("Intentando registrar acceso con Google Calendar...");

    //         // 1️⃣ Validaciones básicas (puedes ampliarlas si quieres)
    //         if (accesoRequest.getClave() == null || accesoRequest.getClave().isEmpty() ||
    //                 accesoRequest.getDireccion() == null || accesoRequest.getDireccion().isEmpty() ||
    //                 accesoRequest.getCerradura() == null || accesoRequest.getCerradura().isEmpty() ||
    //                 accesoRequest.getGestor() == null || accesoRequest.getGestor().getDni() == null ||
    //                 accesoRequest.getCliente() == null || accesoRequest.getCliente().getDni() == null) {
    //             return new ResponseEntity<>("Campos obligatorios vacíos.", HttpStatus.BAD_REQUEST);
    //         }

    //         // 2️⃣ Buscar gestor y cliente en la BBDD
    //         Optional<Gestor> gestor = gestorRepository.findById(accesoRequest.getGestor().getDni());
    //         Optional<Cliente> cliente = clienteRepository.findById(accesoRequest.getCliente().getDni());

    //         if (gestor.isEmpty() || cliente.isEmpty()) {
    //             log.error("Gestor o Cliente no encontrados en la base de datos.");
    //             return new ResponseEntity<>("Gestor o Cliente no encontrados.", HttpStatus.NOT_FOUND);
    //         }

    //         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    //         Date fechaInicio = formatter.parse(accesoRequest.getFechainicio());
    //         Date fechaFin = formatter.parse(accesoRequest.getFechafin());

    //         // 3️⃣ Crear manualmente el objeto Acceso a partir de AccesoRequest
    //         Acceso nuevoAcceso = new Acceso();
    //         nuevoAcceso.setClave(tokenService.generateToken(accesoRequest.getClave())); // genera clave encriptada
    //         nuevoAcceso.setDireccion(accesoRequest.getDireccion());
    //         nuevoAcceso.setCerradura(accesoRequest.getCerradura());
    //         nuevoAcceso.setFechainicio(fechaInicio);
    //         nuevoAcceso.setFechafin(fechaFin);
    //         nuevoAcceso.setGestor(gestor.get());
    //         nuevoAcceso.setCliente(cliente.get());

    //         // 4️⃣ Guardar acceso en base de datos
    //         Acceso accesoGuardado = accesoRepository.save(nuevoAcceso);
    //         log.info("Acceso guardado: {}", accesoGuardado);

    //         // 5️⃣ (En el siguiente paso) Llamaremos aquí a GoogleCalendarService usando
    //         // accesoRequest.getGoogleToken()

    //         return ResponseEntity.ok("Acceso registrado correctamente en BBDD.");

    //     } catch (Exception e) {
    //         log.error("Error registrando acceso: {}", e.getMessage());
    //         return new ResponseEntity<>("❌ Error registrando acceso: " + e.getMessage(),
    //                 HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @GetMapping("/{id}")
    public ResponseEntity<Acceso> readOne(@PathVariable Long id) {
        Optional<Acceso> acceso = accesoRepository.findById(id);
        return acceso.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acceso> update(@RequestBody Acceso newAcceso, @PathVariable Long id) {
        return accesoRepository.findById(id).map(acceso -> {
            acceso.setFechainicio(newAcceso.getFechainicio());
            acceso.setFechafin(newAcceso.getFechafin());
            acceso.setDireccion(newAcceso.getDireccion());
            acceso.setCerradura(newAcceso.getCerradura());
            acceso.setGestor(newAcceso.getGestor());
            acceso.setCliente(newAcceso.getCliente());

            // Solo codifica si la clave nueva no coincide con la ya codificada
            if (!tokenService.matches(newAcceso.getClave(), acceso.getClave())) {
                String tokenGenerado = tokenService.generateToken(newAcceso.getClave());
                acceso.setClave(tokenGenerado);
            }

            accesoRepository.save(acceso);
            return ResponseEntity.ok().body(acceso);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Acceso> partialUpdate(@RequestBody Acceso newAcceso, @PathVariable Long id) {
        return accesoRepository.findById(id).map(acceso -> {
            if (newAcceso.getClave() != null && !tokenService.matches(newAcceso.getClave(), acceso.getClave())) {
                acceso.setClave(tokenService.generateToken(newAcceso.getClave()));
            }
            if (newAcceso.getFechainicio() != null)
                acceso.setFechainicio(newAcceso.getFechainicio());
            if (newAcceso.getFechafin() != null)
                acceso.setFechafin(newAcceso.getFechafin());
            if (newAcceso.getDireccion() != null)
                acceso.setDireccion(newAcceso.getDireccion());
            if (newAcceso.getCerradura() != null)
                acceso.setCerradura(newAcceso.getCerradura());
            if (newAcceso.getGestor() != null)
                acceso.setGestor(newAcceso.getGestor());
            if (newAcceso.getCliente() != null)
                acceso.setCliente(newAcceso.getCliente());

            accesoRepository.save(acceso);
            return ResponseEntity.ok().body(acceso);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (accesoRepository.existsById(id)) {
            accesoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
