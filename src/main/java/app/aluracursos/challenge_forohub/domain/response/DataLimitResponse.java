package app.aluracursos.challenge_forohub.domain.response;

import java.time.LocalDateTime;

public record DataLimitResponse(
        Long respuestaId,
        String nombre,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        Boolean solucion
) {
    public DataLimitResponse(Responses responses) {
        this(responses.getId(), responses.getNombre(), responses.getMensaje(), responses.getFechaDeCreacion()
        , responses.getSolucion());
    }
}
