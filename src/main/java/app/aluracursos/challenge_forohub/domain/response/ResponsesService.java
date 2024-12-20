package app.aluracursos.challenge_forohub.domain.response;

import app.aluracursos.challenge_forohub.domain.topic.TopicRepository;
import app.aluracursos.challenge_forohub.domain.user.UserRepository;
import app.aluracursos.challenge_forohub.infra.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponsesService {

    @Autowired
    private ResponsesRepository ResponsesRepository;

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private TopicRepository tRepository;


    public DataResponseConfirm crearNuevaRespuesta(DataResponse data, UserRepository uRepository, TopicRepository tRepository){
        Responses responses = new Responses(data, uRepository, tRepository);
        if (!uRepository.existsById(data.usuarioId())){
            throw new ValidacionException("El id de usuario ingresado no existe.");
        }
        if (!tRepository.existsById(data.topicoId())){
            throw new ValidacionException("El id de tópico ingresado no existe.");
        }
        ResponsesRepository.save(responses);
        return new DataResponseConfirm(responses, ResponsesRepository, uRepository, tRepository);
    }
}
