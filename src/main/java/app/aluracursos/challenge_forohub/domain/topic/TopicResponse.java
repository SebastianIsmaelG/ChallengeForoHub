package app.aluracursos.challenge_forohub.domain.topic;

import app.aluracursos.challenge_forohub.domain.response.ResponsesRepository;
import app.aluracursos.challenge_forohub.domain.user.UserRepository;
import app.aluracursos.challenge_forohub.infra.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicResponse {

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private TopicRepository tRepository;

    @Autowired
    private ResponsesRepository rRepository;

    public DataConfirmTopic postearTopico(DataNewTopic dataNewTopic){
        if (!uRepository.existsById(dataNewTopic.usuarioId())) {
            throw new ValidacionException("El id de usuario ingresado no existe.");
        }
        if (tRepository.existsByTitulo(dataNewTopic.titulo())){
            throw new ValidacionException("Un tópico con el título ingresado ya existe.");
        }
        var topico = new Topic(dataNewTopic, uRepository);
        tRepository.save(topico);
        return new DataConfirmTopic(topico);
    }

    public DataConfirmTopic actualizarTopico(Long id, DataUpdateTopic datosActualizar){
        if (!tRepository.existsById(id)) {
            throw new ValidacionException("El id de tópico ingresado no existe.");
        }
        if (!uRepository.existsById(datosActualizar.usuarioId())){
            throw new ValidacionException("El id de usuario ingresado no existe.");
        }
        if (!tRepository.getReferenceById(id).getUser().getId().equals(datosActualizar.usuarioId())){
            throw new ValidacionException("Su id de usuario no tiene autorización para modificar este post.");
        }
        if (tRepository.existsByTitulo(datosActualizar.titulo())){
            if (tRepository.getReferenceById(id).getTitulo().equals(datosActualizar.titulo())) {
                throw new ValidacionException("El título actualizado es igual al título original.");
            } else {
            throw new ValidacionException("Un tópico con el título ingresado ya existe.");
            }
        }
        if (tRepository.existsByMensaje(datosActualizar.mensaje())){
            if (tRepository.getReferenceById(id).getMensaje().equals(datosActualizar.mensaje())) {
                throw new ValidacionException("El mensaje actualizado es igual al mensaje original.");
            } else {
                throw new ValidacionException("Un tópico con el mensaje ingresado ya existe.");
            }
        }
        Topic topic = tRepository.getReferenceById(id);
        topic.actualizar(datosActualizar);
        return new DataConfirmTopic(topic);
    }

    public void eliminarTopico(Long id){
        if (!tRepository.existsById(id)) {
           throw new ValidacionException("El id de tópico ingresado no existe.");
        }
        Topic topic = tRepository.getReferenceById(id);
        tRepository.deleteById(id);

    }

    public DataTopic topicoPorId(Long id){
        if (!tRepository.existsById(id)) {
           throw new ValidacionException("El id de tópico ingresado no existe");
        }
        return new DataTopic(tRepository.getReferenceById(id), uRepository, rRepository);
    }
}
