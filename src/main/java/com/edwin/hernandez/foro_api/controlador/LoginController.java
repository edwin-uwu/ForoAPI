package com.edwin.hernandez.foro_api.controlador;

import com.edwin.hernandez.foro_api.modelos.DTOs.LoginDTO;
import com.edwin.hernandez.foro_api.negocio.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    public ResponseEntity login(@RequestBody @Valid LoginDTO datos){
        var token = loginService.iniciarSesion(datos);
        return ResponseEntity.ok(token);

    }
}
