package com.api.hub.controller;


import com.api.hub.domain.usuarios.DatosAutenticacionUsuario;
import com.api.hub.domain.usuarios.Usuario;
import com.api.hub.infra.security.DatosJWTToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.api.hub.infra.security.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación", description = "Operaciones de autenticación")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Iniciar sesión", description = "Permite iniciar sesión a un usuario")
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.correoElectronico(),
                datosAutenticacionUsuario.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        var JWTToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        String tipo = "Bearer";
        return ResponseEntity.ok(new DatosJWTToken(JWTToken, tipo));
    }
}