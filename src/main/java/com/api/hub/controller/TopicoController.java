package com.api.hub.controller;


import com.api.hub.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService; // Servicio para operaciones de tópicos

    @Autowired
    private TopicosRepository topicoRepository; // Repositorio para acceso a datos de tópicos

    // Endpoint para registrar un nuevo tópico
    @PostMapping
    @Transactional
    public ResponseEntity<DatosListarTopicos> registrarTopico(@RequestBody @Valid DatosInputTopico datos, UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoService.registrarTopico(datos); // Llama al servicio para registrar el tópico
        DatosListarTopicos datosListarTopico = new DatosListarTopicos(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getAutor().getNombre(), topico.getCurso().getNombre(), topico.getFechaCreacion());
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri(); // Construye la URI del nuevo recurso
        return ResponseEntity.created(uri).body(datosListarTopico); // Retorna respuesta de creación con detalles del tópico
    }

    // Endpoint para listar tópicos activos
    @GetMapping
    public ResponseEntity<Page<DatosListarTopicos>> listarTopicosActivos(
            @PageableDefault(size = 10, sort = {"fechaCreacion"}, direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).map(DatosListarTopicos::new));
    }

    // Endpoint para buscar tópicos por curso y año
    @GetMapping("/buscar")
    public ResponseEntity<Page<DatosListarTopicos>> buscarPorCursoYAnio(
            @RequestParam String nombreCurso,
            @RequestParam int year,
            @PageableDefault(size = 10, sort = {"fechaCreacion"}, direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<Topico> topicos = topicoService.buscarTopicosPorCursoYAnio(nombreCurso, year, paginacion); // Busca tópicos por curso y año
        return ResponseEntity.ok(topicos.map(DatosListarTopicos::new)); // Retorna los resultados paginados
    }

    // Endpoint para obtener un tópico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> topico = topicoService.buscarTopicoPorId(id); // Busca un tópico por su ID
        if (!topico.isPresent()) {
            return ResponseEntity.notFound().build(); // Si no se encuentra el tópico, devuelve respuesta de no encontrado
        }
        DatosDetalleTopico datosDetalleTopico = new DatosDetalleTopico(
                topico.get().getId(),
                topico.get().getTitulo(),
                topico.get().getMensaje(),
                topico.get().getFechaCreacion(),
                topico.get().getStatus(),
                topico.get().getAutor().getNombre(),
                topico.get().getCurso().getNombre()
        );
        return ResponseEntity.ok(datosDetalleTopico); // Retorna detalles del tópico encontrado
    }

    // Endpoint para actualizar un tópico por su ID
    @PutMapping("/{id}")
    public ResponseEntity<DatosListarTopicos> actualizarTopico(@PathVariable Long id, @RequestBody DatosInputTopico datos) {
        Topico topicoActualizado = topicoService.actualizarTopico(id, datos); // Actualiza un tópico por su ID con los datos proporcionados
        DatosListarTopicos datosActualizados = new DatosListarTopicos(topicoActualizado.getId(), topicoActualizado.getTitulo(), topicoActualizado.getMensaje(), topicoActualizado.getAutor().getNombre(), topicoActualizado.getCurso().getNombre(), topicoActualizado.getFechaCreacion());
        return ResponseEntity.ok(datosActualizados); // Retorna los detalles actualizados del tópico
    }

    // Endpoint para desactivar un tópico por su ID
    @DeleteMapping("/desactivar/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id); // Obtiene una referencia al tópico por su ID
        topico.eliminar(); // Desactiva el tópico
        return ResponseEntity.noContent().build(); // Retorna respuesta sin contenido
    }

    // Endpoint para eliminar permanentemente un tópico por su ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopicoBD(@PathVariable Long id) {
        topicoService.eliminarTopicoPorId(id); // Elimina permanentemente un tópico por su ID
        return ResponseEntity.noContent().build(); // Retorna respuesta sin contenido
    }
}