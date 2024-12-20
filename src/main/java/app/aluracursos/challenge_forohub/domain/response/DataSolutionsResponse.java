package app.aluracursos.challenge_forohub.domain.response;

public record DataSolutionsResponse(
        Long usuarioId,
        Long topicoId,
        Long respuestaId,
        Boolean solucion
) {
}
