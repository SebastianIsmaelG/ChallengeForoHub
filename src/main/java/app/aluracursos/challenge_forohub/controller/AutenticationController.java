package app.aluracursos.challenge_forohub.controller;

import app.aluracursos.challenge_forohub.domain.user.User;
import app.aluracursos.challenge_forohub.domain.user.DataUser;
import app.aluracursos.challenge_forohub.infra.security.DatosJWTToken;
import app.aluracursos.challenge_forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity authUser(@RequestBody @Valid DataUser dataUser){
        Authentication authToken = new UsernamePasswordAuthenticationToken(dataUser.email(), dataUser.contrasenha());

        var user = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((User) user.getPrincipal());

        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

}
