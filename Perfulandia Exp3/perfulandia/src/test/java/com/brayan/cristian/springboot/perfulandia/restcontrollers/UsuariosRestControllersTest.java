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

import com.brayan.cristian.springboot.perfulandia.entities.Usuarios;
import com.brayan.cristian.springboot.perfulandia.services.UsuariosServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuariosRestControllersTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuariosServiceImpl usuariosserviceimpl;

    private List<Usuarios> usuariosLista;

    @Test
    public void verUsuariosTest() throws Exception {
        when(usuariosserviceimpl.findByAll()).thenReturn(usuariosLista);
        mockmvc.perform(get("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void verunUsuarioTest() {
        Usuarios unUsuario = new Usuarios(1L, "Brayan", "br.ahumadar@duocuc.cl", "Calle Falsa 123");
        try {
            when(usuariosserviceimpl.findById(1L)).thenReturn(Optional.of(unUsuario));
            mockmvc.perform(get("/api/usuarios/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception ex) {
            fail("Error al obtener el usuario: " + ex.getMessage());
        }

    }

    @Test
    public void usuarioNoExisteTest() throws Exception {
        when(usuariosserviceimpl.findById(99L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/usuarios/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void crearUsuarioTest() throws Exception {
        Usuarios unUsuario = new Usuarios(1L, "Brayan", "br.ahumadar@duocuc.cl", "Calle Falsa 123");
        Usuarios otroUsuario = new Usuarios(4L, "Cristian", "cr.ormazabals@duocuc.cl", "Calle Verdadera 456");
        when(usuariosserviceimpl.save(any(Usuarios.class))).thenReturn(otroUsuario);
        mockmvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unUsuario)))
                .andExpect(status().isCreated());
    }


}
