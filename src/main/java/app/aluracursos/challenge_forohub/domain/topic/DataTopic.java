package app.aluracursos.challenge_forohub.domain.topic;

import app.aluracursos.challenge_forohub.domain.response.DataLimitResponse;
import app.aluracursos.challenge_forohub.domain.response.ResponsesRepository;
import app.aluracursos.challenge_forohub.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public record DataTopic(
        Long topicoId,

        String titulo,

        String mensaje,

        LocalDateTime fechaDeCreacion,

        Long usuarioId,

        String nombre,

        Cursos curso,

        List<DataLimitResponse> respuestas
) {
    public DataTopic(Topic topic, UserRepository uRepository, ResponsesRepository rRepository) {
        this(topic.getId(), topic.getTitulo(), topic.getMensaje(), topic.getFechaDeCreacion(), topic.getUser().getId()
                , uRepository.getReferenceById(topic.getUser().getId()).getNombre(),
                topic.getCurso(),
                rRepository.findAllByTopicoId(topic.getId()).stream().map(r -> new DataLimitResponse(r.getId(), uRepository.getReferenceById
                        (topic.getUser().getId()).getNombre(), r.getMensaje(), r.getFechaDeCreacion(), r.getSolucion())).toList());
    }

}
