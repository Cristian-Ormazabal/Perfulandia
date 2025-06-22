package com.brayan.cristian.springboot.perfulandia.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.brayan.cristian.springboot.perfulandia.entities.Envio;
import com.brayan.cristian.springboot.perfulandia.repositories.EnvioRepository;

public class EnvioServiceImplTest {

    @InjectMocks
    private EnvioServiceImpl envioService;
    
    @Mock
    private EnvioRepository envioRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.cargarEnvio();
    }

    List<Envio> eLista = new ArrayList<Envio>();

    @Test
    public void findByAllTest() {
        Mockito.when(envioRepository.findAll()).thenReturn(eLista);
        List<Envio> respuesta = envioService.findByAll();
        assertEquals(3, respuesta.size());
        verify(envioRepository, times(1)).findAll();
    }

    public void cargarEnvio() {
        Envio envio1 = new Envio(Long.valueOf(1), "Envio 1", null, "Enviado");
        Envio envio2 = new Envio(Long.valueOf(2), "Envio 2", null, "Enviado");
        Envio envio3 = new Envio(Long.valueOf(3), "Envio 3", null, "Enviado");

        eLista.add(envio1);
        eLista.add(envio2);
        eLista.add(envio3);
    }
}