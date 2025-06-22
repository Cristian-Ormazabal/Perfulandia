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

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService uService;

    @GetMapping
    public List<Usuarios> List() {
        return uService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Usuarios> usuarioOpcional = uService.findById(id);
        if (usuarioOpcional.isPresent()) {
            return ResponseEntity.ok(usuarioOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Usuarios> crear(@RequestBody Usuarios unUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(uService.save(unUsuario));
    }
    
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