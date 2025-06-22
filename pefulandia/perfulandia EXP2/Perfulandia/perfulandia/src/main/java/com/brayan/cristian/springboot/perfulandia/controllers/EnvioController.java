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

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService eService;

    @GetMapping
    public List<Envio> List() {
        return eService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id) {
        Optional<Envio> envioOpcional = eService.findById(id);
        if (envioOpcional.isPresent()) {
            return ResponseEntity.ok(envioOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Envio> crear(@RequestBody Envio unEnvio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eService.save(unEnvio));
    }
    
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
