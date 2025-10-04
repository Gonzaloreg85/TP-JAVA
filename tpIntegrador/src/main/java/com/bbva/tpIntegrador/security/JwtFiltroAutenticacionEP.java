package com.bbva.tpIntegrador.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;

@Component
public class JwtFiltroAutenticacionEP extends OncePerRequestFilter {
    // Inyecta la clave secreta desde application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtiene el header Authorization de la petición
        String authHeader = request.getHeader("Authorization");
        // Verifica que el header no sea nulo y que comience con "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extrae el token JWT del header
            String token = authHeader.substring(7);
            try {
                // Construye la clave secreta a partir del valor inyectado
                Key clave = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
                // Parsea y valida el token JWT usando la clave secreta
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(clave)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                // Obtiene el usuario (subject) del token
                String username = claims.getSubject();
                // Si el usuario existe, crea el objeto de autenticación y lo establece en el contexto de seguridad
                if (username != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username, null, Collections.emptyList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Si el token es inválido o expiró, no se autentica al usuario
            }
        }
        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
