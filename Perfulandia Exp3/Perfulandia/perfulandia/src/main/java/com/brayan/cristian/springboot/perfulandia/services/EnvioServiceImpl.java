package com.brayan.cristian.springboot.perfulandia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brayan.cristian.springboot.perfulandia.entities.Envio;
import com.brayan.cristian.springboot.perfulandia.repositories.EnvioRepository;

@Service
public class EnvioServiceImpl implements EnvioService {

    @Autowired
    private EnvioRepository eRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Envio> findByAll() {
        return (List<Envio>) eRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Envio> delete(Envio unEnvio) {
        Optional<Envio> envioOpcional = eRepository.findById(unEnvio.getId());
        envioOpcional.ifPresent(envioDb->{
            eRepository.delete(unEnvio);
        });
        return envioOpcional;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Envio> findById(Long id) {
        return eRepository.findById(id);
    }

    @Override
    @Transactional
    public Envio save(Envio unEnvio) {
        return eRepository.save(unEnvio);
    }

}
