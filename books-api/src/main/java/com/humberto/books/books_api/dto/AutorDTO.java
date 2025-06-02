package com.humberto.books.books_api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class AutorDTO {
    private Long idAutor;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    public AutorDTO() {
    }

    public AutorDTO(Long idAutor, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.idAutor = idAutor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
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
        AutorDTO autorDTO = (AutorDTO) o;
        return Objects.equals(idAutor, autorDTO.idAutor) &&
               Objects.equals(nombre, autorDTO.nombre) &&
               Objects.equals(apellido, autorDTO.apellido) &&
               Objects.equals(fechaNacimiento, autorDTO.fechaNacimiento) &&
               Objects.equals(nacionalidad, autorDTO.nacionalidad) &&
               Objects.equals(fechaCreacion, autorDTO.fechaCreacion) &&
               Objects.equals(fechaModificacion, autorDTO.fechaModificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAutor, nombre, apellido, fechaNacimiento, nacionalidad, fechaCreacion, fechaModificacion);
    }
}