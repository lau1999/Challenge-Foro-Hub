package com.api.hub.domain.respuesta;


import com.api.hub.domain.topico.Topico;
import com.api.hub.domain.usuarios.Usuario;

public record DatosRegistroRespuesta(
        String mensaje,
        Topico topico,
        Usuario autor,
        String solucion
) {

}
