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

import com.brayan.cristian.springboot.perfulandia.entities.Usuarios;
import com.brayan.cristian.springboot.perfulandia.repositories.UsuariosRepository;

public class UsuariosServiceImplTest {

    @InjectMocks
    private UsuariosServiceImpl usuariosService;

    @Mock
    private UsuariosRepository usuariosRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.cargarUsuarios();
    }

    List<Usuarios> uLista = new ArrayList<Usuarios>();

    @Test
    public void findByAllTest() {
        Mockito.when(usuariosRepository.findAll()).thenReturn(uLista);
        List<Usuarios> respuesta = usuariosService.findByAll();
        assertEquals(3, respuesta.size());
        verify(usuariosRepository, times(1)).findAll();
    }

    public void cargarUsuarios() {
        Usuarios usuario1 = new Usuarios(Long.valueOf(1), "Brayan", "br.ahumadar@duocuc.cl", "Calle Falsa 123");
        Usuarios usuario2 = new Usuarios(Long.valueOf(2), "Cristian", "correofalso@gmail.com", "Avenida Siempre Viva 456");
        Usuarios usuario3 = new Usuarios(Long.valueOf(3), "Pedrito", "pedrito.pro@gmail.com", "Calle Real 789");

        uLista.add(usuario1);
        uLista.add(usuario2);
        uLista.add(usuario3);
    }
}
