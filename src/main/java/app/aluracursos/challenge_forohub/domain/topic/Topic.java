package app.aluracursos.challenge_forohub.domain.topic;

import app.aluracursos.challenge_forohub.domain.response.Responses;
import app.aluracursos.challenge_forohub.domain.user.User;
import app.aluracursos.challenge_forohub.domain.user.UserRepository;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private LocalDateTime fechaDeCreacion;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Cursos curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Responses> responses = new ArrayList<>();


    public Topic(DataNewTopic datos, UserRepository userRepository) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaDeCreacion = LocalDateTime.now();
        this.status = "Abierto";
        this.user = userRepository.findById(datos.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + datos.usuarioId()));

        this.curso = datos.curso();
    }


    public void actualizar(@Valid DataUpdateTopic datosActualizar) {
        if (datosActualizar.titulo() != null) {
            this.titulo = datosActualizar.titulo();
        }
        if (datosActualizar.mensaje() != null) {
            this.mensaje = datosActualizar.mensaje();
        }
    }


    @Override
    public String toString() {
        return "Topico{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fechaDeCreacion=" + fechaDeCreacion +
                ", status=" + status +
                ", usuario=" + user.getNombre() +
                ", curso=" + curso +
                '}';
    }

    public void actualizarStatus(Boolean solucion) {
        if (solucion) {
            this.status = "Cerrado";
        }
    }
}


