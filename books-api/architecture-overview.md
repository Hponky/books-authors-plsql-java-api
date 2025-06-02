# Visión General de la Arquitectura de Carpetas

Este documento describe la arquitectura de carpetas propuesta para la aplicación Spring Boot de gestión de libros y autores, justificando la función de cada capa.

## Estructura de Carpetas Propuesta

```
src/main/java/com/humberto/books/books_api/
├── controller/
│   ├── AutorController.java
│   └── LibroController.java
├── service/
│   ├── AutorService.java
│   └── LibroService.java
├── repository/
│   ├── AutorRepository.java
│   └── LibroRepository.java
├── model/
│   ├── Autor.java
│   └── Libro.java
└── dto/
    ├── AutorDTO.java
    └── LibroDTO.java
```

## Justificación de Cada Capa

*   **`controller/`**: Contiene las clases que actúan como puntos de entrada de la API REST. Son responsables de manejar las solicitudes HTTP entrantes, invocar la lógica de negocio apropiada en la capa de servicio y devolver las respuestas HTTP. Esto sigue el patrón MVC (Model-View-Controller) y promueve la separación de preocupaciones, haciendo que la lógica de la interfaz de usuario sea independiente de la lógica de negocio.

*   **`service/`**: Contiene la lógica de negocio principal de la aplicación. Las clases de servicio orquestan las operaciones, aplican las reglas de negocio y coordinan las interacciones con la capa de persistencia. Esta capa abstrae la complejidad del negocio de los controladores y los repositorios, facilitando la reutilización de la lógica y la prueba unitaria.

*   **`repository/`**: Contiene las interfaces y clases responsables de la interacción con la base de datos. Utilizan Spring Data JPA para definir métodos de acceso a datos para las entidades `Autor` y `Libro`. Esta capa aísla la lógica de persistencia del resto de la aplicación, permitiendo cambios en la tecnología de la base de datos sin afectar las capas superiores.

*   **`model/`**: Contiene las clases de entidad que representan las tablas de la base de datos (`AUTORES` y `LIBROS`). Estas clases son mapeadas a las tablas de la base de datos utilizando anotaciones JPA. Mantener las entidades en una capa separada ayuda a definir claramente el modelo de dominio de la aplicación.

*   **`dto/`**: Contiene las clases de Data Transfer Object (DTO). Estas clases se utilizan para transferir datos entre las diferentes capas de la aplicación (por ejemplo, del controlador al servicio, o del servicio al controlador para las respuestas de la API). Los DTOs permiten controlar qué datos se exponen a la API externa y qué datos se utilizan internamente, desacoplando el modelo de dominio de la representación de la API.