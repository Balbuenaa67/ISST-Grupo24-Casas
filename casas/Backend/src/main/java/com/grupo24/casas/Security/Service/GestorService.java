package com.grupo24.casas.Security.Service;

import com.grupo24.casas.Models.Gestor;
import com.grupo24.casas.Repository.GestorRepository;

import com.grupo24.casas.Models.Cliente;
import com.grupo24.casas.Repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestorService {

    @Autowired
    private GestorRepository gestorRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    // public List<Gestor> findAll() {
    //     return gestorRepository.findAll();
    // }

    public Optional<Gestor> findByEmail(String email) {
        return gestorRepository.findByEmail(email);
    }

    // public Gestor saveGestor(Gestor gestor) {
    //     gestor.setPassword(passwordEncoder.encode(gestor.getPassword()));
    //     return gestorRepository.save(gestor);
    // }

    // public void deleteGestor(String dni) {
    //     gestorRepository.deleteById(dni);
    // }
}