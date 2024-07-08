package com.api.hub.domain.topico.validaciones;


import com.api.hub.domain.topico.DatosInputTopico;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class CamposObligatorios implements ValidadorDeTopico{

    @Override
    public void validar(DatosInputTopico datos) {
        if (datos.titulo() == null || datos.titulo().isEmpty()) {
            throw new ValidationException("El título es obligatorio");
        }
        if (datos.mensaje() == null || datos.mensaje().isEmpty()) {
            throw new ValidationException("El mensaje es obligatorio");
        }
        if (datos.idUsuario() == null) {
            throw new ValidationException("El usuario es obligatorio");
        }
        if (datos.nombreCurso() == null) {
            throw new ValidationException("El curso es obligatorio");
        }
    }
}

