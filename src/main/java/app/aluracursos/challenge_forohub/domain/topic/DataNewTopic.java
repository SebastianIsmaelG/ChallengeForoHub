package app.aluracursos.challenge_forohub.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataNewTopic(
        @NotNull
        Long usuarioId,

        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotBlank
        String status,

        @NotNull
        Cursos curso
) {
}
