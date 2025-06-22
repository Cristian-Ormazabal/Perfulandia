package com.brayan.cristian.springboot.perfulandia.services;

import java.util.List;
import java.util.Optional;

import com.brayan.cristian.springboot.perfulandia.entities.Inventario;

public interface InventarioService {
    List<Inventario> findByAll();
    Optional<Inventario> findById(Long id);
    Inventario save(Inventario unInventario);
    Optional<Inventario> delete(Inventario unInventario);

}
