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

    // Lista de inventarios para pruebas
    List<Inventario> iLista = new ArrayList<Inventario>();

    // Prueba unitaria FindByAll
    @Test
    public void findByAllTest() {
        Mockito.when(inventarioRepository.findAll()).thenReturn(iLista);
        List<Inventario> respuesta = inventarioService.findByAll();
        assertEquals(3, respuesta.size());
        verify(inventarioRepository, times(1)).findAll();
    }

    // Prueba unitaria Save
    @Test
    public void saveInventarioTest() {
        Inventario nuevoInventario = new Inventario(null, "Nuevo Producto", "100ml", 75000);
        Inventario inventarioGuardado = new Inventario(4L, "Nuevo Producto", "100ml", 75000);

        Mockito.when(inventarioRepository.save(nuevoInventario)).thenReturn(inventarioGuardado);

        Inventario resultado = inventarioService.save(nuevoInventario);

        assertNotNull(resultado);
        assertEquals(4L, resultado.getId());
        assertEquals("Nuevo Producto", resultado.getNombre());
        verify(inventarioRepository, times(1)).save(nuevoInventario);
    }

    // Prueba unitaria Delete
    @Test
    public void deleteInventarioTest() {
        Inventario inventarioAEliminar = new Inventario(1L, "Producto 1", "30ml", 25000);
        
        Mockito.when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioAEliminar));

        inventarioService.delete(inventarioAEliminar);

        verify(inventarioRepository, times(1)).delete(inventarioAEliminar);
    }

    // Prueba unitaria FindById
    @Test
    public void findByIdTest() {
        Inventario inventarioBuscado = new Inventario(1L, "Producto 1", "30ml", 25000);

        Mockito.when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioBuscado));

        Optional<Inventario> resultado = inventarioService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Producto 1", resultado.get().getNombre());
        verify(inventarioRepository, times(1)).findById(1L);
    }

    
    // Método auxiliar para cargar datos de prueba
    public void cargarInventario() {
        Inventario inventario1 = new Inventario(Long.valueOf(1), "Producto 1", "30ml", 25000);
        Inventario inventario2 = new Inventario(Long.valueOf(2), "Producto 2", "50ml", 50000);
        Inventario inventario3 = new Inventario(Long.valueOf(3), "Producto 3", "125ml", 135000);

        iLista.add(inventario1);
        iLista.add(inventario2);
        iLista.add(inventario3);
    }

}
