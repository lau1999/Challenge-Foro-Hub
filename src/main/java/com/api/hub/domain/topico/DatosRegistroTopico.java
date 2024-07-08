package com.api.hub.domain.topico;


import com.api.hub.domain.curso.Curso;
import com.api.hub.domain.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public record DatosRegistroTopico(
        String titulo,
        String mensaje,
        @JsonManagedReference
        Usuario idUsuario,
        Curso nombreCurso
) {
}
