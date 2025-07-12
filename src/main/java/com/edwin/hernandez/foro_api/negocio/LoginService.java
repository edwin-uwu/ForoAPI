package com.edwin.hernandez.foro_api.negocio;

import com.edwin.hernandez.foro_api.infra.TokenJWTDTO;
import com.edwin.hernandez.foro_api.infra.TokenService;
import com.edwin.hernandez.foro_api.modelos.DTOs.LoginDTO;
import com.edwin.hernandez.foro_api.modelos.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager manager;

    public TokenJWTDTO iniciarSesion(LoginDTO datos){
        var autenticacionToken =new UsernamePasswordAuthenticationToken(datos.usuario(),datos.contrasena());
        var autenticacion =manager.authenticate(autenticacionToken);

        var tokenJWT =tokenService.generarToken((Usuario) autenticacion.getPrincipal());

        return new TokenJWTDTO(tokenJWT);
    }
}
