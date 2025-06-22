package com.brayan.cristian.springboot.perfulandia.controllers;

import java.util.List;
import java.util.Optional;

import com.brayan.cristian.springboot.perfulandia.entities.Usuarios;
import com.brayan.cristian.springboot.perfulandia.services.UsuariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("api/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService uService;

    //Documentación FindbyAll
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Usuarios.class)))
    @GetMapping
    public List<Usuarios> List() {
        return uService.findByAll();
    }

    //Documentación FindById
    @Operation(summary = "Obtener un usuario por ID", description = "Obtiene los detalles de un usuario específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Usuarios> usuarioOpcional = uService.findById(id);
        if (usuarioOpcional.isPresent()) {
            return ResponseEntity.ok(usuarioOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //Documentación Post
    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema.")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Usuarios.class)))
    @PostMapping
    public ResponseEntity<Usuarios> crear(@RequestBody Usuarios unUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(uService.save(unUsuario));
    }
    
    //Documentación Put
    @Operation(summary = "Modificar un usuario existente", description = "Actualiza los detalles de un usuario existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario modificado correctamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Usuarios unUsuario) {
        Optional<Usuarios> usuarioOpcional = uService.findById(id);
        if (usuarioOpcional.isPresent()) {
            Usuarios usuarioExistente = usuarioOpcional.get();
            usuarioExistente.setNombre(unUsuario.getNombre());
            usuarioExistente.setCorreo(unUsuario.getCorreo());
            usuarioExistente.setDireccionEnvio(unUsuario.getDireccionEnvio());
            Usuarios usuarioModificado = uService.save(usuarioExistente);
            return ResponseEntity.ok(usuarioModificado);
        }
        return ResponseEntity.notFound().build();
    }

    //Documentación Delete
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario del sistema por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Usuarios unUsuario = new Usuarios();
        unUsuario.setId(id);
        Optional<Usuarios> usuarioOpcional = uService.delete(unUsuario);
        if (usuarioOpcional.isPresent()) {
            return ResponseEntity.ok(usuarioOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}