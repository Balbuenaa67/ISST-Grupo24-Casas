package com.grupo24.casas.Security.Service;

import com.grupo24.casas.Models.Cliente;
import com.grupo24.casas.Repository.ClienteRepository;
import com.grupo24.casas.Security.Modelo.ClienteSecure;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class ClienteDetailsService implements UserDetailsService {

    @Autowired
    //private GestorRepository gestorRepository;
    private ClienteService  clienteService;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return ClienteSecure.build(cliente);
    }

}



// package com.grupo24.casas.Security.Controller;

// import com.grupo24.casas.Models.Cliente;
// import com.grupo24.casas.Repository.ClienteRepository;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// public class AuthController {

//     private final ClienteRepository clienteRepository;
//     private final PasswordEncoder passwordEncoder;

//     public AuthController(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
//         this.clienteRepository = clienteRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @PostMapping("/register")
//     public ResponseEntity<String> register(@RequestBody Cliente cliente) {
//         cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
//         clienteRepository.save(cliente);
//         return ResponseEntity.ok("Usuario registrado exitosamente.");
//     }
// }