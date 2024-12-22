package app.aluracursos.challenge_forohub.controller;

import app.aluracursos.challenge_forohub.domain.response.ResponsesRepository;
import app.aluracursos.challenge_forohub.domain.topic.*;
import app.aluracursos.challenge_forohub.domain.user.UserRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponsesRepository responsesRepository;

    @Autowired
    private TopicResponse topicResponse;

    @PostMapping
    public ResponseEntity<DataConfirmTopic> enviarNuevoTopico(@RequestBody @Valid DataNewTopic dataNewTopic, UriComponentsBuilder uriComponentsBuilder){
        DataConfirmTopic dataConfirmTopic = topicResponse.postearTopico(dataNewTopic);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(dataConfirmTopic.id()).toUri();
        return ResponseEntity.created(url).body(dataConfirmTopic);
    }

    @GetMapping
    public ResponseEntity<Page<DataConfirmTopic>> listarTodosLosTopicos(@PageableDefault
                                                                            @SortDefault(sort = "status", direction = Sort.Direction.ASC)
                                                                            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                                                            Pageable pageable){
    return ResponseEntity.ok(topicRepository.findAll(pageable).map(DataConfirmTopic::new));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DataConfirmTopic> actualizarTopico(@PathVariable("id") Long id, @RequestBody @Valid DataUpdateTopic datosActualizar){
        DataConfirmTopic datosActualizados = topicResponse.actualizarTopico(id, datosActualizar);
        return ResponseEntity.ok(datosActualizados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicResponse.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/topicoPorId/{id}")
    public ResponseEntity<DataTopic> buscarTopicoPorId(@PathVariable Long id){
        return ResponseEntity.ok(topicResponse.topicoPorId(id));
    }

    @PostMapping("/buscar")
    public ResponseEntity<List<DataResultTopic>> buscarTopicoPorPalabraClave(@RequestBody
                                                                   @Schema(description = "Request para la búsqueda de tópicos") DataFindTopic datos){

        if (!userRepository.existsById(datos.usuarioId())){
            return ResponseEntity.notFound().build();
        }

        List<Topic> topics = topicRepository.buscarPorPalabraClave(datos.busqueda());

        if (topics.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<DataResultTopic> datosTopicos = topics
                .stream()
                .map(t -> new DataResultTopic(t, responsesRepository, userRepository))
                .toList();
        if (datosTopicos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(datosTopicos);
    }

}
