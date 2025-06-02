package com.humberto.books.books_api.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LibroDTOTest {

    @Test
    void testLibroDTOConstructorAndGetters() {
        Long idLibro = 1L;
        String titulo = "Cien años de soledad";
        String isbn = "978-0307474278";
        Integer anioPublicacion = 1967;
        Long idAutor = 101L;
        LocalDateTime fechaCreacion = LocalDateTime.now().minusDays(5);
        LocalDateTime fechaModificacion = LocalDateTime.now().minusDays(1);

        LibroDTO libroDTO = new LibroDTO(idLibro, titulo, isbn, anioPublicacion, idAutor, fechaCreacion, fechaModificacion);

        assertEquals(idLibro, libroDTO.getIdLibro());
        assertEquals(titulo, libroDTO.getTitulo());
        assertEquals(isbn, libroDTO.getIsbn());
        assertEquals(anioPublicacion, libroDTO.getAnioPublicacion());
        assertEquals(idAutor, libroDTO.getIdAutor());
        assertEquals(fechaCreacion, libroDTO.getFechaCreacion());
        assertEquals(fechaModificacion, libroDTO.getFechaModificacion());
    }

    @Test
    void testLibroDTOSetters() {
        LibroDTO libroDTO = new LibroDTO(null, null, null, null, null, null, null);

        Long idLibro = 2L;
        String titulo = "El amor en los tiempos del cólera";
        String isbn = "978-0307387424";
        Integer anioPublicacion = 1985;
        Long idAutor = 101L;
        LocalDateTime fechaCreacion = LocalDateTime.now().minusDays(10);
        LocalDateTime fechaModificacion = LocalDateTime.now().minusDays(2);

        libroDTO.setIdLibro(idLibro);
        libroDTO.setTitulo(titulo);
        libroDTO.setIsbn(isbn);
        libroDTO.setAnioPublicacion(anioPublicacion);
        libroDTO.setIdAutor(idAutor);
        libroDTO.setFechaCreacion(fechaCreacion);
        libroDTO.setFechaModificacion(fechaModificacion);

        assertEquals(idLibro, libroDTO.getIdLibro());
        assertEquals(titulo, libroDTO.getTitulo());
        assertEquals(isbn, libroDTO.getIsbn());
        assertEquals(anioPublicacion, libroDTO.getAnioPublicacion());
        assertEquals(idAutor, libroDTO.getIdAutor());
        assertEquals(fechaCreacion, libroDTO.getFechaCreacion());
        assertEquals(fechaModificacion, libroDTO.getFechaModificacion());
    }

    @Test
    void testLibroDTOEquality() {
        LocalDateTime fechaCreacion1 = LocalDateTime.of(2020, 1, 1, 10, 0);
        LocalDateTime fechaModificacion1 = LocalDateTime.of(2020, 1, 2, 11, 0);

        LibroDTO libroDTO1 = new LibroDTO(1L, "Cien años de soledad", "978-0307474278", 1967, 101L, fechaCreacion1, fechaModificacion1);
        LibroDTO libroDTO2 = new LibroDTO(1L, "Cien años de soledad", "978-0307474278", 1967, 101L, fechaCreacion1, fechaModificacion1);
        LibroDTO libroDTO3 = new LibroDTO(2L, "El amor en los tiempos del cólera", "978-0307387424", 1985, 101L, LocalDateTime.of(2021, 1, 1, 10, 0), LocalDateTime.of(2021, 1, 2, 11, 0));

        assertEquals(libroDTO1, libroDTO2);
        assertNotEquals(libroDTO1, libroDTO3);
    }

    @Test
    void testLibroDTOHashCode() {
        LocalDateTime fechaCreacion1 = LocalDateTime.of(2020, 1, 1, 10, 0);
        LocalDateTime fechaModificacion1 = LocalDateTime.of(2020, 1, 2, 11, 0);

        LibroDTO libroDTO1 = new LibroDTO(1L, "Cien años de soledad", "978-0307474278", 1967, 101L, fechaCreacion1, fechaModificacion1);
        LibroDTO libroDTO2 = new LibroDTO(1L, "Cien años de soledad", "978-0307474278", 1967, 101L, fechaCreacion1, fechaModificacion1);

        assertEquals(libroDTO1.hashCode(), libroDTO2.hashCode());
    }
}