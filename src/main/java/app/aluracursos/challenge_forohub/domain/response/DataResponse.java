package app.aluracursos.challenge_forohub.domain.response;

public record DataResponse(
        Long usuarioId,

        Long topicoId,

        String mensaje
) {

    public DataResponse(Responses responses) {
        this(responses.getUser().getId(),
        responses.getTopic().getId(),
         responses.getMensaje());
    }
}
