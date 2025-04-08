package com.grupo24.cerraduras_casas.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo24.cerraduras_casas.Model.Acceso;
import com.grupo24.cerraduras_casas.Model.Cerradura;
import com.grupo24.cerraduras_casas.Repository.AccesoRepository;
import com.grupo24.cerraduras_casas.Repository.CerraduraRepository;
import com.grupo24.cerraduras_casas.Service.SeamService;
import com.grupo24.cerraduras_casas.Service.TokenService;
import com.grupo24.cerraduras_casas.payload.request.AperturaRequest;

@RestController
@RequestMapping("/api/apertura")
public class AperturaController {

    @Autowired
    private AccesoRepository accesoRepository;

    @Autowired
    private CerraduraRepository cerraduraRepository;

    @Autowired
    private SeamService seamService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/abrir")
    public ResponseEntity<?> abrirCerradura(@RequestBody AperturaRequest request) {
        Optional<Cerradura> cerraduraOpt = cerraduraRepository.findByNombre(request.getCerradura());

        if (cerraduraOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cerradura no encontrada");
        }

        Cerradura cerradura = cerraduraOpt.get();

        // Buscar accesos por cerradura y verificar la clave con tokenService
        List<Acceso> accesos = accesoRepository.findByCerradura(request.getCerradura());
        Acceso accesoValido = null;

        for (Acceso acc : accesos) {
            if (tokenService.matches(request.getClave(), acc.getClave())) {
                accesoValido = acc;
                break;
            }
        }

        if (accesoValido == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
        }

        Date ahora = new Date();

        // Verificar que el acceso esté dentro del horario permitido
        if (ahora.before(accesoValido.getFechainicio()) || ahora.after(accesoValido.getFechafin())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso fuera del horario permitido");
        }

        // Verificar que la clave de la cerradura también coincida con la introducida
        if (!tokenService.matches(request.getClave(), cerradura.getClave())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Clave incorrecta para esta cerradura");
        }

        try {
            String deviceId = cerradura.getNombre();
            String respuesta = seamService.abrirCerradura(deviceId).block();

            cerradura.setAbierto(true);
            cerraduraRepository.save(cerradura);

            return ResponseEntity.ok("Cerradura abierta exitosamente vía Seam. Respuesta: " + respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al contactar con Seam: " + e.getMessage());
        }
    }
}