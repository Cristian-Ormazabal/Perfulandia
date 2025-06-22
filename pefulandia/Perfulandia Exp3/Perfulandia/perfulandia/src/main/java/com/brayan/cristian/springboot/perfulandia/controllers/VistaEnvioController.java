package com.brayan.cristian.springboot.perfulandia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.brayan.cristian.springboot.perfulandia.entities.Envio;
import com.brayan.cristian.springboot.perfulandia.services.EnvioService;

import java.util.List;

@Controller
public class VistaEnvioController {

    private final EnvioService envioService;

    public VistaEnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping("/envios")
    public String mostrarEnvios(Model model) {
        List<Envio> envio = envioService.findByAll();
        model.addAttribute("envio", envio);
        return "envio";
    }
}