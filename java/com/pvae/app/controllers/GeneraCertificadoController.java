package com.pvae.app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pvae.app.servicies.UnidadService;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/generaCertificados")
public class GeneraCertificadoController {
      @Autowired
      private UnidadService   unidadService;

      @GetMapping("/")
      public String listarUnidades(Model model) {
            model.addAttribute("titulo", "Generar Certificado");
            model.addAttribute("listaUnidades", unidadService.listarUnidades());
            return "generaCertificados/formulario";
      }



      
      
}
