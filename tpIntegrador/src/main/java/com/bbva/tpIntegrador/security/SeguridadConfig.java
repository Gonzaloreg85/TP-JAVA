package com.bbva.tpIntegrador.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SeguridadConfig {
    @Autowired // Inyecta el filtro de autenticación JWT
    private JwtFiltroAutenticacionEP jwtAuthenticationFilter;

    @Bean // Expone el SecurityFilterChain como un bean de Spring
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilita CSRF porque se usa JWT (stateless)
            .csrf(csrf -> csrf.disable())
            // Configura la política de sesión como STATELESS (sin sesiones)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Configura las reglas de autorización
            .authorizeHttpRequests(auth -> auth
                // Permite el acceso sin autenticación al endpoint de login
                .requestMatchers("/api/login").permitAll()
                // Requiere autenticación para cualquier otro endpoint
                .anyRequest().authenticated()
            )
            // Agrega el filtro JWT antes del filtro de autenticación por usuario/contraseña
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // Construye y retorna la cadena de filtros de seguridad
        return http.build();
    }
}
