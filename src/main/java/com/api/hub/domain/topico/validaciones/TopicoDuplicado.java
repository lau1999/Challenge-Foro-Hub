package com.api.hub.domain.topico.validaciones;


import com.api.hub.domain.topico.DatosInputTopico;
import com.api.hub.domain.topico.TopicosRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidadorDeTopico{

    private final TopicosRepository topicoRepository;

    public TopicoDuplicado(TopicosRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }
    @Override
    public void validar(DatosInputTopico datos) {
        if (topicoRepository.findByTituloIgnoreCase(datos.titulo()) != null &&
                topicoRepository.findByMensajeIgnoreCase(datos.mensaje()) != null) {
            throw new ValidationException("Ya existe un tópico con el mismo título y mensaje");
        }
    }
}
