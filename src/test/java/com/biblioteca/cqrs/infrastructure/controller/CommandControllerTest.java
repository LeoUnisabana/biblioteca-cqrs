package com.biblioteca.cqrs.infrastructure.controller;

import com.biblioteca.cqrs.application.command.DevolverLibroCommand;
import com.biblioteca.cqrs.application.command.PrestarLibroCommand;
import com.biblioteca.cqrs.application.command.RegistrarLibroCommand;
import com.biblioteca.cqrs.application.handler.DevolverLibroCommandHandler;
import com.biblioteca.cqrs.application.handler.PrestarLibroCommandHandler;
import com.biblioteca.cqrs.application.handler.RegistrarLibroCommandHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommandController.class)
class CommandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrarLibroCommandHandler registrarLibroCommandHandler;
    @MockBean
    private PrestarLibroCommandHandler prestarLibroCommandHandler;
    @MockBean
    private DevolverLibroCommandHandler devolverLibroCommandHandler;

    @Test
    void registrarLibroDevuelve201() throws Exception {
        doNothing().when(registrarLibroCommandHandler).handle(new RegistrarLibroCommand("1", "Libro Test"));
        mockMvc.perform(post("/libros")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"titulo\":\"Libro Test\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void prestarLibroDevuelve200() throws Exception {
        doNothing().when(prestarLibroCommandHandler).handle(new PrestarLibroCommand("1", "user1"));
        mockMvc.perform(post("/libros/prestar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"libroId\":\"1\",\"usuarioId\":\"user1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void devolverLibroDevuelve200() throws Exception {
        doNothing().when(devolverLibroCommandHandler).handle(new DevolverLibroCommand("1"));
        mockMvc.perform(post("/libros/devolver")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"libroId\":\"1\"}"))
                .andExpect(status().isOk());
    }
}
