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

    // Prueba unitaria FindByAll
    @Test
    public void findByAllTest() {
        Mockito.when(usuariosRepository.findAll()).thenReturn(uLista);
        List<Usuarios> respuesta = usuariosService.findByAll();
        assertEquals(3, respuesta.size());
        verify(usuariosRepository, times(1)).findAll();
    }

    // Prueba unitaria Save
    @Test
    public void saveUsuarioTest() {
        Usuarios nuevoUsuario = new Usuarios(null, "Nuevo", "nuevo@mail.com", "Dirección 123");
        Usuarios usuarioGuardado = new Usuarios(4L, "Nuevo", "nuevo@mail.com", "Dirección 123");

        Mockito.when(usuariosRepository.save(nuevoUsuario)).thenReturn(usuarioGuardado);

        Usuarios resultado = usuariosService.save(nuevoUsuario);

        assertNotNull(resultado);
        assertEquals(4L, resultado.getId());
        assertEquals("Nuevo", resultado.getNombre());
        verify(usuariosRepository, times(1)).save(nuevoUsuario);
    }

    // Prueba unitaria Delete
    @Test
    public void deleteUsuarioTest() {
        Usuarios usuario = new Usuarios(1L, "Brayan", "br.ahumadar@duocuc.cl", "Calle Falsa 123");

        Mockito.when(usuariosRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        usuariosService.delete(usuario);
        
        verify(usuariosRepository, times(1)).delete(usuario);
    }

    // Prueba unitaria FindById
    @Test
    public void findByIdTest() {
        Usuarios usuario = new Usuarios(1L, "Cristian", "correofalso@gmail.com", "Avenida Siempre Viva 456");

        Mockito.when(usuariosRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuarios> resultado = usuariosService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Cristian", resultado.get().getNombre());
        verify(usuariosRepository, times(1)).findById(1L);
    }

    // Método auxiliar para cargar datos de prueba
    public void cargarUsuarios() {
        Usuarios usuario1 = new Usuarios(Long.valueOf(1), "Brayan", "br.ahumadar@duocuc.cl", "Calle Falsa 123");
        Usuarios usuario2 = new Usuarios(Long.valueOf(2), "Cristian", "correofalso@gmail.com", "Avenida Siempre Viva 456");
        Usuarios usuario3 = new Usuarios(Long.valueOf(3), "Pedrito", "pedrito.pro@gmail.com", "Calle Real 789");

        uLista.add(usuario1);
        uLista.add(usuario2);
        uLista.add(usuario3);
    }
}
