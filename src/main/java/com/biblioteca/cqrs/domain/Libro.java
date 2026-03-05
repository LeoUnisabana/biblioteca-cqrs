package com.biblioteca.cqrs.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@Table(name = "libro")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Libro {
    
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;
    
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;
    
    @Column(name = "prestado", nullable = false)
    private boolean prestado;
    
    @Column(name = "usuario_id", length = 100)
    private String usuarioId;

    public Libro(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.prestado = false;
        this.usuarioId = null;
    }

    public void prestar(String usuarioId) {
        if (!estaDisponible()) {
            log.warn("[DOMAIN] El libro ya está prestado: {}", id);
            throw new IllegalStateException("No se puede prestar un libro que ya está prestado.");
        }
        this.prestado = true;
        this.usuarioId = usuarioId;
        log.info("[DOMAIN] Libro marcado como prestado: {} por usuario {}", id, usuarioId);
    }

    public void devolver() {
        if (estaDisponible()) {
            log.warn("[DOMAIN] El libro no está prestado: {}", id);
            throw new IllegalStateException("El libro no está prestado.");
        }
        this.prestado = false;
        this.usuarioId = null;
        log.info("[DOMAIN] Libro marcado como devuelto: {}", id);
    }

    public boolean estaDisponible() {
        return !prestado;
    }
}
