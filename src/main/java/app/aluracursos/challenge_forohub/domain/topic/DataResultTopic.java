package app.aluracursos.challenge_forohub.domain.topic;

import app.aluracursos.challenge_forohub.domain.response.DataLimitResponse;
import app.aluracursos.challenge_forohub.domain.response.ResponsesRepository;
import app.aluracursos.challenge_forohub.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public record DataResultTopic(
        Long topicoId,

        String titulo,

        String mensaje,

        LocalDateTime fechaDeCreacion,

        Cursos curso,

        String Status,

        List<DataLimitResponse> respuestas
) {
    public DataResultTopic(Topic topic, ResponsesRepository rRepository, UserRepository uRepository) {
        this(topic.getId(), topic.getTitulo(), topic.getMensaje(), topic.getFechaDeCreacion(),
                topic.getCurso(), topic.getStatus(), rRepository.findAllByTopicoId(topic.getId()).stream().map(r -> new DataLimitResponse
                        (r.getId(), uRepository.getReferenceById
                        (topic.getUser().getId()).getNombre(), r.getMensaje(), r.getFechaDeCreacion(), r.getSolucion())).toList());
    }
}
