package com.brayan.cristian.springboot.perfulandia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.brayan.cristian.springboot.perfulandia.services.InventarioService;
import com.brayan.cristian.springboot.perfulandia.entities.Inventario;

import java.util.List;

@Controller
public class VistaInventarioController {

    private final InventarioService inventarioService;

    public VistaInventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/inventario")
    public String mostrarInventario(Model model) {
        List<Inventario> inventario = inventarioService.findByAll();
        model.addAttribute("inventario", inventario);
        return "inventario";
    }
}