package com.humberto.books.books_api.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class LibroDTO {
    private Long idLibro;
    private String titulo;
    private String isbn;
    private Integer anioPublicacion;
    private Long idAutor;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    public LibroDTO() {
    }

    public LibroDTO(Long idLibro, String titulo, String isbn, Integer anioPublicacion, Long idAutor, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.idAutor = idAutor;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibroDTO libroDTO = (LibroDTO) o;
        return Objects.equals(idLibro, libroDTO.idLibro) &&
               Objects.equals(titulo, libroDTO.titulo) &&
               Objects.equals(isbn, libroDTO.isbn) &&
               Objects.equals(anioPublicacion, libroDTO.anioPublicacion) &&
               Objects.equals(idAutor, libroDTO.idAutor) &&
               Objects.equals(fechaCreacion, libroDTO.fechaCreacion) &&
               Objects.equals(fechaModificacion, libroDTO.fechaModificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLibro, titulo, isbn, anioPublicacion, idAutor, fechaCreacion, fechaModificacion);
    }
}