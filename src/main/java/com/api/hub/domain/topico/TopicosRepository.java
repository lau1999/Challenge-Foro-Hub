package com.api.hub.domain.topico;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicosRepository extends JpaRepository<Topico,Long> {
    Page<Topico> findByStatusTrue(Pageable paginacion);

    Topico findByTituloIgnoreCase(String titulo);

    Topico findByMensajeIgnoreCase(String mensaje);
    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso AND YEAR(t.fechaCreacion) = :year")
    Page<Topico> findByCursoNombreAndYear(@Param("nombreCurso") String nombreCurso, @Param("year") int year, Pageable paginacion);

}
