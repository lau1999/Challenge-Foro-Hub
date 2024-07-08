package com.api.hub.domain.usuarios;


import com.api.hub.domain.perfil.Perfil;
import jakarta.validation.constraints.Email;

import java.util.List;

public record DatosRegistroUsuario(
        String nombre,
        @Email
        String correoElectronico,
        String contrasena,
        List<Perfil> perfiles
) {
}
