package com.humberto.books.books_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.humberto.books.books_api.model.Autor;
import com.humberto.books.books_api.repository.AutorRepository;

class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    private Autor autor1;
    private Autor autor2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        autor1 = new Autor(1L, "Gabriel", "García Márquez", LocalDate.of(1927, 3, 6), "Colombiana", LocalDateTime.now(), LocalDateTime.now());
        autor2 = new Autor(2L, "Isabel", "Allende", LocalDate.of(1942, 8, 2), "Chilena", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void findAllShouldReturnAllAutores() {
        when(autorRepository.findAll()).thenReturn(Arrays.asList(autor1, autor2));

        List<Autor> autores = autorService.findAll();

        assertNotNull(autores);
        assertEquals(2, autores.size());
        assertTrue(autores.contains(autor1));
        assertTrue(autores.contains(autor2));
        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnAutorWhenFound() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor1));

        Optional<Autor> foundAutor = autorService.findById(1L);

        assertTrue(foundAutor.isPresent());
        assertEquals(autor1, foundAutor.get());
        verify(autorRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(autorRepository.findById(3L)).thenReturn(Optional.empty());

        Optional<Autor> foundAutor = autorService.findById(3L);

        assertFalse(foundAutor.isPresent());
        verify(autorRepository, times(1)).findById(3L);
    }

    @Test
    void saveShouldInsertAutorWhenIdIsNull() {
        Autor newAutor = new Autor(null, "Nuevo", "Autor", LocalDate.now(), "Nacionalidad", null, null);
        when(autorRepository.save(any(Autor.class))).thenReturn(autor1);

        Autor savedAutor = autorService.save(newAutor);

        assertNotNull(savedAutor);
        assertEquals(autor1, savedAutor);
        verify(autorRepository, times(1)).save(newAutor);
        verify(autorRepository, never()).update(any(Autor.class));
    }

    @Test
    void saveShouldUpdateAutorWhenIdIsNotNull() {
        Autor existingAutor = new Autor(1L, "Existente", "Autor", LocalDate.now(), "Nacionalidad", LocalDateTime.now(), LocalDateTime.now());
        when(autorRepository.update(any(Autor.class))).thenReturn(existingAutor);

        Autor updatedAutor = autorService.save(existingAutor);

        assertNotNull(updatedAutor);
        assertEquals(existingAutor, updatedAutor);
        verify(autorRepository, times(1)).update(existingAutor);
        verify(autorRepository, never()).save(any(Autor.class));
    }

    @Test
    void updateShouldThrowExceptionOnRepositoryError() {
        Autor existingAutor = new Autor(1L, "Existente", "Autor", LocalDate.now(), "Nacionalidad", LocalDateTime.now(), LocalDateTime.now());
        when(autorRepository.update(any(Autor.class))).thenThrow(new RuntimeException("Error al actualizar autor"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> autorService.save(existingAutor));
        assertTrue(thrown.getMessage().contains("Error al actualizar autor"));
    }

    @Test
    void saveShouldThrowExceptionOnRepositoryError() {
        Autor newAutor = new Autor(null, "Nuevo", "Autor", LocalDate.now(), "Nacionalidad", null, null);
        when(autorRepository.save(any(Autor.class))).thenThrow(new RuntimeException("Error al guardar autor"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> autorService.save(newAutor));
        assertTrue(thrown.getMessage().contains("Error al guardar autor"));
    }

    @Test
    void deleteByIdShouldCallRepositoryDelete() {
        doNothing().when(autorRepository).deleteById(1L);

        autorService.deleteById(1L);

        verify(autorRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdShouldThrowExceptionOnRepositoryError() {
        doThrow(new RuntimeException("Error de eliminación")).when(autorRepository).deleteById(1L);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> autorService.deleteById(1L));
        assertTrue(thrown.getMessage().contains("Error de eliminación"));
    }
}