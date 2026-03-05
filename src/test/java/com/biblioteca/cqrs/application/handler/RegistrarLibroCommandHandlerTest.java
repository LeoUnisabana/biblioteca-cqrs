package com.biblioteca.cqrs.application.handler;

import com.biblioteca.cqrs.application.command.RegistrarLibroCommand;
import com.biblioteca.cqrs.domain.Libro;
import com.biblioteca.cqrs.infrastructure.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrarLibroCommandHandlerTest {
    private LibroRepository libroRepository;
    private RegistrarLibroCommandHandler handler;

    @BeforeEach
    void setUp() {
        libroRepository = mock(LibroRepository.class);
        handler = new RegistrarLibroCommandHandler(libroRepository);
    }

    @Test
    void handleGuardaLibroCorrectamente() {
        RegistrarLibroCommand command = new RegistrarLibroCommand("10", "Microservicios");
        handler.handle(command);
        ArgumentCaptor<Libro> captor = ArgumentCaptor.forClass(Libro.class);
        verify(libroRepository).guardar(captor.capture());
        Libro libro = captor.getValue();
        assertEquals("10", libro.getId());
        assertEquals("Microservicios", libro.getTitulo());
        assertTrue(libro.estaDisponible());
    }
}
