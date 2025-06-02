package com.humberto.books.books_api.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.humberto.books.books_api.model.Libro;

@Repository
public class LibroRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AutorRepository autorRepository; // Necesario para mapear el autor en Libro
    private final SimpleJdbcCall insertarLibroCall;
    private final SimpleJdbcCall actualizarLibroCall;
    private final SimpleJdbcCall eliminarLibroCall;
    private final SimpleJdbcCall consultarLibroPorIdCall;
    private final SimpleJdbcCall consultarTodosLibrosCall;
    private final SimpleJdbcCall consultarLibrosPorAutorCall;
    private final SimpleJdbcCall consultarLibroPorIsbnCall;

    @Autowired
    public LibroRepository(JdbcTemplate jdbcTemplate, AutorRepository autorRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.autorRepository = autorRepository;

        // Configuración para SP_INSERTAR_LIBRO
        this.insertarLibroCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_LIBROS")
                .withProcedureName("SP_INSERTAR_LIBRO")
                .declareParameters(
                        new SqlParameter("p_titulo", Types.VARCHAR),
                        new SqlParameter("p_isbn", Types.VARCHAR),
                        new SqlParameter("p_anio_publicacion", Types.NUMERIC),
                        new SqlParameter("p_id_autor", Types.NUMERIC),
                        new SqlOutParameter("p_id_libro_out", Types.NUMERIC),
                        new SqlOutParameter("p_error_code", Types.NUMERIC),
                        new SqlOutParameter("p_error_message", Types.VARCHAR)
                );

        // Configuración para SP_ACTUALIZAR_LIBRO
        this.actualizarLibroCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_LIBROS")
                .withProcedureName("SP_ACTUALIZAR_LIBRO")
                .declareParameters(
                        new SqlParameter("p_id_libro", Types.NUMERIC),
                        new SqlParameter("p_titulo", Types.VARCHAR),
                        new SqlParameter("p_isbn", Types.VARCHAR),
                        new SqlParameter("p_anio_publicacion", Types.NUMERIC),
                        new SqlParameter("p_id_autor", Types.NUMERIC),
                        new SqlOutParameter("p_error_code", Types.NUMERIC),
                        new SqlOutParameter("p_error_message", Types.VARCHAR)
                );

        // Configuración para SP_ELIMINAR_LIBRO
        this.eliminarLibroCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_LIBROS")
                .withProcedureName("SP_ELIMINAR_LIBRO")
                .declareParameters(
                        new SqlParameter("p_id_libro", Types.NUMERIC),
                        new SqlOutParameter("p_error_code", Types.NUMERIC),
                        new SqlOutParameter("p_error_message", Types.VARCHAR)
                );

        // Configuración para SF_CONSULTAR_LIBRO_POR_ID
        this.consultarLibroPorIdCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_LIBROS")
                .withFunctionName("SF_CONSULTAR_LIBRO_POR_ID")
                .declareParameters(
                        new SqlParameter("p_id_libro", Types.NUMERIC),
                        new SqlOutParameter("RETURN", Types.REF_CURSOR, new LibroRowMapper())
                );

        // Configuración para SF_CONSULTAR_TODOS_LIBROS
        this.consultarTodosLibrosCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_LIBROS")
                .withFunctionName("SF_CONSULTAR_TODOS_LIBROS")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.REF_CURSOR, new LibroRowMapper())
                );

        // Configuración para SF_CONSULTAR_LIBROS_POR_AUTOR
        this.consultarLibrosPorAutorCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_LIBROS")
                .withFunctionName("SF_CONSULTAR_LIBROS_POR_AUTOR")
                .declareParameters(
                        new SqlParameter("p_id_autor", Types.NUMERIC),
                        new SqlOutParameter("RETURN", Types.REF_CURSOR, new LibroRowMapper())
                );

        // Configuración para SF_CONSULTAR_LIBRO_POR_ISBN
        this.consultarLibroPorIsbnCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_LIBROS")
                .withFunctionName("SF_CONSULTAR_LIBRO_POR_ISBN")
                .declareParameters(
                        new SqlParameter("p_isbn", Types.VARCHAR),
                        new SqlOutParameter("RETURN", Types.REF_CURSOR, new LibroRowMapper())
                );
    }

    public List<Libro> findAll() {
        Map<String, Object> result = consultarTodosLibrosCall.execute();
        return (List<Libro>) result.get("RETURN");
    }

    public Optional<Libro> findById(Long id) {
        Map<String, Object> in = Collections.singletonMap("p_id_libro", id);
        Map<String, Object> result = consultarLibroPorIdCall.execute(in);
        List<Libro> libros = (List<Libro>) result.get("RETURN");
        return libros.isEmpty() ? Optional.empty() : Optional.of(libros.get(0));
    }

    public Libro save(Libro libro) {
        Map<String, Object> in = Map.of(
                "p_titulo", libro.getTitulo(),
                "p_isbn", libro.getIsbn(),
                "p_anio_publicacion", libro.getAnioPublicacion(),
                "p_id_autor", libro.getAutor() != null ? libro.getAutor().getIdAutor() : null
        );
        Map<String, Object> out = insertarLibroCall.execute(in);
        int errorCode = ((Number) out.get("p_error_code")).intValue();
        String errorMessage = (String) out.get("p_error_message");

        if (errorCode != 0) {
            throw new RuntimeException("Error al insertar libro: " + errorMessage + " (Código: " + errorCode + ")");
        }
        libro.setIdLibro(((Number) out.get("p_id_libro_out")).longValue());
        return libro;
    }

    public Libro update(Libro libro) {
        Map<String, Object> in = Map.of(
                "p_id_libro", libro.getIdLibro(),
                "p_titulo", libro.getTitulo(),
                "p_isbn", libro.getIsbn(),
                "p_anio_publicacion", libro.getAnioPublicacion(),
                "p_id_autor", libro.getAutor() != null ? libro.getAutor().getIdAutor() : null
        );
        Map<String, Object> out = actualizarLibroCall.execute(in);
        int errorCode = ((Number) out.get("p_error_code")).intValue();
        String errorMessage = (String) out.get("p_error_message");

        if (errorCode != 0) {
            throw new RuntimeException("Error al actualizar libro: " + errorMessage + " (Código: " + errorCode + ")");
        }
        return libro;
    }

    public void deleteById(Long id) {
        Map<String, Object> in = Collections.singletonMap("p_id_libro", id);
        Map<String, Object> out = eliminarLibroCall.execute(in);
        int errorCode = ((Number) out.get("p_error_code")).intValue();
        String errorMessage = (String) out.get("p_error_message");

        if (errorCode != 0) {
            throw new RuntimeException("Error al eliminar libro: " + errorMessage + " (Código: " + errorCode + ")");
        }
    }

    public List<Libro> findByAutorId(Long autorId) {
        Map<String, Object> in = Collections.singletonMap("p_id_autor", autorId);
        Map<String, Object> result = consultarLibrosPorAutorCall.execute(in);
        return (List<Libro>) result.get("RETURN");
    }

    public Optional<Libro> findByIsbn(String isbn) {
        Map<String, Object> in = Collections.singletonMap("p_isbn", isbn);
        Map<String, Object> result = consultarLibroPorIsbnCall.execute(in);
        List<Libro> libros = (List<Libro>) result.get("RETURN");
        return libros.isEmpty() ? Optional.empty() : Optional.of(libros.get(0));
    }

    private class LibroRowMapper implements RowMapper<Libro> {
        @Override
        public Libro mapRow(ResultSet rs, int rowNum) throws SQLException {
            Libro libro = new Libro();
            libro.setIdLibro(rs.getLong("ID_LIBRO"));
            libro.setTitulo(rs.getString("TITULO"));
            libro.setIsbn(rs.getString("ISBN"));
            libro.setAnioPublicacion(rs.getObject("ANIO_PUBLICACION", Integer.class));
            libro.setFechaCreacion(rs.getObject("FECHA_CREACION", LocalDateTime.class));
            libro.setFechaModificacion(rs.getObject("FECHA_MODIFICACION", LocalDateTime.class));

            // Obtener el autor usando AutorRepository
            Long idAutor = rs.getLong("ID_AUTOR");
            autorRepository.findById(idAutor).ifPresent(libro::setAutor);

            return libro;
        }
    }
}