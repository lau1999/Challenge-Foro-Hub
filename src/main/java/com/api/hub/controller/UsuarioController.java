package com.api.hub.controller;


import com.api.hub.domain.usuarios.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "Operaciones de gestión de usuarios")
public class UsuarioController {

    // Inyecta el repositorio de Usuario, que es responsable de las operaciones CRUD en la base de datos.
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
// Método para registrar un nuevo usuario. Recibe datos del usuario en el cuerpo de la solicitud.
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        // Guarda el nuevo usuario en la base de datos.
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));

        // Crea un objeto de respuesta con los datos del usuario.
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(usuario.getNombre(), usuario.getCorreoElectronico());

        // Construye la URI del nuevo recurso creado.
        URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        // Devuelve una respuesta 201 Created con la URI del nuevo usuario y su información.
        return ResponseEntity.created(uri).body(datosRespuestaUsuario);
    }

    @GetMapping
// Método para obtener una lista paginada de usuarios.
    public ResponseEntity paginadoUsuarios() {
        // Transforma la lista de usuarios en una lista de objetos de datos de usuario.
        Stream<Object> usuarios = usuarioRepository.findAll().stream().map(u -> new DatosListarUsuario(u.getId(), u.getCorreoElectronico(), u.getNombre()));

        // Devuelve la lista de usuarios.
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
// Método para obtener un usuario por su ID.
    public ResponseEntity<?> retornarUsuarioPorId(@PathVariable Long id) {
        // Busca el usuario por su ID.
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.getReferenceById(id));

        // Si el usuario no existe, devuelve una respuesta 404 Not Found.
        if (!usuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Crea un objeto de datos de usuario y lo devuelve en la respuesta.
        DatosListarUsuario datosListarUsuario = new DatosListarUsuario(usuario.get().getId(), usuario.get().getCorreoElectronico(), usuario.get().getNombre());
        return ResponseEntity.ok(datosListarUsuario);
    }

    @PutMapping()
    @Transactional
// Método para actualizar un usuario existente.
    public ResponseEntity actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        // Obtiene el usuario por su ID.
        Usuario usuario = usuarioRepository.getReferenceById(datosActualizarUsuario.id());

        // Actualiza los datos del usuario.
        usuario.actualizarDatos(datosActualizarUsuario);

        // Devuelve una respuesta con los datos actualizados del usuario.
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario.getNombre(), usuario.getCorreoElectronico()));
    }

    @DeleteMapping("/{id}")
    @Transactional
// Método para eliminar un usuario por su ID.
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        // Busca el usuario por su ID.
        var usuario = usuarioRepository.findById(id);

        // Si el usuario existe, lo elimina y devuelve una respuesta 204 No Content.
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return ResponseEntity.noContent().build();
        }

        // Si el usuario no existe, devuelve una respuesta 404 Not Found.
        return ResponseEntity.notFound().build();
    }

}
