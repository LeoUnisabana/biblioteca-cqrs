package com.biblioteca.cqrs.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DevolverLibroCommand {
    private final String libroId;
}
