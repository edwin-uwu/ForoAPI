package com.edwin.hernandez.foro_api.infra;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.edwin.hernandez.foro_api.modelos.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.springframework.security.config.Elements.JWT;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generarToken(Usuario usuario){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return com.auth0.jwt.JWT.create()
                    .withIssuer("API Edwin.med")
                    .withSubject(usuario.getUsuario())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algoritmo);
        }catch(JWTCreationException ex){
            throw new RuntimeException("Error al generar el token JWT",ex);
        }
    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return com.auth0.jwt.JWT.require(algoritmo)
                    .withIssuer("API Edwin.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("TokenJWT invalido o expirado");
        }
    }
}
