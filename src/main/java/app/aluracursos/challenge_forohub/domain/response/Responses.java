package app.aluracursos.challenge_forohub.domain.response;

import app.aluracursos.challenge_forohub.domain.topic.Topic;
import app.aluracursos.challenge_forohub.domain.topic.TopicRepository;
import app.aluracursos.challenge_forohub.domain.user.User;
import app.aluracursos.challenge_forohub.domain.user.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@SecurityRequirement(name = "bearer-key")
public class Responses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User user;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topic topic;
    
    private String mensaje;

    private LocalDateTime fechaDeCreacion;

    private Boolean solucion;

    public Responses(@Valid DataResponse datos, UserRepository uRepository, TopicRepository tRepository) {
        this.user = uRepository.findById(datos.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + datos.usuarioId()));
        this.topic = tRepository.findById(datos.topicoId())
                .orElseThrow(() -> new IllegalArgumentException("Topico no encontrado con el ID: " + datos.topicoId()));

        this.nombre = uRepository.getReferenceById(datos.usuarioId()).getNombre();

        this.mensaje = datos.mensaje();
        this.fechaDeCreacion = LocalDateTime.now();
        this.solucion = false;


    }

    public void actualizarSolucion() {
        this.solucion = true;
    }
}
