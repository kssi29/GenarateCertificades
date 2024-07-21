package com.pvae.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pvae.app.models.ParticipanteModel;

import com.pvae.app.servicies.ParticipanteService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// 0 partipante , 1 organizador, 2 autoridad
@Controller
@RequestMapping("/consultas/participantes")
public class ParticipanteController {

      private final ParticipanteService participanteService;

      public ParticipanteController(ParticipanteService participanteService) {
            this.participanteService = participanteService;
      }

      // atributeName
      private String attributeNameTitulo = "titulo";
      private String attributeNameError = "error";
      private String attributeNameParticipante = "participante";
      // rutas
      private String rutahome = "consultas/participantes/participante";
      private String rutacrear = "consultas/participantes/crear";

      @GetMapping("/")
      public String listarParticipantes(Model model) {
            List<ParticipanteModel> listaParticipantes = participanteService.listarParticipantes();
            model.addAttribute(attributeNameTitulo, "Listado de Participantes");
            model.addAttribute("listaparticipantes", listaParticipantes);

            return rutahome;
      }

      @GetMapping("/crear")
      public String crearParticipante(Model model) {
            ParticipanteModel participante = new ParticipanteModel();
            model.addAttribute(attributeNameTitulo, "Crear Participante");
            model.addAttribute(attributeNameParticipante, participante);
            return rutacrear;
      }

      @PostMapping("/guardar")
      public String guardarParticipante(@Valid @ModelAttribute("participante") ParticipanteModel participante,
                  Model model) {
            for (ParticipanteModel participanteito : participanteService.listarParticipantes()) {
                  if (participanteito.getCi() == participante.getCi()) {
                        String errorMessage = "Ya existe un participante con el ci proporcionado.";
                        model.addAttribute(attributeNameError, errorMessage);
                        model.addAttribute(attributeNameTitulo, "Crear Participante");
                        model.addAttribute(attributeNameParticipante, participante);
                        return rutacrear;
                  }
            }
            participanteService.guardarParticipante(participante);
            return rutahome;

      }

      @GetMapping("/editar/{id}")
      public String editarParticipante(@PathVariable Long id, Model model) {
            ParticipanteModel participante = participanteService.buscarParticipante(id);
            model.addAttribute(attributeNameTitulo, "Editar Participante");
            model.addAttribute(attributeNameParticipante, participante);
            return rutacrear;
      }

      @GetMapping("/eliminar/{id}")
      public String eliminarParticipante(@PathVariable Long id) {
            participanteService.eliminarParticipante(id);
            return "redirect:/consultas/participantes/";
      }

}
