package com.humberto.books.books_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.humberto.books.books_api.model.Autor;
import com.humberto.books.books_api.service.AutorService;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<Autor>> getAllAutores() {
        List<Autor> autores = autorService.findAll();
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> getAutorById(@PathVariable Long id) {
        return autorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createAutor(@RequestBody Autor autor) {
        try {
            Autor savedAutor = autorService.save(autor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAutor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAutor(@PathVariable Long id, @RequestBody Autor autor) {
        try {
            // Asegurarse de que el ID del autor en el cuerpo coincida con el ID de la ruta
            if (!id.equals(autor.getIdAutor())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID del autor en la ruta no coincide con el ID del autor en el cuerpo.");
            }
            Autor updatedAutor = autorService.save(autor);
            return ResponseEntity.ok(updatedAutor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAutor(@PathVariable Long id) {
        try {
            autorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}