package com.api.hub.domain.topico;

import java.time.LocalDateTime;

public record DatosListarTopicos(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String curso,
        LocalDateTime fechaCreacion


) {
    public DatosListarTopicos(Topico topico) {
        this(topico.getId(),topico.getTitulo(), topico.getMensaje().toString(),topico.getAutor().getNombre(),topico.getCurso().getNombre(),topico.getFechaCreacion());
    }
}
