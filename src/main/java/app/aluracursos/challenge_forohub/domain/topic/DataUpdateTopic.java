package app.aluracursos.challenge_forohub.domain.topic;

import jakarta.validation.constraints.NotNull;

public record DataUpdateTopic(
        @NotNull
        Long topicoId,

        @NotNull
        Long usuarioId,

        String titulo,

        String mensaje
) {
    public DataUpdateTopic(Long topicoId, Long usuarioId, String titulo, String mensaje) {
        this.topicoId = topicoId;
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.mensaje = mensaje;
    }
}
