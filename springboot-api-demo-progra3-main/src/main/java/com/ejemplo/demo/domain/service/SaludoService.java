package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.SaludoResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SaludoService {

    public SaludoResponse crearSaludo(String nombre) {
        String nombreNormalizado = normalizarNombre(nombre);
        String mensaje = "Hola, %s. Bienvenido a Spring Boot 3!"
                .formatted(nombreNormalizado);
        return new SaludoResponse(mensaje, Instant.now());
    }


    String normalizarNombre(String nombre) {

        // Validar nulo o vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Mundo";
        }

        nombre = nombre.trim();

        // Validar que no tenga números
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            throw new IllegalArgumentException("El nombre no debe contener números");
        }

        // Primera letra mayúscula + resto minúscula
        nombre = nombre.substring(0, 1).toUpperCase() +
                 nombre.substring(1).toLowerCase();

        // Agregar prefijo
        return "Estudiante " + nombre;
    }
}