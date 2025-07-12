package com.edwin.hernandez.foro_api.modelos.DTOs;

import com.edwin.hernandez.foro_api.modelos.Topico;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ListaTopicos(
        Long id,
        String titulo,
        String mensaje,
        String auto,
        String curso,
        LocalDateTime fechaCreacion
) {
    public ListaTopicos(Topico datos){
        this(datos.getId(),datos.getTitulo(),datos.getMensaje(),datos.getAutor(),datos.getCurso(),datos.getFechaCreacion());
    }
}
