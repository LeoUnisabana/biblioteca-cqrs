package com.biblioteca.cqrs.application.handler;

import com.biblioteca.cqrs.domain.Libro;
import com.biblioteca.cqrs.infrastructure.repository.LibroRepository;
import com.biblioteca.cqrs.application.command.PrestarLibroCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrestarLibroCommandHandler {
    private final LibroRepository libroRepository;

    @Transactional
    public void handle(PrestarLibroCommand command) {
        log.info("[COMMAND] Ejecutando PrestarLibroCommand para libro {}", command.getLibroId());
        Libro libro = libroRepository.buscarPorId(command.getLibroId());
        libro.prestar(command.getUsuarioId());
        libroRepository.guardar(libro);
    }
}
