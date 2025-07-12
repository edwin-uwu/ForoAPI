package com.edwin.hernandez.foro_api.dominio;

import com.edwin.hernandez.foro_api.modelos.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico,Long> {

    @Query("""
            SELECT t from Topico t
            WHERE t.curso = :curso
            AND Year(t.fechaCreacion) = :anio
            """)
    Page<Topico> buscarPorCursoYAnio(String curso, Integer anio,Pageable paginacion);

    @Query("""
            SELECT c FROM Topico c
            WHERE c.curso =:curso
            """)
    Page<Topico> findByCurso(String curso, Pageable paginacion);
    @Query("""
            SELECT t FROM Topico t
            WHERE Year(t.fechaCreacion) = :anio
            """)
    Page<Topico> findByanio(Integer anio, Pageable paginacion);
}
