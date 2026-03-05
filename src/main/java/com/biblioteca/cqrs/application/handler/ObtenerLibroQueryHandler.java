package com.biblioteca.cqrs.application.handler;

import com.biblioteca.cqrs.domain.Libro;
import com.biblioteca.cqrs.infrastructure.repository.LibroRepository;
import com.biblioteca.cqrs.application.query.ObtenerLibroQuery;
import com.biblioteca.cqrs.application.query.LibroResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ObtenerLibroQueryHandler {
    private final LibroRepository libroRepository;

    public LibroResponse handle(ObtenerLibroQuery query) {
        log.info("[QUERY] Consultando libro {}", query.getLibroId());
        Libro libro = libroRepository.buscarPorId(query.getLibroId());
        return new LibroResponse(
                libro.getId(),
                libro.getTitulo(),
                libro.isPrestado(),
                libro.getUsuarioId()
        );
    }
}
