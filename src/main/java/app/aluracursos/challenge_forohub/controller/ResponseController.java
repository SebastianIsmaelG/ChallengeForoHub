package app.aluracursos.challenge_forohub.controller;

import app.aluracursos.challenge_forohub.domain.response.*;
import app.aluracursos.challenge_forohub.domain.topic.Topic;
import app.aluracursos.challenge_forohub.domain.topic.TopicRepository;
import app.aluracursos.challenge_forohub.domain.user.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos/{topicoId}")
@SecurityRequirement(name = "bearer-key")
public class ResponseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ResponsesRepository responsesRepository;

    @Autowired
    private ResponsesService validar;

    @PostMapping
    public ResponseEntity<DataResponseConfirm> nuevaRespuesta(@RequestBody @Valid DataResponse datos,
                                                              UriComponentsBuilder uriComponentsBuilder){
        Responses responses = new Responses(datos, userRepository, topicRepository);
        responsesRepository.save(responses);
        URI url = uriComponentsBuilder.path("/topicos/{topicoId}/{respuestaId}").buildAndExpand(responses.getTopic().getId(), responses.getId()).toUri();
        return ResponseEntity.created(url).body(new DataResponseConfirm(
                responses, responsesRepository, userRepository, topicRepository));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@RequestBody DataResponseDelete datos, @PathVariable Long id){
        if (!responsesRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        Responses responses = responsesRepository.getReferenceById(id);
        responsesRepository.delete(responses);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DataLimitResponse> actualizarSolucionRespuesta(@RequestBody DataSolutionsResponse datos){
        Topic topic = topicRepository.getReferenceById(datos.topicoId());
        if (topic.getStatus() == "Cerrado"){
            System.out.println("El tópico ya se encuentra cerrado, pero puede elegir más de una solución.");
        }
        Responses responses = responsesRepository.getReferenceById(datos.respuestaId());
        if (!topicRepository.getReferenceById(datos.topicoId()).getUser().getId().equals(datos.usuarioId())) {
            return ResponseEntity.badRequest().build();
        }
        responses.actualizarSolucion();
        topic.actualizarStatus(responses.getSolucion());
        return ResponseEntity.ok(new DataLimitResponse(responses));
    }

}
