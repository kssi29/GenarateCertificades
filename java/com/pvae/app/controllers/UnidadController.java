package com.pvae.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


import com.pvae.app.servicies.UnidadService;

@Controller
@RequestMapping("consultas/unidades")
public class UnidadController {
      @Autowired
      private UnidadService unidadService;

      @GetMapping("/")
      public String listarUnidades(Model model) {
            model.addAttribute("titulo", "Listado de Unidades");
            model.addAttribute("listaunidades", unidadService.listarUnidades());
            return "consultas/unidades/unidad";
      }


      
}
