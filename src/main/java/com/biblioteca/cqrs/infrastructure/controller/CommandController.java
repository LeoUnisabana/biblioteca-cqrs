package com.biblioteca.cqrs.infrastructure.controller;

import com.biblioteca.cqrs.application.command.PrestarLibroCommand;
import com.biblioteca.cqrs.application.handler.PrestarLibroCommandHandler;
import com.biblioteca.cqrs.application.command.RegistrarLibroCommand;
import com.biblioteca.cqrs.application.handler.RegistrarLibroCommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libros")
public class CommandController {
    private final RegistrarLibroCommandHandler registrarLibroCommandHandler;
    private final PrestarLibroCommandHandler prestarLibroCommandHandler;
    private final com.biblioteca.cqrs.application.handler.DevolverLibroCommandHandler devolverLibroCommandHandler;

    public CommandController(
        RegistrarLibroCommandHandler registrarLibroCommandHandler,
        PrestarLibroCommandHandler prestarLibroCommandHandler,
        com.biblioteca.cqrs.application.handler.DevolverLibroCommandHandler devolverLibroCommandHandler
    ) {
        this.registrarLibroCommandHandler = registrarLibroCommandHandler;
        this.prestarLibroCommandHandler = prestarLibroCommandHandler;
        this.devolverLibroCommandHandler = devolverLibroCommandHandler;
    }

    @PostMapping
    public ResponseEntity<Void> registrarLibro(@RequestBody RegistrarLibroCommand command) {
        registrarLibroCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/prestar")
    public ResponseEntity<Void> prestarLibro(@RequestBody PrestarLibroCommand command) {
        prestarLibroCommandHandler.handle(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/devolver")
    public ResponseEntity<Void> devolverLibro(@RequestBody com.biblioteca.cqrs.application.command.DevolverLibroCommand command) {
        devolverLibroCommandHandler.handle(command);
        return ResponseEntity.ok().build();
    }
}
