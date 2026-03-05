package com.biblioteca.cqrs.infrastructure.repository;

import com.biblioteca.cqrs.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {
    
    default void guardar(Libro libro) {
        save(libro);
    }
    
    default Libro buscarPorId(String id) {
        return findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe un libro con id: " + id));
    }
}
