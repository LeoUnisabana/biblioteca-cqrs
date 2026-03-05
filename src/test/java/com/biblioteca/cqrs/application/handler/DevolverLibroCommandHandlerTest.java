package com.biblioteca.cqrs.application.handler;

import com.biblioteca.cqrs.application.command.DevolverLibroCommand;
import com.biblioteca.cqrs.domain.Libro;
import com.biblioteca.cqrs.infrastructure.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DevolverLibroCommandHandlerTest {
    private LibroRepository libroRepository;
    private DevolverLibroCommandHandler handler;

    @BeforeEach
    void setUp() {
        libroRepository = mock(LibroRepository.class);
        handler = new DevolverLibroCommandHandler(libroRepository);
    }

    @Test
    void handleDevuelveLibroCorrectamente() {
        Libro libro = new Libro("30", "Arquitectura");
        libro.prestar("userZ");
        when(libroRepository.buscarPorId("30")).thenReturn(libro);
        DevolverLibroCommand command = new DevolverLibroCommand("30");
        handler.handle(command);
        assertTrue(libro.estaDisponible());
        assertNull(libro.getUsuarioId());
        verify(libroRepository).guardar(libro);
    }

    @Test
    void handleLanzaExcepcionSiNoPrestado() {
        Libro libro = new Libro("31", "Cloud");
        when(libroRepository.buscarPorId("31")).thenReturn(libro);
        DevolverLibroCommand command = new DevolverLibroCommand("31");
        assertThrows(IllegalStateException.class, () -> handler.handle(command));
    }
}
