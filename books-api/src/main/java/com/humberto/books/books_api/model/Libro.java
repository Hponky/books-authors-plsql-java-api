package com.humberto.books.books_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "LIBROS")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LIBRO")
    private Long idLibro;

    @Column(name = "TITULO", nullable = false, length = 255)
    private String titulo;

    @Column(name = "ISBN", unique = true, nullable = false, length = 20)
    private String isbn;

    @Column(name = "ANIO_PUBLICACION")
    private Integer anioPublicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AUTOR", nullable = false)
    private Autor autor;

    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;

    public Libro() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Libro(Long idLibro, String titulo, String isbn, Integer anioPublicacion, Autor autor, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.autor = autor;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    // Getters
    public Long getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public Autor getAutor() {
        return autor;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    // Setters
    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(idLibro, libro.idLibro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLibro);
    }

    @Override
    public String toString() {
        return "Libro{" +
               "idLibro=" + idLibro +
               ", titulo='" + titulo + '\'' +
               ", isbn='" + isbn + '\'' +
               ", anioPublicacion=" + anioPublicacion +
               ", autor=" + autor +
               ", fechaCreacion=" + fechaCreacion +
               ", fechaModificacion=" + fechaModificacion +
               '}';
    }
}