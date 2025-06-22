package com.brayan.cristian.springboot.perfulandia.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.brayan.cristian.springboot.perfulandia.entities.Inventario;
import com.brayan.cristian.springboot.perfulandia.services.InventarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class InventarioRestControllersTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventarioServiceImpl inventarioserviceimpl;

    private List<Inventario> inventariosLista;

    @Test
    public void verInventariosTest() throws Exception {
        when(inventarioserviceimpl.findByAll()).thenReturn(inventariosLista);
        mockmvc.perform(get("/api/inventario")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void verunInventarioTest() {
        Inventario unInventario = new Inventario(1L, "Raulph Lauren", "50ml", 15000);
        try {
            when(inventarioserviceimpl.findById(1L)).thenReturn(Optional.of(unInventario));
            mockmvc.perform(get("/api/inventario/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
         catch (Exception ex) {
            fail("Error al obtener el inventario: " + ex.getMessage());
        }
    }

    @Test
    public void crearInventarioTest() throws Exception {
        Inventario unInventario = new Inventario(null, "Raulph Lauren", "50ml", 15000);
        Inventario otroInventario = new Inventario(4L, "Raulph Lauren", "100ml", 30000);
        when(inventarioserviceimpl.save(any(Inventario.class))).thenReturn(otroInventario);
        mockmvc.perform(post("/api/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unInventario)))
                .andExpect(status().isCreated());
    }

}
