package com.pvae.app.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.pvae.app.util.GeneraPDFUtil;


@Controller
public class CertificadoController {
    private final GeneraPDFUtil generaPDFUtil;


    public CertificadoController(GeneraPDFUtil generaPDFUtil) {
        this.generaPDFUtil = generaPDFUtil;
    }


    @GetMapping("/generarCertificados/{eventoId}")
    public String generarCertificados(@PathVariable("eventoId") Long eventoId, Model model) {
        try {
            System.err.println("Generando certificados para el evento con ID: " + eventoId);
            generaPDFUtil.generaCertificados(eventoId);
        } catch (Exception e) {

            e.printStackTrace();
        }


        return "redirect:/";
    }


}
