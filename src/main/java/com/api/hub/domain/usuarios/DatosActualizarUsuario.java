package com.api.hub.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(
        @NotNull
        Long id,
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String correoElectronico,
        @NotBlank
        String contrasena
) {
}
