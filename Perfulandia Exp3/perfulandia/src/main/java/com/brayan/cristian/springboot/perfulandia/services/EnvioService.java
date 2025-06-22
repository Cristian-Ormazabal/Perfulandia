package com.brayan.cristian.springboot.perfulandia.services;

import java.util.List;
import java.util.Optional;

import com.brayan.cristian.springboot.perfulandia.entities.Envio;

public interface EnvioService {

    List<Envio> findByAll();
    
    Optional<Envio> findById(Long id);

    Envio save(Envio unEnvio);

    Optional<Envio> delete(Envio unEnvio);

}
