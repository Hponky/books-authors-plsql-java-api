package com.humberto.books.books_api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import static org.mockito.MockitoAnnotations.openMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humberto.books.books_api.model.Autor;
import com.humberto.books.books_api.model.Libro;
import com.humberto.books.books_api.service.LibroService;

@WebMvcTest(LibroController.class)
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LibroService libroService;

    @InjectMocks
    private LibroController libroController;

    @Autowired
    private ObjectMapper objectMapper;

    private Libro libro1;
    private Libro libro2;
    private Autor autor;

    @BeforeEach
    void setUp() {
        openMocks(this); // Inicializa los mocks
        autor = new Autor(1L, "Gabriel", "García Márquez", LocalDate.of(1927, 3, 6), "Colombiana", LocalDateTime.now(), LocalDateTime.now());
        libro1 = new Libro(1L, "Cien años de soledad", "978-0307474278", 1967, autor, LocalDateTime.now(), LocalDateTime.now());
        libro2 = new Libro(2L, "El amor en los tiempos del cólera", "978-0307387424", 1985, autor, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void getAllLibrosShouldReturnListOfLibros() throws Exception {
        when(libroService.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        mockMvc.perform(get("/api/libros")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].titulo", is(libro1.getTitulo())))
                .andExpect(jsonPath("$[1].titulo", is(libro2.getTitulo())));

        verify(libroService, times(1)).findAll();
    }

    @Test
    void getLibroByIdShouldReturnLibroWhenFound() throws Exception {
        when(libroService.findById(1L)).thenReturn(Optional.of(libro1));

        mockMvc.perform(get("/api/libros/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is(libro1.getTitulo())));

        verify(libroService, times(1)).findById(1L);
    }

    @Test
    void getLibroByIdShouldReturnNotFoundWhenNotFound() throws Exception {
        when(libroService.findById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/libros/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(libroService, times(1)).findById(3L);
    }

    @Test
    void createLibroShouldReturnCreatedLibro() throws Exception {
        when(libroService.save(any(Libro.class))).thenReturn(libro1);

        mockMvc.perform(post("/api/libros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libro1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo", is(libro1.getTitulo())));

        verify(libroService, times(1)).save(any(Libro.class));
    }

    @Test
    void updateLibroShouldReturnUpdatedLibroWhenFound() throws Exception {
        Libro updatedDetails = new Libro(1L, "Nuevo Titulo", "978-1234567890", 2023, autor, LocalDateTime.now(), LocalDateTime.now());
        when(libroService.save(any(Libro.class))).thenReturn(updatedDetails);

        mockMvc.perform(put("/api/libros/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is(updatedDetails.getTitulo())));

        verify(libroService, times(1)).save(any(Libro.class));
    }

    @Test
    void updateLibroShouldReturnNotFoundWhenNotFound() throws Exception {
        Libro updatedDetails = new Libro(3L, "Nuevo Titulo", "978-1234567890", 2023, autor, LocalDateTime.now(), LocalDateTime.now());
        when(libroService.save(any(Libro.class))).thenThrow(new RuntimeException("Libro no encontrado para actualizar"));

        mockMvc.perform(put("/api/libros/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isBadRequest());

        verify(libroService, times(1)).save(any(Libro.class));
    }

    @Test
    void deleteLibroShouldReturnNoContentWhenFound() throws Exception {
        doNothing().when(libroService).deleteById(1L);

        mockMvc.perform(delete("/api/libros/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(libroService, times(1)).deleteById(1L);
    }

    @Test
    void deleteLibroShouldReturnNotFoundWhenNotFound() throws Exception {
        doThrow(new RuntimeException("Libro no encontrado para eliminar")).when(libroService).deleteById(3L);

        mockMvc.perform(delete("/api/libros/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(libroService, times(1)).deleteById(3L);
    }
}