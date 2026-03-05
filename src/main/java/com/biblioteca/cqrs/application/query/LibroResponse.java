package com.biblioteca.cqrs.application.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LibroResponse {
    private String id;
    private String titulo;
    private boolean prestado;
    private String usuarioId;
}
