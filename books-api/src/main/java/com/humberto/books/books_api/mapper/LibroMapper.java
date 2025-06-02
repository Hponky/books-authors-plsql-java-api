package com.humberto.books.books_api.mapper;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.humberto.books.books_api.dto.LibroDTO;
import com.humberto.books.books_api.model.Autor;
import com.humberto.books.books_api.model.Libro;
import com.humberto.books.books_api.repository.AutorRepository;

@Component
public class LibroMapper {

    private final AutorRepository autorRepository;

    public LibroMapper(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public LibroDTO toDTO(Libro libro) {
        if (libro == null) {
            return null;
        }
        return new LibroDTO(
                libro.getIdLibro(),
                libro.getTitulo(),
                libro.getIsbn(),
                libro.getAnioPublicacion(),
                libro.getAutor() != null ? libro.getAutor().getIdAutor() : null,
                libro.getFechaCreacion(),
                libro.getFechaModificacion()
        );
    }

    public Libro toEntity(LibroDTO libroDTO) {
        if (libroDTO == null) {
            return null;
        }
        Libro libro = new Libro();
        libro.setIdLibro(libroDTO.getIdLibro());
        libro.setTitulo(libroDTO.getTitulo());
        libro.setIsbn(libroDTO.getIsbn());
        libro.setAnioPublicacion(libroDTO.getAnioPublicacion());

        if (libroDTO.getIdAutor() != null) {
            Optional<Autor> autorOptional = autorRepository.findById(libroDTO.getIdAutor());
            autorOptional.ifPresent(libro::setAutor);
        }

        // Fecha de creación y modificación se manejan en la entidad con @PrePersist y @PreUpdate
        if (libroDTO.getFechaCreacion() != null) {
            libro.setFechaCreacion(libroDTO.getFechaCreacion());
        } else {
            libro.setFechaCreacion(LocalDateTime.now()); // Asegurar que siempre tenga un valor
        }
        if (libroDTO.getFechaModificacion() != null) {
            libro.setFechaModificacion(libroDTO.getFechaModificacion());
        }
        return libro;
    }
}