package com.humberto.books.books_api.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.humberto.books.books_api.dto.AutorDTO;
import com.humberto.books.books_api.model.Autor;

@Component
public class AutorMapper {

    public AutorDTO toDTO(Autor autor) {
        if (autor == null) {
            return null;
        }
        return new AutorDTO(
                autor.getIdAutor(),
                autor.getNombre(),
                autor.getApellido(),
                autor.getFechaNacimiento(),
                autor.getNacionalidad(),
                autor.getFechaCreacion(),
                autor.getFechaModificacion()
        );
    }

    public Autor toEntity(AutorDTO autorDTO) {
        if (autorDTO == null) {
            return null;
        }
        Autor autor = new Autor();
        autor.setIdAutor(autorDTO.getIdAutor());
        autor.setNombre(autorDTO.getNombre());
        autor.setApellido(autorDTO.getApellido());
        autor.setFechaNacimiento(autorDTO.getFechaNacimiento());
        autor.setNacionalidad(autorDTO.getNacionalidad());
        // Fecha de creación y modificación se manejan en la entidad con @PrePersist y @PreUpdate
        // Si el DTO trae estas fechas, se pueden establecer, de lo contrario, la entidad las generará.
        if (autorDTO.getFechaCreacion() != null) {
            autor.setFechaCreacion(autorDTO.getFechaCreacion());
        } else {
            autor.setFechaCreacion(LocalDateTime.now()); // Asegurar que siempre tenga un valor
        }
        if (autorDTO.getFechaModificacion() != null) {
            autor.setFechaModificacion(autorDTO.getFechaModificacion());
        }
        return autor;
    }
}