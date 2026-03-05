package com.biblioteca.cqrs.infrastructure.repository;

import com.biblioteca.cqrs.domain.Libro;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.liquibase.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create"
})
class LibroRepositoryTest {

    @Autowired
    private LibroRepository libroRepository;

    @Test
    void guardarYBuscarPorId() {
        Libro libro = new Libro("100", "Spring Data");
        libroRepository.guardar(libro);
        Libro encontrado = libroRepository.buscarPorId("100");
        assertEquals("100", encontrado.getId());
        assertEquals("Spring Data", encontrado.getTitulo());
        assertTrue(encontrado.estaDisponible());
    }

    @Test
    void buscarPorIdNoExistenteLanzaExcepcion() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> libroRepository.buscarPorId("999"));
    }
}
