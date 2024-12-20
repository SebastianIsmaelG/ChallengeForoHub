package app.aluracursos.challenge_forohub.domain.topic;

import java.time.LocalDateTime;

public record DataConfirmTopic(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        String status
) {
    public DataConfirmTopic(Topic topic) {
        this(topic.getId(), topic.getTitulo(), topic.getMensaje(), topic.getFechaDeCreacion(), topic.getStatus());
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String titulo() {
        return titulo;
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
