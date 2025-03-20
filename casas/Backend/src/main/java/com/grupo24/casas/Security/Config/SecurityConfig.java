package com.grupo24.casas.Security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivamos CSRF (para facilitar pruebas)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/").permitAll() // Rutas públicas sin autenticación
                .requestMatchers("/admin/").hasRole("ADMIN") // Solo ADMIN accede
                .anyRequest().authenticated() // Todo lo demás requiere autenticación
            )
            .formLogin(login -> login
                .loginPage("/login") // Página de inicio de sesión personalizada
                .defaultSuccessUrl("/home", true) // Redirección tras login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }

    // Bean para codificar contraseñas con BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}