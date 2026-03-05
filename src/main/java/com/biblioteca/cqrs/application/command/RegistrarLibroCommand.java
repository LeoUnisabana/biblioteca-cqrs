package com.biblioteca.cqrs.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrarLibroCommand {
    private final String id;
    private final String titulo;
}
