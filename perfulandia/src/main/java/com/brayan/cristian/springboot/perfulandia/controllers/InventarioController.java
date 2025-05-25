package com.brayan.cristian.springboot.perfulandia.controllers;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.brayan.cristian.springboot.perfulandia.entities.Inventario;
import com.brayan.cristian.springboot.perfulandia.services.InventarioService;


import java.util.List;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/inventario")
public class InventarioController {
    @Autowired
    private InventarioService iService;

    @GetMapping
    public List<Inventario> List(){
        return iService.findByAll();
       
    }
    
    @PostMapping
    public ResponseEntity<Inventario>crear(@RequestBody Inventario unInventario){
        return ResponseEntity.status(HttpStatus.CREATED).body(iService.save(unInventario));

    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalle(@PathVariable Long id){
        Optional<Inventario> inventarioOpcional = iService.findById(id);
        if (inventarioOpcional.isPresent()){
            return ResponseEntity.ok(inventarioOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


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
    
    // borrar, efecto de esta linea para hacer un pull
}
