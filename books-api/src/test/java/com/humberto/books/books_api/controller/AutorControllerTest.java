package com.humberto.books.books_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humberto.books.books_api.model.Autor;
import com.humberto.books.books_api.service.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AutorController.class)
class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AutorService autorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Autor autor1;
    private Autor autor2;

    @BeforeEach
    void setUp() {
        autor1 = new Autor(1L, "Gabriel", "García Márquez", LocalDate.of(1927, 3, 6), "Colombiana", LocalDateTime.now(), LocalDateTime.now());
        autor2 = new Autor(2L, "Isabel", "Allende", LocalDate.of(1942, 8, 2), "Chilena", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void getAllAutoresShouldReturnListOfAutores() throws Exception {
        when(autorService.findAll()).thenReturn(Arrays.asList(autor1, autor2));

        mockMvc.perform(get("/api/autores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is(autor1.getNombre())))
                .andExpect(jsonPath("$[1].nombre", is(autor2.getNombre())));

        verify(autorService, times(1)).findAll();
    }

    @Test
    void getAutorByIdShouldReturnAutorWhenFound() throws Exception {
        when(autorService.findById(1L)).thenReturn(Optional.of(autor1));

        mockMvc.perform(get("/api/autores/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(autor1.getNombre())));

        verify(autorService, times(1)).findById(1L);
    }

    @Test
    void getAutorByIdShouldReturnNotFoundWhenNotFound() throws Exception {
        when(autorService.findById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/autores/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(autorService, times(1)).findById(3L);
    }

    @Test
    void createAutorShouldReturnCreatedAutor() throws Exception {
        when(autorService.save(any(Autor.class))).thenReturn(autor1);

        mockMvc.perform(post("/api/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(autor1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(autor1.getNombre())));

        verify(autorService, times(1)).save(any(Autor.class));
    }

    @Test
    void updateAutorShouldReturnUpdatedAutorWhenFound() throws Exception {
        Autor updatedDetails = new Autor(1L, "Nuevo Nombre", "Nuevo Apellido", LocalDate.of(1927, 3, 6), "Nueva Nacionalidad", LocalDateTime.now(), LocalDateTime.now());
        when(autorService.save(any(Autor.class))).thenReturn(updatedDetails);

        mockMvc.perform(put("/api/autores/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(updatedDetails.getNombre())));

        verify(autorService, times(1)).save(any(Autor.class));
    }

    @Test
    void updateAutorShouldReturnNotFoundWhenNotFound() throws Exception {
        Autor updatedDetails = new Autor(3L, "Nuevo Nombre", "Nuevo Apellido", LocalDate.of(1927, 3, 6), "Nueva Nacionalidad", LocalDateTime.now(), LocalDateTime.now());
        when(autorService.save(any(Autor.class))).thenThrow(new RuntimeException("Autor no encontrado para actualizar"));

        mockMvc.perform(put("/api/autores/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isBadRequest());

        verify(autorService, times(1)).save(any(Autor.class));
    }

    @Test
    void deleteAutorShouldReturnNoContentWhenFound() throws Exception {
        doNothing().when(autorService).deleteById(1L);

        mockMvc.perform(delete("/api/autores/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(autorService, times(1)).deleteById(1L);
    }

    @Test
    void deleteAutorShouldReturnNotFoundWhenNotFound() throws Exception {
        doThrow(new RuntimeException("Autor no encontrado para eliminar")).when(autorService).deleteById(3L);

        mockMvc.perform(delete("/api/autores/{id}", 3L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(autorService, times(1)).deleteById(3L);
    }
}