package com.edwin.hernandez.foro_api.dominio;

import com.edwin.hernandez.foro_api.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    UserDetails findByUsuario(String usuario);

}
