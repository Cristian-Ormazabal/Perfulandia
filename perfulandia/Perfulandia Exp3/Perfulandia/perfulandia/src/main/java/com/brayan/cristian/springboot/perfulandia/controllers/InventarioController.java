package com.brayan.cristian.springboot.perfulandia.controllers;

import java.util.Optional;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brayan.cristian.springboot.perfulandia.entities.Inventario;
import com.brayan.cristian.springboot.perfulandia.services.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Inventarios", description = "Operaciones relacionadas con inventarios")
@RestController
@RequestMapping("api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService iService;

    //Documentación FindByAll
    @Operation(summary = "Listar todos los inventarios", description = "Obtiene una lista de todos los inventarios registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de inventarios obtenida correctamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class)))
    @GetMapping
    public List<Inventario> List(){
        return iService.findByAll();
       
    }

    //Documentación FindById
    @Operation(summary = "Obtener un inventario por ID", description = "Obtiene los detalles de un inventario específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario encontrado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Inventario> inventarioOpcional = iService.findById(id);
        if (inventarioOpcional.isPresent()){
            return ResponseEntity.ok(inventarioOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    //Documentación Post
    @Operation(summary = "Crear un nuevo inventario", description = "Registra un nuevo inventario en el sistema.")
    @ApiResponse(responseCode = "201", description = "Inventario creado correctamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class)))
    @PostMapping
    public ResponseEntity<Inventario>crear(@RequestBody Inventario unInventario){
        return ResponseEntity.status(HttpStatus.CREATED).body(iService.save(unInventario));

    }
    
    //Documentación Put
    @Operation(summary = "Modificar un inventario existente", description = "Actualiza los detalles de un inventario existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario modificado correctamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Inventario unInventario){
        Optional<Inventario> inventarioOptional = iService.findById(id);
        if (inventarioOptional.isPresent()){
            Inventario inventarioExistente = inventarioOptional.get();
            inventarioExistente.setNombre(unInventario.getNombre());
            inventarioExistente.setDescripcion(unInventario.getDescripcion());
            inventarioExistente.setPrecio(unInventario.getPrecio());
            Inventario productomodificado = iService.save(inventarioExistente);
            return ResponseEntity.ok(productomodificado);
        }
        return ResponseEntity.notFound().build();
    }

    //Documentación Delete
    @Operation(summary = "Eliminar un inventario por ID", description = "Elimina un inventario del sistema por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario eliminado correctamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Inventario unInventario = new Inventario();
        unInventario.setId(id);
        Optional<Inventario> invenOptional = iService.delete(unInventario);
        if (invenOptional.isPresent()) {
            return ResponseEntity.ok(invenOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    // borrar, efecto de esta linea para hacer un pull x2 
}
