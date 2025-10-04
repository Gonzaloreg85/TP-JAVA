package com.bbva.tpIntegrador.controller;

import com.bbva.tpIntegrador.model.User;
import com.bbva.tpIntegrador.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SeguridadController {
    // Inyecta la clave secreta desde application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;
    // Inyecta el tiempo de expiración desde application.properties
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        User user = userService.validarUser(username, password);
        if (user == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            return ResponseEntity.status(401).body(error);
        }
        // Construye la clave secreta a partir del valor inyectado
        Key clave = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(clave, SignatureAlgorithm.HS256)
                .compact();
        Map<String, String> response = new HashMap<>();
        response.put("token: ", token);
        return ResponseEntity.ok(response);
    }
}
