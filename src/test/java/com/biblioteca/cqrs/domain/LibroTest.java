package com.biblioteca.cqrs.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    @Test
    void crearLibroDebeEstarDisponible() {
        Libro libro = new Libro("1", "Clean Architecture");
        assertEquals("1", libro.getId());
        assertEquals("Clean Architecture", libro.getTitulo());
        assertTrue(libro.estaDisponible());
        assertNull(libro.getUsuarioId());
    }

    @Test
    void prestarLibroCambiaEstadoYUsuario() {
        Libro libro = new Libro("2", "DDD");
        libro.prestar("user123");
        assertFalse(libro.estaDisponible());
        assertEquals("user123", libro.getUsuarioId());
    }

    @Test
    void noPermitePrestarLibroYaPrestado() {
        Libro libro = new Libro("3", "TDD");
        libro.prestar("user1");
        Exception ex = assertThrows(IllegalStateException.class, () -> libro.prestar("user2"));
        assertEquals("No se puede prestar un libro que ya está prestado.", ex.getMessage());
    }

    @Test
    void devolverLibroCambiaEstado() {
        Libro libro = new Libro("4", "Refactoring");
        libro.prestar("userX");
        libro.devolver();
        assertTrue(libro.estaDisponible());
        assertNull(libro.getUsuarioId());
    }

    @Test
    void noPermiteDevolverLibroNoPrestado() {
        Libro libro = new Libro("5", "Patterns");
        Exception ex = assertThrows(IllegalStateException.class, libro::devolver);
        assertEquals("El libro no está prestado.", ex.getMessage());
    }
}
