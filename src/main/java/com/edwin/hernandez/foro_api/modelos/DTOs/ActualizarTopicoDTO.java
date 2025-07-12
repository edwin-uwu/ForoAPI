package com.edwin.hernandez.foro_api.modelos.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarTopicoDTO(
        @NotNull Long id,
        String titulo,
        String mensaje,
        String autor,
        String curso
) {
}
