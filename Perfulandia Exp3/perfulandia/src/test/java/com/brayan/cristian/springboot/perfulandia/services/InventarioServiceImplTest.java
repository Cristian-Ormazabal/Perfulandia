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

import com.brayan.cristian.springboot.perfulandia.entities.Inventario;
import com.brayan.cristian.springboot.perfulandia.repositories.InventarioRepository;


public class InventarioServiceImplTest {

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    @Mock
    private InventarioRepository inventarioRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.cargarInventario();
    }

    List<Inventario> iLista = new ArrayList<Inventario>();

    @Test
    public void findByAllTest() {
        Mockito.when(inventarioRepository.findAll()).thenReturn(iLista);
        List<Inventario> respuesta = inventarioService.findByAll();
        assertEquals(3, respuesta.size());
        verify(inventarioRepository, times(1)).findAll();
    }

    public void cargarInventario() {
        Inventario inventario1 = new Inventario(Long.valueOf(1), "Producto 1", "30ml", 25000);
        Inventario inventario2 = new Inventario(Long.valueOf(2), "Producto 2", "50ml", 50000);
        Inventario inventario3 = new Inventario(Long.valueOf(3), "Producto 3", "125ml", 135000);

        iLista.add(inventario1);
        iLista.add(inventario2);
        iLista.add(inventario3);
    }

}
