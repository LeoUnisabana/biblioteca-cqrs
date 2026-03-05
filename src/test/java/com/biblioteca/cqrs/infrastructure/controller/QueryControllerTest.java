package com.biblioteca.cqrs.infrastructure.controller;

import com.biblioteca.cqrs.application.handler.ObtenerLibroQueryHandler;
import com.biblioteca.cqrs.application.query.LibroResponse;
import com.biblioteca.cqrs.application.query.ObtenerLibroQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QueryController.class)
class QueryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObtenerLibroQueryHandler obtenerLibroQueryHandler;

    @Test
    void obtenerLibroDevuelve200YJson() throws Exception {
        LibroResponse response = new LibroResponse("1", "Libro Test", false, null);
        when(obtenerLibroQueryHandler.handle(any(ObtenerLibroQuery.class))).thenReturn(response);
        mockMvc.perform(get("/libros/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.titulo").value("Libro Test"))
                .andExpect(jsonPath("$.prestado").value(false))
                .andExpect(jsonPath("$.usuarioId").doesNotExist());
    }
}
