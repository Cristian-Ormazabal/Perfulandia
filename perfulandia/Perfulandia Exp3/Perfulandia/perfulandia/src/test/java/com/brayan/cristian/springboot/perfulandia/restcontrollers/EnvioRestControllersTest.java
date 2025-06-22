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

import com.brayan.cristian.springboot.perfulandia.entities.Envio;
import com.brayan.cristian.springboot.perfulandia.services.EnvioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class EnvioRestControllersTest {

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EnvioServiceImpl envioserviceimpl;

    private List<Envio> enviosLista;

    @Test
    public void verEnviosTest() throws Exception {
        when(envioserviceimpl.findByAll()).thenReturn(enviosLista);
        mockmvc.perform(get("/api/envios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void verunEnvioTest() {
        Envio unEnvio = new Envio(1L, "Brayan", null, "Calle Falsa 123");
        try{
            when(envioserviceimpl.findById(1L)).thenReturn(Optional.of(unEnvio));
            mockmvc.perform(get("/api/envios/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
        catch (Exception ex) {
            fail("Error al obtener el envio: " + ex.getMessage());
        }
    }

    @Test
    public void envioNoExisteTest() throws Exception {
        when(envioserviceimpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/envios/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearEnvioTest() throws Exception {
        Envio unEnvio = new Envio(null, "Brayan", null, "Calle Falsa 123");
        Envio otroEnvio = new Envio(4L, "Brayan", null, "Calle Falsa 123");
        when(envioserviceimpl.save(any(Envio.class))).thenReturn(otroEnvio);
        mockmvc.perform(post("/api/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unEnvio)))
                .andExpect(status().isCreated());
    }
}
