package com.brayan.cristian.springboot.perfulandia.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // Prueba unitaria FindByAll
    @Test
    public void findByAllTest() {
        Mockito.when(envioRepository.findAll()).thenReturn(eLista);
        List<Envio> respuesta = envioService.findByAll();
        assertEquals(3, respuesta.size());
        verify(envioRepository, times(1)).findAll();
    }

    // Prueba unitaria Save
    @Test
    public void saveEnvioTest() {
        Envio nuevoEnvio = new Envio(null, "Nuevo Envio", null, "Enviado");
        Envio envioGuardado = new Envio(4L, "Nuevo Envio", null, "Enviado");

        Mockito.when(envioRepository.save(nuevoEnvio)).thenReturn(envioGuardado);

        Envio resultado = envioService.save(nuevoEnvio);

        assertNotNull(resultado);
        assertEquals(4L, resultado.getId());
        assertEquals("Nuevo Envio", resultado.getNombre());
        verify(envioRepository, times(1)).save(nuevoEnvio);
    }

    // Prueba unitaria Delete
    @Test
    public void deleteEnvioTest() {
        Envio envioAEliminar = new Envio(1L, "Envio 1", null, "Enviado");
        Mockito.when(envioRepository.findById(1L)).thenReturn(Optional.of(envioAEliminar));
        
        envioService.delete(envioAEliminar);
        
        verify(envioRepository, times(1)).delete(envioAEliminar);
    }

    // Prueba unitaria FindById
    @Test
    public void findByIdTest() {
        Envio envioEsperado = new Envio(1L, "Envio 1", null, "Enviado");
        Mockito.when(envioRepository.findById(1L)).thenReturn(Optional.of(envioEsperado));
        
        Optional<Envio> resultado = envioService.findById(1L);
        
        assertTrue(resultado.isPresent());
        assertEquals(envioEsperado.getId(), resultado.get().getId());
        verify(envioRepository, times(1)).findById(1L);
    }

    // Método auxiliar para cargar datos de prueba
    public void cargarEnvio() {
        Envio envio1 = new Envio(Long.valueOf(1), "Envio 1", null, "Enviado");
        Envio envio2 = new Envio(Long.valueOf(2), "Envio 2", null, "Enviado");
        Envio envio3 = new Envio(Long.valueOf(3), "Envio 3", null, "Enviado");

        eLista.add(envio1);
        eLista.add(envio2);
        eLista.add(envio3);
    }
}