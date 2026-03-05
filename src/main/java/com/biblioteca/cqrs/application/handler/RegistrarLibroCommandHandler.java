package com.biblioteca.cqrs.application.handler;

import com.biblioteca.cqrs.domain.Libro;
import com.biblioteca.cqrs.infrastructure.repository.LibroRepository;
import com.biblioteca.cqrs.application.command.RegistrarLibroCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RegistrarLibroCommandHandler {
    private final LibroRepository libroRepository;

    public RegistrarLibroCommandHandler(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Transactional
    public void handle(RegistrarLibroCommand command) {
        log.info("[COMMAND] Ejecutando RegistrarLibroCommand: {} - {}", command.getId(), command.getTitulo());
        Libro libro = new Libro(command.getId(), command.getTitulo());
        libroRepository.guardar(libro);
    }
}
