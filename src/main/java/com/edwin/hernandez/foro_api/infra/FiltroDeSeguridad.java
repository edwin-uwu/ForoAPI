package com.edwin.hernandez.foro_api.infra;

import com.edwin.hernandez.foro_api.dominio.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroDeSeguridad extends OncePerRequestFilter {
    @Autowired
    private UsuarioRepository usuarioPerository;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);
        if(tokenJWT != null)
        {
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = usuarioPerository.findByUsuario(subject);
            var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Usuario logueado");
        }

        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var autorizacionHeader = request.getHeader("Authorization");
        if(autorizacionHeader != null){
            return autorizacionHeader.replace("Bearer ","");
        }
        return null;
    }
}
