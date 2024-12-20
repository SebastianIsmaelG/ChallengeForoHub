package app.aluracursos.challenge_forohub.domain.response;

import app.aluracursos.challenge_forohub.domain.topic.TopicRepository;
import app.aluracursos.challenge_forohub.domain.user.UserRepository;

import java.time.LocalDateTime;

public record DataResponseConfirm(
        Long id,

        String nombreUsuario,

        String tituloTopico,

        String mensaje,

        LocalDateTime fechaDeCreacion
) {
    public DataResponseConfirm(Responses responses, ResponsesRepository repository,
                               UserRepository uRepository, TopicRepository tRepository) {
        this(responses.getId(), uRepository.getReferenceById(repository.getReferenceById(responses.getId()).getUser().getId()).getNombre()
                , tRepository.getReferenceById(repository.getReferenceById(responses.getId()).getTopic().getId()).getTitulo()
                , responses.getMensaje(), responses.getFechaDeCreacion());
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String nombreUsuario() {
        return nombreUsuario;
    }

    @Override
    public String tituloTopico() {
        return tituloTopico;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public LocalDateTime fechaDeCreacion() {
        return fechaDeCreacion;
    }
}
