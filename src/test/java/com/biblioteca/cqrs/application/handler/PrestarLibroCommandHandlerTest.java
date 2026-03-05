package com.biblioteca.cqrs.application.handler;

import com.biblioteca.cqrs.application.command.PrestarLibroCommand;
import com.biblioteca.cqrs.domain.Libro;
import com.biblioteca.cqrs.infrastructure.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrestarLibroCommandHandlerTest {
    private LibroRepository libroRepository;
    private PrestarLibroCommandHandler handler;

    @BeforeEach
    void setUp() {
        libroRepository = mock(LibroRepository.class);
        handler = new PrestarLibroCommandHandler(libroRepository);
    }

    @Test
    void handlePrestaLibroCorrectamente() {
        Libro libro = new Libro("20", "Spring Boot");
        when(libroRepository.buscarPorId("20")).thenReturn(libro);
        PrestarLibroCommand command = new PrestarLibroCommand("20", "userABC");
        handler.handle(command);
        assertFalse(libro.estaDisponible());
        assertEquals("userABC", libro.getUsuarioId());
        verify(libroRepository).guardar(libro);
    }

    @Test
    void handleLanzaExcepcionSiNoDisponible() {
        Libro libro = new Libro("21", "Java");
        libro.prestar("otro");
        when(libroRepository.buscarPorId("21")).thenReturn(libro);
        PrestarLibroCommand command = new PrestarLibroCommand("21", "nuevo");
        assertThrows(IllegalStateException.class, () -> handler.handle(command));
    }
}
