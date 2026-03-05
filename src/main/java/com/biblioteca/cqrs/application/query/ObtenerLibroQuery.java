package com.biblioteca.cqrs.application.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ObtenerLibroQuery {
    private final String libroId;
}
