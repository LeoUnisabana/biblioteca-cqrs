package com.biblioteca.cqrs.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrestarLibroCommand {
    private final String libroId;
    private final String usuarioId;
}
