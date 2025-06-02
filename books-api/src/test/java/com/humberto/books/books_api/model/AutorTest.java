package com.humberto.books.books_api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AutorTest {

    @Test
    void testAutorCreation() {
        Autor autor = new Autor();
        assertNotNull(autor);
    }

    @Test
    void testAutorProperties() {
        Long id = 1L;
        String nombre = "Gabriel";
        String apellido = "Garcia Marquez";
        LocalDate fechaNacimiento = LocalDate.of(1927, 3, 6);
        String nacionalidad = "Colombiano";
        LocalDateTime fechaCreacion = LocalDateTime.now();
        LocalDateTime fechaModificacion = LocalDateTime.now();

        Autor autor = new Autor(id, nombre, apellido, fechaNacimiento, nacionalidad, fechaCreacion, fechaModificacion);

        assertEquals(id, autor.getIdAutor());
        assertEquals(nombre, autor.getNombre());
        assertEquals(apellido, autor.getApellido());
        assertEquals(fechaNacimiento, autor.getFechaNacimiento());
        assertEquals(nacionalidad, autor.getNacionalidad());
        assertEquals(fechaCreacion, autor.getFechaCreacion());
        assertEquals(fechaModificacion, autor.getFechaModificacion());
    }
    @Test
    void testEqualsAndHashCode() {
        Autor autor1 = new Autor(1L, "Nombre", "Apellido", LocalDate.now(), "Nacionalidad", LocalDateTime.now(), LocalDateTime.now());
        Autor autor2 = new Autor(1L, "Otro Nombre", "Otro Apellido", LocalDate.now(), "Otra Nacionalidad", LocalDateTime.now(), LocalDateTime.now());
        Autor autor3 = new Autor(2L, "Nombre", "Apellido", LocalDate.now(), "Nacionalidad", LocalDateTime.now(), LocalDateTime.now());

        assertEquals(autor1, autor2);
        assertEquals(autor1.hashCode(), autor2.hashCode());
        assertNotEquals(autor1, autor3);
        assertNotEquals(autor1.hashCode(), autor3.hashCode());
    }

    @Test
    void testPrePersist() {
        Autor autor = new Autor();
        autor.onCreate(); // Simula el callback @PrePersist
        assertNotNull(autor.getFechaCreacion());
        assertNull(autor.getFechaModificacion()); // No debería estar seteado en @PrePersist
    }

    @Test
    void testPreUpdate() {
        Autor autor = new Autor();
        autor.onCreate(); // Simula el callback @PrePersist
        LocalDateTime initialCreationTime = autor.getFechaCreacion();

        // Simula una modificación
        autor.setNombre("Nuevo Nombre");
        autor.onUpdate(); // Simula el callback @PreUpdate

        assertNotNull(autor.getFechaModificacion());
        // Verificamos que la fecha de modificación sea posterior o igual a la de creación inicial
        // Esto es más robusto que isAfter() debido a la precisión de LocalDateTime.now()
        assertTrue(autor.getFechaModificacion().compareTo(initialCreationTime) >= 0);
    }
}