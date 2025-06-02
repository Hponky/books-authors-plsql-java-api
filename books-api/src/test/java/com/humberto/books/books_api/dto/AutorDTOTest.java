package com.humberto.books.books_api.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AutorDTOTest {

    @Test
    void testAutorDTOConstructorAndGetters() {
        Long idAutor = 1L;
        String nombre = "Gabriel";
        String apellido = "García Márquez";
        LocalDate fechaNacimiento = LocalDate.of(1927, 3, 6);
        String nacionalidad = "Colombiana";
        LocalDateTime fechaCreacion = LocalDateTime.now().minusDays(5);
        LocalDateTime fechaModificacion = LocalDateTime.now().minusDays(1);

        AutorDTO autorDTO = new AutorDTO(idAutor, nombre, apellido, fechaNacimiento, nacionalidad, fechaCreacion, fechaModificacion);

        assertEquals(idAutor, autorDTO.getIdAutor());
        assertEquals(nombre, autorDTO.getNombre());
        assertEquals(apellido, autorDTO.getApellido());
        assertEquals(fechaNacimiento, autorDTO.getFechaNacimiento());
        assertEquals(nacionalidad, autorDTO.getNacionalidad());
        assertEquals(fechaCreacion, autorDTO.getFechaCreacion());
        assertEquals(fechaModificacion, autorDTO.getFechaModificacion());
    }

    @Test
    void testAutorDTOSetters() {
        AutorDTO autorDTO = new AutorDTO(null, null, null, null, null, null, null);

        Long idAutor = 2L;
        String nombre = "Isabel";
        String apellido = "Allende";
        LocalDate fechaNacimiento = LocalDate.of(1942, 8, 2);
        String nacionalidad = "Chilena";
        LocalDateTime fechaCreacion = LocalDateTime.now().minusDays(10);
        LocalDateTime fechaModificacion = LocalDateTime.now().minusDays(2);

        autorDTO.setIdAutor(idAutor);
        autorDTO.setNombre(nombre);
        autorDTO.setApellido(apellido);
        autorDTO.setFechaNacimiento(fechaNacimiento);
        autorDTO.setNacionalidad(nacionalidad);
        autorDTO.setFechaCreacion(fechaCreacion);
        autorDTO.setFechaModificacion(fechaModificacion);

        assertEquals(idAutor, autorDTO.getIdAutor());
        assertEquals(nombre, autorDTO.getNombre());
        assertEquals(apellido, autorDTO.getApellido());
        assertEquals(fechaNacimiento, autorDTO.getFechaNacimiento());
        assertEquals(nacionalidad, autorDTO.getNacionalidad());
        assertEquals(fechaCreacion, autorDTO.getFechaCreacion());
        assertEquals(fechaModificacion, autorDTO.getFechaModificacion());
    }

    @Test
    void testAutorDTOEquality() {
        LocalDate fechaNacimiento1 = LocalDate.of(1927, 3, 6);
        LocalDateTime fechaCreacion1 = LocalDateTime.of(2020, 1, 1, 10, 0);
        LocalDateTime fechaModificacion1 = LocalDateTime.of(2020, 1, 2, 11, 0);

        AutorDTO autorDTO1 = new AutorDTO(1L, "Gabriel", "García Márquez", fechaNacimiento1, "Colombiana", fechaCreacion1, fechaModificacion1);
        AutorDTO autorDTO2 = new AutorDTO(1L, "Gabriel", "García Márquez", fechaNacimiento1, "Colombiana", fechaCreacion1, fechaModificacion1);
        AutorDTO autorDTO3 = new AutorDTO(2L, "Isabel", "Allende", LocalDate.of(1942, 8, 2), "Chilena", LocalDateTime.of(2021, 1, 1, 10, 0), LocalDateTime.of(2021, 1, 2, 11, 0));

        assertEquals(autorDTO1, autorDTO2);
        assertNotEquals(autorDTO1, autorDTO3);
    }

    @Test
    void testAutorDTOHashCode() {
        LocalDate fechaNacimiento1 = LocalDate.of(1927, 3, 6);
        LocalDateTime fechaCreacion1 = LocalDateTime.of(2020, 1, 1, 10, 0);
        LocalDateTime fechaModificacion1 = LocalDateTime.of(2020, 1, 2, 11, 0);

        AutorDTO autorDTO1 = new AutorDTO(1L, "Gabriel", "García Márquez", fechaNacimiento1, "Colombiana", fechaCreacion1, fechaModificacion1);
        AutorDTO autorDTO2 = new AutorDTO(1L, "Gabriel", "García Márquez", fechaNacimiento1, "Colombiana", fechaCreacion1, fechaModificacion1);

        assertEquals(autorDTO1.hashCode(), autorDTO2.hashCode());
    }
}