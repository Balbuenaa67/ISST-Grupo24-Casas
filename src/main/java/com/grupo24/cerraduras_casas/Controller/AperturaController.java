package com.grupo24.cerraduras_casas.Controller;

import java.util.Date;
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
import com.grupo24.cerraduras_casas.payload.request.AperturaRequest;

@RestController
@RequestMapping("/api/apertura")
public class AperturaController {

    @Autowired
    private AccesoRepository accesoRepository;

    @Autowired
    private CerraduraRepository cerraduraRepository;

    @PostMapping("/abrir")
    public ResponseEntity<?> abrirCerradura(@RequestBody AperturaRequest request) {
        Optional<Cerradura> cerraduraOpt = cerraduraRepository.findByNombre(request.getCerradura());

        if (cerraduraOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cerradura no encontrada");
        }

        Cerradura cerradura = cerraduraOpt.get();

        // Buscar acceso con los parámetros recibidos
        Optional<Acceso> accesoOpt = accesoRepository.findByCerraduraAndClave(request.getCerradura(), request.getClave());

        if (accesoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
        }

        Acceso acceso = accesoOpt.get();
        Date ahora = new Date();

        // Verificar que el acceso esté dentro del horario permitido
        if (ahora.before(acceso.getFechainicio()) || ahora.after(acceso.getFechafin())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso fuera del horario permitido");
        }

        // Verificar que la clave de la cerradura coincida con la clave de acceso
        if (!cerradura.getClave().equals(request.getClave())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Clave incorrecta para esta cerradura");
        }

        // Cambiar el estado de la cerradura a "abierto"
        cerradura.setAbierto(true);
        cerraduraRepository.save(cerradura); // Guardar la cerradura con el nuevo estado

        return ResponseEntity.ok("Cerradura abierta exitosamente");
    }
}
