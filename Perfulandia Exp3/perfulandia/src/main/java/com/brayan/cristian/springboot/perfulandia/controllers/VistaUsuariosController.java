package com.brayan.cristian.springboot.perfulandia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.brayan.cristian.springboot.perfulandia.services.UsuariosService;
import com.brayan.cristian.springboot.perfulandia.entities.Usuarios;

import java.util.List;

@Controller
public class VistaUsuariosController {

    private final UsuariosService usuariosService;

    public VistaUsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping("/usuarios")
    public String mostrarUsuarios(Model model) {
        List<Usuarios> usuarios = usuariosService.findByAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }
}