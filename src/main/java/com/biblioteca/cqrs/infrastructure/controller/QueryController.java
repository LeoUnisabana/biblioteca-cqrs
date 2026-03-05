package com.biblioteca.cqrs.infrastructure.controller;

import com.biblioteca.cqrs.application.query.ObtenerLibroQuery;
import com.biblioteca.cqrs.application.handler.ObtenerLibroQueryHandler;
import com.biblioteca.cqrs.application.query.LibroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
public class QueryController {
    private final ObtenerLibroQueryHandler obtenerLibroQueryHandler;

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> obtenerLibro(@PathVariable String id) {
        ObtenerLibroQuery query = new ObtenerLibroQuery(id);
        LibroResponse response = obtenerLibroQueryHandler.handle(query);
        return ResponseEntity.ok(response);
    }
}
