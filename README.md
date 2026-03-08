# Demo patrón CQRS Biblioteca y Despliegue

Leonardo Pérez Ramírez

---

## Descripción

Este proyecto es una demostración académica de la arquitectura CQRS (Command Query Responsibility Segregation) aplicada sobre Clean Architecture usando Spring Boot y una base de datos PostgreSQL gestionada con Liquibase. El objetivo es mostrar de forma didáctica cómo separar completamente el modelo de escritura (commands) del modelo de lectura (queries) en un sistema de gestión de biblioteca.

---

## ¿Qué es CQRS?

CQRS es un patrón arquitectónico que propone separar las operaciones de escritura (Command) de las operaciones de lectura (Query) en un sistema. Esto permite que cada lado evolucione de manera independiente, optimizando la lógica de negocio y la consulta de datos.

---

## ¿Por qué CQRS en este proyecto?

- Permite demostrar la separación de responsabilidades de forma clara.
- Facilita la explicación académica de los flujos de modificación y consulta.
- Mejora la mantenibilidad y escalabilidad del sistema.
- Evidencia cómo la lógica de negocio vive en el dominio y no en los controladores.
- Permite mostrar integración real con una base de datos relacional y migraciones automáticas.

---

## Estructura del Proyecto

```
com.biblioteca.cqrs
├── application
│   ├── command        # DTOs de escritura 
│   ├── query          # DTOs de consulta
│   └── handler        # Orquestadores de comandos y queries
├── domain
│   └── Libro          # Lógica de negocio y entidades del dominio
└── infrastructure
    ├── controller     # Exposición de endpoints REST
    └── repository     # Repositorios JPA (persistencia en PostgreSQL)
```

### Domain

Contiene la lógica de negocio pura. Ejemplo: métodos prestar(), devolver(), estaDisponible() en la entidad Libro.

### Application

Contiene los DTOs de Command y Query, y los handlers que orquestan el flujo de cada operación.

### Infrastructure

Implementa los repositorios JPA, conectados a PostgreSQL mediante Spring Data JPA. La estructura de la base de datos se gestiona automáticamente con Liquibase.

### Controller

Expone los endpoints REST, delegando la lógica a los handlers. No contiene lógica de negocio.

---

## Configuración y ejecución

### Requisitos previos
- Java 17+
- PostgreSQL corriendo en localhost:5432, base de datos llamada `library`, usuario y contraseña `postgres` (puedes cambiar esto en `src/main/resources/application.yml`)

### Primer uso
1. Asegúrate de que la base de datos existe y el usuario tiene permisos.
2. Al iniciar la aplicación, Liquibase creará automáticamente la tabla `libro` si no existe.
3. Ejecuta:
   ```bash
   mvn spring-boot:run
   ```

---

## Flujo de Ejecución

### Registrar libro

1. Controller recibe RegistrarLibroCommand.
2. Handler crea la entidad Libro y la guarda en el repositorio (en la base de datos).
3. Logging: [COMMAND] → [DOMAIN]

### Prestar libro

1. Controller recibe PrestarLibroCommand.
2. Handler busca el libro, llama prestar() y guarda el estado.
3. Logging: [COMMAND] → [DOMAIN]

### Devolver libro

1. Controller recibe DevolverLibroCommand.
2. Handler busca el libro, llama devolver() y guarda el estado.
3. Logging: [COMMAND] → [DOMAIN]

### Consultar libro

1. Controller recibe ObtenerLibroQuery.
2. Handler busca el libro y retorna un DTO de lectura.
3. Logging: [QUERY]

---

## Separación entre Command y Query

- Los Commands modifican el estado del sistema.
- Los Queries solo leen datos, sin modificar el estado.
- Cada handler tiene una responsabilidad única.
- No se mezclan flujos de lectura y escritura.

---

## Comparación CQRS vs CRUD tradicional

| CQRS                        | CRUD tradicional           |
|-----------------------------|---------------------------|
| Separación Command/Query    | Métodos combinados        |
| Lógica de negocio en dominio| Lógica en controller      |
| Handlers orquestan          | Controladores gestionan   |
| Escalabilidad didáctica     | Simplicidad, menos clara  |

---

## Diagrama de Clases (Mermaid)

```mermaid
classDiagram
    %% === HANDLERS ===
    class RegistrarLibroCommandHandler
    class PrestarLibroCommandHandler
    class DevolverLibroCommandHandler
    class ObtenerLibroQueryHandler
    <<handler>> RegistrarLibroCommandHandler
    <<handler>> PrestarLibroCommandHandler
    <<handler>> DevolverLibroCommandHandler
    <<handler>> ObtenerLibroQueryHandler

    %% === COMMANDS ===
    class RegistrarLibroCommand
    class PrestarLibroCommand
    class DevolverLibroCommand
    <<command>> RegistrarLibroCommand
    <<command>> PrestarLibroCommand
    <<command>> DevolverLibroCommand

    %% === QUERIES ===
    class ObtenerLibroQuery
    class LibroResponse
    <<query>> ObtenerLibroQuery
    <<query>> LibroResponse

    %% === DOMAIN & REPOSITORY ===
    class Libro {
        +String id
        +String titulo
        +boolean prestado
        +String usuarioId
        +prestar(usuarioId)
        +devolver()
        +estaDisponible()
    }
    <<domain>> Libro
    class LibroRepository
    <<repository>> LibroRepository

    %% === RELACIONES (de arriba hacia abajo) ===
    %% Command Handlers
    RegistrarLibroCommandHandler --> RegistrarLibroCommand : ejecuta
    RegistrarLibroCommandHandler --> Libro : crea
    RegistrarLibroCommandHandler --> LibroRepository : guarda

    PrestarLibroCommandHandler --> PrestarLibroCommand : ejecuta
    PrestarLibroCommandHandler --> Libro : modifica
    PrestarLibroCommandHandler --> LibroRepository : consulta/guarda

    DevolverLibroCommandHandler --> DevolverLibroCommand : ejecuta
    DevolverLibroCommandHandler --> Libro : modifica
    DevolverLibroCommandHandler --> LibroRepository : consulta/guarda

    %% Query Handler
    ObtenerLibroQueryHandler --> ObtenerLibroQuery : ejecuta
    ObtenerLibroQueryHandler --> LibroRepository : consulta
    ObtenerLibroQueryHandler --> LibroResponse : retorna

    %% === Agrupación visual (comentarios) ===
    %% Handlers arriba, Commands/Queries al centro, Dominio/Repositorio abajo
    %% Flechas solo hacia abajo para evitar cruces
```

---

## Diagrama de Flujo (Mermaid)

```mermaid
flowchart TD
    A[Controller] --> B[CommandHandler]
    B --> C[Domain]
    C --> D[Repository]

    A[Controller] --> E[QueryHandler]
    E --> D[Repository]
```

---

## Ejemplo de logs reales eje

```
[COMMAND] Ejecutando RegistrarLibroCommand: 123 - Clean Architecture
[DOMAIN] Libro marcado como prestado: 123 por usuario 456
[COMMAND] Ejecutando DevolverLibroCommand para libro 123
[DOMAIN] Libro marcado como devuelto: 123
[QUERY] Consultando libro 123
```

---