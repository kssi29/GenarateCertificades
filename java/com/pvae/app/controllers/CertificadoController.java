package com.pvae.app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pvae.app.util.GeneraPDFUtil;


@Controller
public class CertificadoController {
     private final GeneraPDFUtil generaPDFUtil;

    @Autowired
    public CertificadoController(GeneraPDFUtil generaPDFUtil) {
        this.generaPDFUtil = generaPDFUtil;
    }

    @GetMapping("/generarCertificados/{eventoId}")
    public String generarCertificados(@PathVariable("eventoId") Long eventoId, Model model) {
        generaPDFUtil.generarPDFHolaMundo(eventoId); // Llamada al método en instancia de GeneraPDFUtil

        return "redirect:/"; // Redirige a la página principal o a donde sea necesario
    }
    
      
}
