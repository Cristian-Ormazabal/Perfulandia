package com.brayan.cristian.springboot.perfulandia.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brayan.cristian.springboot.perfulandia.entities.Envio;
import com.brayan.cristian.springboot.perfulandia.services.EnvioService;

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

@Tag(name = "Envios", description = "Operaciones relacionadas con envíos")
@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService eService;

    //Documentación FindbyAll
    @Operation(summary = "Listar todos los envíos", description = "Obtiene una lista de todos los envíos registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida correctamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Envio.class)))
    @GetMapping
    public List<Envio> List() {
        return eService.findByAll();
    }

    //Documentación FindById
    @Operation(summary = "Obtener un envío por ID", description = "Obtiene los detalles de un envío específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío encontrado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Envio.class))),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Envio> envioOpcional = eService.findById(id);
        if (envioOpcional.isPresent()) {
            return ResponseEntity.ok(envioOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //Documentación Post
    @Operation(summary = "Crear un nuevo envío", description = "Registra un nuevo envío en el sistema.")
    @ApiResponse(responseCode = "201", description = "Envío creado correctamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Envio.class)))
    @PostMapping
    public ResponseEntity<Envio> crear(@RequestBody Envio unEnvio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eService.save(unEnvio));
    }
    
    //Documentación Put
    @Operation(summary = "Modificar un envío existente", description = "Actualiza los detalles de un envío existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío modificado correctamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Envio.class))),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Envio> modificar(@PathVariable Long id, @RequestBody Envio unEnvio) {
        Optional<Envio> envioOpcional = eService.findById(id);
        if (envioOpcional.isPresent()) {
            Envio envioExistente = envioOpcional.get();
            envioExistente.setNombre(unEnvio.getNombre());
            envioExistente.setDireccion(unEnvio.getDireccion());
            envioExistente.setFechaEnvio(unEnvio.getFechaEnvio());
            Envio envioModificado = eService.save(envioExistente);
            return ResponseEntity.ok(envioModificado);
        }
        return ResponseEntity.notFound().build();
    }

    //Documentación Delete
    @Operation(summary = "Eliminar un envío por ID", description = "Elimina un envío del sistema por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío eliminado correctamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Envio.class))),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Envio unEnvio = new Envio();
        unEnvio.setId(id);
        Optional<Envio> envioOpcional = eService.delete(unEnvio);
        if (envioOpcional.isPresent()) {
            return ResponseEntity.ok(envioOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
