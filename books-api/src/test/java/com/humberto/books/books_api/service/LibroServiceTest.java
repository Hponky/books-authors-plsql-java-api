package com.humberto.books.books_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.humberto.books.books_api.model.Autor;
import com.humberto.books.books_api.model.Libro;
import com.humberto.books.books_api.repository.LibroRepository;

class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroService libroService;

    private Libro libro1;
    private Libro libro2;
    private Autor autor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        autor = new Autor(1L, "Gabriel", "García Márquez", LocalDate.of(1927, 3, 6), "Colombiana", LocalDateTime.now(), LocalDateTime.now());
        libro1 = new Libro(1L, "Cien años de soledad", "978-0307474278", 1967, autor, LocalDateTime.now(), LocalDateTime.now());
        libro2 = new Libro(2L, "El amor en los tiempos del cólera", "978-0307387424", 1985, autor, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void findAllShouldReturnAllLibros() {
        when(libroRepository.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        List<Libro> libros = libroService.findAll();

        assertNotNull(libros);
        assertEquals(2, libros.size());
        assertTrue(libros.contains(libro1));
        assertTrue(libros.contains(libro2));
        verify(libroRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnLibroWhenFound() {
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro1));

        Optional<Libro> foundLibro = libroService.findById(1L);

        assertTrue(foundLibro.isPresent());
        assertEquals(libro1, foundLibro.get());
        verify(libroRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(libroRepository.findById(3L)).thenReturn(Optional.empty());

        Optional<Libro> foundLibro = libroService.findById(3L);

        assertFalse(foundLibro.isPresent());
        verify(libroRepository, times(1)).findById(3L);
    }

    @Test
    void saveShouldReturnSavedLibro() {
        Libro newLibro = new Libro(null, "Nuevo Libro", "978-1111111111", 2024, autor, null, null);
        when(libroRepository.save(any(Libro.class))).thenReturn(libro1);

        Libro savedLibro = libroService.save(newLibro);

        assertNotNull(savedLibro);
        assertEquals(libro1, savedLibro);
        verify(libroRepository, times(1)).save(newLibro);
    }

    @Test
    void updateShouldReturnUpdatedLibro() {
        Libro existingLibro = new Libro(1L, "Libro Actualizado", "978-1234567890", 2023, autor, LocalDateTime.now(), LocalDateTime.now());
        when(libroRepository.update(any(Libro.class))).thenReturn(existingLibro);

        Libro updatedLibro = libroService.save(existingLibro);

        assertNotNull(updatedLibro);
        assertEquals(existingLibro, updatedLibro);
        verify(libroRepository, times(1)).update(existingLibro);
    }

    @Test
    void saveShouldThrowExceptionOnRepositoryError() {
        Libro newLibro = new Libro(null, "Nuevo Libro", "978-1111111111", 2024, autor, null, null);
        when(libroRepository.save(any(Libro.class))).thenThrow(new RuntimeException("Error al guardar libro"));

        RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> libroService.save(newLibro));
        org.junit.jupiter.api.Assertions.assertTrue(thrown.getMessage().contains("Error al guardar libro"));
    }

    @Test
    void updateShouldThrowExceptionOnRepositoryError() {
        Libro existingLibro = new Libro(1L, "Libro Existente", "978-2222222222", 2020, autor, LocalDateTime.now(), LocalDateTime.now());
        when(libroRepository.update(any(Libro.class))).thenThrow(new RuntimeException("Error al actualizar libro"));

        RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> libroService.save(existingLibro));
        org.junit.jupiter.api.Assertions.assertTrue(thrown.getMessage().contains("Error al actualizar libro"));
    }

    @Test
    void deleteByIdShouldCallRepositoryDelete() {
        doNothing().when(libroRepository).deleteById(1L);

        libroService.deleteById(1L);

        verify(libroRepository, times(1)).deleteById(1L);
    }
    @Test
    void deleteByIdShouldThrowExceptionOnRepositoryError() {
        doThrow(new RuntimeException("Error de eliminación de libro")).when(libroRepository).deleteById(1L);

        RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> libroService.deleteById(1L));
        org.junit.jupiter.api.Assertions.assertTrue(thrown.getMessage().contains("Error de eliminación de libro"));
    }
}