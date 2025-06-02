package com.humberto.books.books_api.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
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

import com.humberto.books.books_api.model.Autor;

@Repository
public class AutorRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall insertarAutorCall;
    private final SimpleJdbcCall actualizarAutorCall;
    private final SimpleJdbcCall eliminarAutorCall;
    private final SimpleJdbcCall consultarAutorPorIdCall;
    private final SimpleJdbcCall consultarTodosAutoresCall;

    @Autowired
    public AutorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        // Configuración para SP_INSERTAR_AUTOR
        this.insertarAutorCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_AUTORES")
                .withProcedureName("SP_INSERTAR_AUTOR")
                .declareParameters(
                        new SqlParameter("p_nombre", Types.VARCHAR),
                        new SqlParameter("p_apellido", Types.VARCHAR),
                        new SqlParameter("p_fecha_nacimiento", Types.DATE),
                        new SqlParameter("p_nacionalidad", Types.VARCHAR),
                        new SqlOutParameter("p_id_autor_out", Types.NUMERIC),
                        new SqlOutParameter("p_error_code", Types.NUMERIC),
                        new SqlOutParameter("p_error_message", Types.VARCHAR)
                );

        // Configuración para SP_ACTUALIZAR_AUTOR
        this.actualizarAutorCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_AUTORES")
                .withProcedureName("SP_ACTUALIZAR_AUTOR")
                .declareParameters(
                        new SqlParameter("p_id_autor", Types.NUMERIC),
                        new SqlParameter("p_nombre", Types.VARCHAR),
                        new SqlParameter("p_apellido", Types.VARCHAR),
                        new SqlParameter("p_fecha_nacimiento", Types.DATE),
                        new SqlParameter("p_nacionalidad", Types.VARCHAR),
                        new SqlOutParameter("p_error_code", Types.NUMERIC),
                        new SqlOutParameter("p_error_message", Types.VARCHAR)
                );

        // Configuración para SP_ELIMINAR_AUTOR
        this.eliminarAutorCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_AUTORES")
                .withProcedureName("SP_ELIMINAR_AUTOR")
                .declareParameters(
                        new SqlParameter("p_id_autor", Types.NUMERIC),
                        new SqlOutParameter("p_error_code", Types.NUMERIC),
                        new SqlOutParameter("p_error_message", Types.VARCHAR)
                );

        // Configuración para SF_CONSULTAR_AUTOR_POR_ID
        this.consultarAutorPorIdCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_AUTORES")
                .withFunctionName("SF_CONSULTAR_AUTOR_POR_ID")
                .declareParameters(
                        new SqlParameter("p_id_autor", Types.NUMERIC),
                        new SqlOutParameter("RETURN", Types.REF_CURSOR, new AutorRowMapper())
                );

        // Configuración para SF_CONSULTAR_TODOS_AUTORES
        this.consultarTodosAutoresCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_GESTION_AUTORES")
                .withFunctionName("SF_CONSULTAR_TODOS_AUTORES")
                .declareParameters(
                        new SqlOutParameter("RETURN", Types.REF_CURSOR, new AutorRowMapper())
                );
    }

    public List<Autor> findAll() {
        Map<String, Object> result = consultarTodosAutoresCall.execute();
        return (List<Autor>) result.get("RETURN");
    }

    public Optional<Autor> findById(Long id) {
        Map<String, Object> in = Collections.singletonMap("p_id_autor", id);
        Map<String, Object> result = consultarAutorPorIdCall.execute(in);
        List<Autor> autores = (List<Autor>) result.get("RETURN");
        return autores.isEmpty() ? Optional.empty() : Optional.of(autores.get(0));
    }

    public Autor save(Autor autor) {
        Map<String, Object> in = Map.of(
                "p_nombre", autor.getNombre(),
                "p_apellido", autor.getApellido(),
                "p_fecha_nacimiento", autor.getFechaNacimiento(),
                "p_nacionalidad", autor.getNacionalidad()
        );
        Map<String, Object> out = insertarAutorCall.execute(in);
        int errorCode = ((Number) out.get("p_error_code")).intValue();
        String errorMessage = (String) out.get("p_error_message");

        if (errorCode != 0) {
            throw new RuntimeException("Error al insertar autor: " + errorMessage + " (Código: " + errorCode + ")");
        }
        autor.setIdAutor(((Number) out.get("p_id_autor_out")).longValue());
        return autor;
    }

    public Autor update(Autor autor) {
        Map<String, Object> in = Map.of(
                "p_id_autor", autor.getIdAutor(),
                "p_nombre", autor.getNombre(),
                "p_apellido", autor.getApellido(),
                "p_fecha_nacimiento", autor.getFechaNacimiento(),
                "p_nacionalidad", autor.getNacionalidad()
        );
        Map<String, Object> out = actualizarAutorCall.execute(in);
        int errorCode = ((Number) out.get("p_error_code")).intValue();
        String errorMessage = (String) out.get("p_error_message");

        if (errorCode != 0) {
            throw new RuntimeException("Error al actualizar autor: " + errorMessage + " (Código: " + errorCode + ")");
        }
        return autor;
    }

    public void deleteById(Long id) {
        Map<String, Object> in = Collections.singletonMap("p_id_autor", id);
        Map<String, Object> out = eliminarAutorCall.execute(in);
        int errorCode = ((Number) out.get("p_error_code")).intValue();
        String errorMessage = (String) out.get("p_error_message");

        if (errorCode != 0) {
            throw new RuntimeException("Error al eliminar autor: " + errorMessage + " (Código: " + errorCode + ")");
        }
    }

    private static class AutorRowMapper implements RowMapper<Autor> {
        @Override
        public Autor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Autor autor = new Autor();
            autor.setIdAutor(rs.getLong("ID_AUTOR"));
            autor.setNombre(rs.getString("NOMBRE"));
            autor.setApellido(rs.getString("APELLIDO"));
            autor.setFechaNacimiento(rs.getObject("FECHA_NACIMIENTO", LocalDate.class));
            autor.setNacionalidad(rs.getString("NACIONALIDAD"));
            autor.setFechaCreacion(rs.getObject("FECHA_CREACION", LocalDateTime.class));
            autor.setFechaModificacion(rs.getObject("FECHA_MODIFICACION", LocalDateTime.class));
            return autor;
        }
    }
}