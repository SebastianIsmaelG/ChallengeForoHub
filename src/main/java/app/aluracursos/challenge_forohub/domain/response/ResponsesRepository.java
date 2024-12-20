package app.aluracursos.challenge_forohub.domain.response;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResponsesRepository extends JpaRepository<Responses, Long> {


    void deleteAllByTopicoId(Long id);

    @Query("SELECT r FROM Respuesta r WHERE r.topico.id = :id ORDER BY r.solucion DESC")
    List<Responses> findAllByTopicoId(Long id);
}
