package com.brayan.cristian.springboot.perfulandia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brayan.cristian.springboot.perfulandia.entities.Inventario;
import com.brayan.cristian.springboot.perfulandia.repositories.InventarioRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioServiceImpl implements InventarioService {
    @Autowired
    private InventarioRepository iRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Inventario> findByAll() {

        return (List<Inventario>) iRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Inventario> delete(Inventario unInventario) {
        Optional<Inventario> inventarioOpcional = iRepository.findById(unInventario.getId());
        inventarioOpcional.ifPresent(inventarioDb->{
            iRepository.delete(unInventario);
        });
        return inventarioOpcional;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Inventario> findById(Long id) {
        return iRepository.findById(id);
    }

    @Override
    @Transactional
    public Inventario save(Inventario unInventario) {
        return iRepository.save(unInventario);
    }
}