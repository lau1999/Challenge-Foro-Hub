package com.api.hub.controller;


import com.api.hub.domain.respuesta.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respúesta", description = "Operaciones de gestión de respuesta")
public class RespuestaController {

    @Autowired
    RespuestaService respuestaService;

    @Autowired
    RespuestaRepository respuestaRepository;

    // Método para registrar una nueva respuesta
    @PostMapping
    @Transactional
    public ResponseEntity<DatosListarRespuesta> registarTopico(@RequestBody @Valid DatosInputRespuesta datosEntradaRespuestas,
                                                               UriComponentsBuilder uriComponentsBuilder) {
        // Guardar la respuesta usando el servicio RespuestaService
        DatosRegistroRespuesta registroNuevo = respuestaService.guardarRespuesta(datosEntradaRespuestas);

        // Crear una entidad Respuesta para persistir en la base de datos
        Respuesta respuesta = new Respuesta(registroNuevo);
        respuestaRepository.save(respuesta);  // Guardar la respuesta en el repositorio

        // Construir DTO para la respuesta creada
        DatosListarRespuesta datosListarRespuesta = new DatosListarRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico().getTitulo(),
                respuesta.getAutor().getNombre().toString(),
                respuesta.getSolucion(),
                respuesta.getFechaCreacion()
        );

        // Construir la URI para la respuesta creada
        URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();

        // Devolver una respuesta HTTP 201 Created con el DTO de la respuesta creada
        return ResponseEntity.created(uri).body(datosListarRespuesta);
    }

    // Método para consultar todas las respuestas paginadas
    @GetMapping
    public ResponseEntity<?> consultar(
            @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.ASC) Pageable paginacion) {
        // Consultar todas las respuestas usando el Repositorio RespuestaRepository
        return ResponseEntity.ok(
                respuestaRepository.findAll(paginacion).stream()
                        .map(t -> new DatosListarRespuesta(
                                t.getId(),
                                t.getMensaje(),
                                t.getTopico().getTitulo(),
                                t.getAutor().getNombre().toString(),
                                t.getSolucion(),
                                t.getFechaCreacion()
                        ))
        );
    }

    // Método para retornar una respuesta específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DatosListarRespuesta> retornarUsuario(@PathVariable Long id) {
        // Buscar la respuesta por ID en el Repositorio RespuestaRepository
        Optional<Respuesta> respuesta = Optional.ofNullable(respuestaRepository.getReferenceById(id));

        // Verificar si la respuesta existe y devolverla si es así, de lo contrario devolver notFound()
        if (!respuesta.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Construir DTO para la respuesta encontrada
        DatosListarRespuesta datosListarUsuario = new DatosListarRespuesta(
                respuesta.get().getId(),
                respuesta.get().getMensaje(),
                respuesta.get().getTopico().getTitulo(),
                respuesta.get().getAutor().getNombre(),
                respuesta.get().getSolucion(),
                respuesta.get().getFechaCreacion()
        );

        // Devolver una respuesta HTTP 200 OK con el DTO de la respuesta encontrada
        return ResponseEntity.ok(datosListarUsuario);
    }

    // Método para actualizar una respuesta existente por su ID
    @PutMapping("/{id}")
    public ResponseEntity<DatosListarRespuesta> actualizarTopico(@PathVariable Long id, @RequestBody DatosActualizarRespuesta datos) {
        // Actualizar la respuesta usando el servicio RespuestaService
        Respuesta respuestaActualizado = respuestaService.actualizarRespuesta(id, datos);

        // Construir DTO para la respuesta actualizada
        DatosListarRespuesta datosActualizados = new DatosListarRespuesta(
                respuestaActualizado.getId(),
                respuestaActualizado.getMensaje(),
                respuestaActualizado.getTopico().getTitulo(),
                respuestaActualizado.getAutor().getNombre(),
                respuestaActualizado.getSolucion(),
                respuestaActualizado.getFechaCreacion()
        );

        // Devolver una respuesta HTTP 200 OK con el DTO de la respuesta actualizada
        return ResponseEntity.ok(datosActualizados);
    }

    // Método para eliminar una respuesta por su ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        // Buscar la respuesta por ID en el Repositorio RespuestaRepository
        Optional<Respuesta> respuesta = respuestaRepository.findById(id);

        // Verificar si la respuesta existe y lanzar una excepción si no se encuentra
        if (!respuesta.isPresent()) {
            throw new ValidationException("El ID de la respuesta no fue encontrado");
        }

        // Eliminar la respuesta del Repositorio RespuestaRepository
        respuestaRepository.deleteById(id);

        // Devolver una respuesta HTTP 204 No Content indicando que la respuesta fue eliminada con éxito
        return ResponseEntity.noContent().build();
    }

}
