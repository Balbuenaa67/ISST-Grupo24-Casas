package com.grupo24.casas.Security.Modelo;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.grupo24.casas.Models.Gestor;
import com.grupo24.casas.Models.Cliente;

public class GestorSecure implements UserDetails {
    private String password;
    private String nombre;
    private String email;

    private Collection<? extends GrantedAuthority> authorities;


    public GestorSecure(String username, String password, String nombre, String email, Collection<? extends GrantedAuthority> authorities) {
        this.password = password;
        this.nombre = nombre;
        this.email = email;
        this.authorities = authorities;

    }

    public static GestorSecure build(Gestor usuario){

        List<GrantedAuthority> authorities = usuario.getRoles().stream().map(rol ->
                new SimpleGrantedAuthority(rol.getRolNombre().name())
        ).collect(Collectors.toList());
        return new PropietarioSecure(
            usuario.getPassword(),
            usuario.getNombre(),
            usuario.getEmail(),
            authorities
        );
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
