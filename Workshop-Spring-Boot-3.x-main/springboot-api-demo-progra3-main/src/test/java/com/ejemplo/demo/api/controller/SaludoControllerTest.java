package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.domain.service.SaludoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SaludoController.class)
class SaludoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 🔥 Mock del service (OBLIGATORIO)
    @MockBean
    private SaludoService saludoService;

    @Test
    @DisplayName("Debe responder health del workshop")
    void debeResponderHealthDelWorkshop() throws Exception {
        mockMvc.perform(get("/api/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ok"));
    }

    @Test
    @DisplayName("GET /saludos debe responder mensaje correcto")
    void debeResponderSaludoCorrecto() throws Exception {

        when(saludoService.crearSaludo("Ana"))
                .thenReturn(new com.ejemplo.demo.api.dto.SaludoResponse(
                        "Hola, Estudiante Ana. Bienvenido a Spring Boot 3!",
                        Instant.now()
                ));

        mockMvc.perform(get("/api/v1/saludos")
                        .param("nombre", "Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje")
                        .value("Hola, Estudiante Ana. Bienvenido a Spring Boot 3!"));
    }

    @Test
    @DisplayName("POST /saludos invalido debe retornar 400")
    void debeRetornarErrorValidacion() throws Exception {

        mockMvc.perform(post("/api/v1/saludos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }
}