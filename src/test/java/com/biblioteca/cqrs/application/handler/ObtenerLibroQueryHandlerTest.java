package com.biblioteca.cqrs.application.handler;

import com.biblioteca.cqrs.application.query.LibroResponse;
import com.biblioteca.cqrs.application.query.ObtenerLibroQuery;
import com.biblioteca.cqrs.domain.Libro;
import com.biblioteca.cqrs.infrastructure.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObtenerLibroQueryHandlerTest {
    private LibroRepository libroRepository;
    private ObtenerLibroQueryHandler handler;

    @BeforeEach
    void setUp() {
        libroRepository = mock(LibroRepository.class);
        handler = new ObtenerLibroQueryHandler(libroRepository);
    }

    @Test
    void handleRetornaLibroResponseCorrecto() {
        Libro libro = new Libro("40", "DDD");
        libro.prestar("userQ");
        when(libroRepository.buscarPorId("40")).thenReturn(libro);
        ObtenerLibroQuery query = new ObtenerLibroQuery("40");
        LibroResponse response = handler.handle(query);
        assertEquals("40", response.getId());
        assertEquals("DDD", response.getTitulo());
        assertTrue(response.isPrestado());
        assertEquals("userQ", response.getUsuarioId());
    }
}
