package com.humberto.books.books_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "AUTORES")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AUTOR")
    private Long idAutor;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 100)
    private String apellido;

    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;

    @Column(name = "NACIONALIDAD", length = 50)
    private String nacionalidad;

    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;

    public Autor() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Autor(Long idAutor, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.idAutor = idAutor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    // Getters
    public Long getIdAutor() {
        return idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    // Setters
    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
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
        Autor autor = (Autor) o;
        return Objects.equals(idAutor, autor.idAutor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAutor);
    }

    @Override
    public String toString() {
        return "Autor{" +
               "idAutor=" + idAutor +
               ", nombre='" + nombre + '\'' +
               ", apellido='" + apellido + '\'' +
               ", fechaNacimiento=" + fechaNacimiento +
               ", nacionalidad='" + nacionalidad + '\'' +
               ", fechaCreacion=" + fechaCreacion +
               ", fechaModificacion=" + fechaModificacion +
               '}';
    }
}