package br.com.moreproductive.config;

import br.com.moreproductive.dto.JWTUserDataDTO;
import br.com.moreproductive.entities.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class JwtService {

    private String senha = "TiraIssoDaqui";

    public String gerarToken(Usuario usuario){

        Algorithm algoritmo = Algorithm.HMAC256(senha);

        return JWT.create()
                .withClaim("userID", usuario.getId())
                .withSubject(usuario.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(60000))
                .withIssuedAt(Instant.now())
                .sign(algoritmo);
    }

    public Optional<JWTUserDataDTO> validarToken(String token) {
        try {

            Algorithm algoritmo = Algorithm.HMAC256(senha);
            DecodedJWT decode = JWT.require(algoritmo)
                    .build().verify(token);

            var userData = new JWTUserDataDTO(
                    decode.getClaim("userID").asInt(),
                    decode.getSubject(),
                    decode.getIssuedAtAsInstant()
            );

            return Optional.of(userData);

        } catch (JWTDecodeException e) {
            return Optional.empty();
        }
    }
}
