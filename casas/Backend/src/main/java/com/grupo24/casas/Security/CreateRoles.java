package com.grupo24.casas.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grupo24.casas.Repository.RolRepository;
import com.grupo24.casas.Models.Rol;
import com.grupo24.casas.Enums.RolEnum;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        Rol rolAdmin = new Rol(RolEnum.ROLE_ADMIN);
        Rol rolUser = new Rol(RolEnum.ROLE_USER);
        rolRepository.save(rolAdmin);
        rolRepository.save(rolUser);
    }
}