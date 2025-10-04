package com.bbva.tpIntegrador.service;

import com.bbva.tpIntegrador.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    // Inyecta los usuarios pre-registrados desde application.properties
    @Value("${usuarios.pre}")
    private String usuariosPre;

    private List<User> usuarios;

    // Inicializa la lista de usuarios a partir de la propiedad usuarios.pre
    private void cargarUsuarios() {
        if (usuarios == null) {
            usuarios = new ArrayList<>();
            if (usuariosPre != null && !usuariosPre.isEmpty()) {
                String[] pares = usuariosPre.split(",");
                for (String par : pares) {
                    String[] datos = par.split(":");
                    if (datos.length == 2) {
                        usuarios.add(new User(datos[0], datos[1]));
                    }
                }
            }
        }
    }

    public User validarUser(String username, String password) {
        cargarUsuarios();
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
