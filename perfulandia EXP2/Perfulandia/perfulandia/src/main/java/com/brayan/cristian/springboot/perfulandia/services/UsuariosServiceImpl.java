package com.brayan.cristian.springboot.perfulandia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brayan.cristian.springboot.perfulandia.entities.Usuarios;
import com.brayan.cristian.springboot.perfulandia.repositories.UsuariosRepository;

@Service
public class UsuariosServiceImpl implements UsuariosService{

    @Autowired
    private UsuariosRepository uRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuarios> findByAll() {
        return (List<Usuarios>) uRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuarios> findById(Long id) {
        return uRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuarios save(Usuarios unUsuario) {
        return uRepository.save(unUsuario);
    }

    @Override
    @Transactional
    public Optional<Usuarios> delete(Usuarios unUsuario) {
        Optional<Usuarios> usuarioOpcional = uRepository.findById(unUsuario.getId());
        usuarioOpcional.ifPresent(usuarioDb->{
            uRepository.delete(unUsuario);
        });
        return usuarioOpcional;
    }
}
