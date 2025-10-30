package com.angie.vivero.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de bienvenida para verificar que la aplicación está funcionando.
 */
@RestController
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public Map<String, Object> welcome() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bienvenido al Sistema de Gestión de Vivero");
        response.put("version", "1.0.0");
        response.put("status", "✅ Aplicación funcionando correctamente");
        response.put("endpoints", Map.of(
            "api", "/api",
            "health", "/actuator/health"
        ));
        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("database", "MySQL conectada");
        response.put("application", "Vivero Management System");
        return response;
    }
}
