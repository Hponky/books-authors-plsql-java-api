package com.humberto.books.books_api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LibroTest {

    @Test
    void testLibroCreation() {
        Libro libro = new Libro();
        assertNotNull(libro);
    }

    @Test
    void testLibroProperties() {
        Long idLibro = 1L;
        String titulo = "Cien años de soledad";
        String isbn = "978-0307474278";
        Integer anioPublicacion = 1967;
        LocalDateTime fechaCreacion = LocalDateTime.now();
        LocalDateTime fechaModificacion = LocalDateTime.now();

        Autor autor = new Autor(1L, "Gabriel", "Garcia Marquez", LocalDate.of(1927, 3, 6), "Colombiano", LocalDateTime.now(), LocalDateTime.now());

        Libro libro = new Libro(idLibro, titulo, isbn, anioPublicacion, autor, fechaCreacion, fechaModificacion);

        assertEquals(idLibro, libro.getIdLibro());
        assertEquals(titulo, libro.getTitulo());
        assertEquals(isbn, libro.getIsbn());
        assertEquals(anioPublicacion, libro.getAnioPublicacion());
        assertEquals(autor, libro.getAutor());
        assertEquals(fechaCreacion, libro.getFechaCreacion());
        assertEquals(fechaModificacion, libro.getFechaModificacion());
    }
    @Test
    void testEqualsAndHashCode() {
        Autor autor1 = new Autor(1L, "Nombre", "Apellido", LocalDate.now(), "Nacionalidad", LocalDateTime.now(), LocalDateTime.now());
        Libro libro1 = new Libro(1L, "Titulo", "ISBN1", 2000, autor1, LocalDateTime.now(), LocalDateTime.now());
        Libro libro2 = new Libro(1L, "Otro Titulo", "ISBN2", 2001, autor1, LocalDateTime.now(), LocalDateTime.now());
        Libro libro3 = new Libro(2L, "Titulo", "ISBN1", 2000, autor1, LocalDateTime.now(), LocalDateTime.now());

        assertEquals(libro1, libro2);
        assertEquals(libro1.hashCode(), libro2.hashCode());
        assertNotEquals(libro1, libro3);
        assertNotEquals(libro1.hashCode(), libro3.hashCode());
    }

    @Test
    void testPrePersist() {
        Libro libro = new Libro();
        libro.onCreate(); // Simula el callback @PrePersist
        assertNotNull(libro.getFechaCreacion());
        assertNull(libro.getFechaModificacion()); // No debería estar seteado en @PrePersist
    }

    @Test
    void testPreUpdate() {
        Libro libro = new Libro();
        libro.onCreate(); // Simula el callback @PrePersist
        LocalDateTime initialCreationTime = libro.getFechaCreacion();

        // Simula una modificación
        libro.setTitulo("Nuevo Titulo");
        libro.onUpdate(); // Simula el callback @PreUpdate

        assertNotNull(libro.getFechaModificacion());
        // Verificamos que la fecha de modificación sea posterior o igual a la de creación inicial
        // Esto es más robusto que isAfter() debido a la precisión de LocalDateTime.now()
        assertTrue(libro.getFechaModificacion().compareTo(initialCreationTime) >= 0);
    }
}