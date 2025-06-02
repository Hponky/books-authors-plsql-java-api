package com.humberto.books.books_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.humberto.books.books_api.model.Libro;
import com.humberto.books.books_api.repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }

    public Libro save(Libro libro) {
        if (libro.getIdLibro() == null) {
            return libroRepository.save(libro);
        } else {
            return libroRepository.update(libro);
        }
    }

    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }

    public List<Libro> findByAutorId(Long autorId) {
        return libroRepository.findByAutorId(autorId);
    }

    public Optional<Libro> findByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }
}