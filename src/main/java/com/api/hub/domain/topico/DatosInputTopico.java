package com.api.hub.domain.topico;

import jakarta.validation.constraints.NotBlank;


public record DatosInputTopico(

        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        String idUsuario,
        String nombreCurso
) {
}
