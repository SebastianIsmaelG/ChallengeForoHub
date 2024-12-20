package app.aluracursos.challenge_forohub.domain.topic;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    boolean existsByTitulo(@NotBlank String titulo);

    boolean existsByMensaje(String mensaje);

    @Query("SELECT t FROM Topico t WHERE t.titulo ILIKE CONCAT('%', :busqueda, '%') " +
            "OR t.mensaje ILIKE CONCAT('%', :busqueda, '%')")
    List<Topic> buscarPorPalabraClave(String busqueda);
}
