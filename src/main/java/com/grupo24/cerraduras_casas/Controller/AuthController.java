package com.grupo24.cerraduras_casas.Controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo24.cerraduras_casas.Model.Gestor;
import com.grupo24.cerraduras_casas.Service.GestorService;
import com.grupo24.cerraduras_casas.Service.JwtUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Permite llamadas desde React
public class AuthController {
    @Autowired
    private GestorService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<Gestor> gestorOpt = userService.findByEmail(email);
        if (gestorOpt.isEmpty() || !userService.verifyPassword(password, gestorOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciales incorrectas"));
        }

        String token = jwtUtil.generateToken(email);
        // return ResponseEntity.ok(Map.of("token", token, "user", Map.of("email", email)));
        Gestor gestor = gestorOpt.get();

        return ResponseEntity.ok(Map.of(
            "token", token,
            "user", Map.of(
                "email", email,
                "dni", gestor.getDni())));
    }

    @GetMapping("/protected")
    public ResponseEntity<?> protectedRoute(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("message", "Token faltante"));
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(403).body(Map.of("message", "Token inválido"));
        }

        String email = jwtUtil.extractEmail(token);
        return ResponseEntity.ok(Map.of("message", "Acceso permitido", "email", email));
    }
}
