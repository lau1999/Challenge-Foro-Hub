package com.api.hub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosInputRespuesta(
        @NotBlank
        String mensaje,
        @NotNull
        String idTopico,
        @NotNull
        String idUsuario,
        @NotBlank
        String solucion
) {
}
