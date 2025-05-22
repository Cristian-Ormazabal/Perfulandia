package com.brayan.cristian.springboot.perfulandia.services;

import java.util.List;
import java.util.Optional;

import com.brayan.cristian.springboot.perfulandia.entities.Usuarios;

public interface UsuariosService {

    List<Usuarios> findByAll();
    Optional<Usuarios> findById(Long id);
    Usuarios save(Usuarios unUsuario);
    Optional<Usuarios> delete(Usuarios unUsuario);
}
