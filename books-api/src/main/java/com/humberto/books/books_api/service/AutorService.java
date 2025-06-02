package com.humberto.books.books_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.humberto.books.books_api.model.Autor;
import com.humberto.books.books_api.repository.AutorRepository;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Optional<Autor> findById(Long id) {
        return autorRepository.findById(id);
    }

    public Autor save(Autor autor) {
        if (autor.getIdAutor() == null) {
            return autorRepository.save(autor);
        } else {
            return autorRepository.update(autor);
        }
    }

    public void deleteById(Long id) {
        autorRepository.deleteById(id);
    }
}