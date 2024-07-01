package com.pvae.app.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
      @Autowired
      private ParticipanteService participanteService;



      
      @GetMapping("/")
      public String listarParticipantes(Model model) {
            List<ParticipanteModel> listaParticipantes = participanteService.listarParticipantes();
            model.addAttribute("titulo", "Listado de Participantes");
            model.addAttribute("listaparticipantes", listaParticipantes);

            return "consultas/participantes/participante";
      }

      @GetMapping("/crear")
      public String crearParticipante(Model model) {
            ParticipanteModel participante = new ParticipanteModel();
            model.addAttribute("titulo", "Crear Participante");
            model.addAttribute("participante", participante);
            return "consultas/participantes/crear";
      }

      @PostMapping("/guardar")
      public String guardarParticipante(@Valid @ModelAttribute("participante") ParticipanteModel participante,
                  Model model) {
            for (ParticipanteModel participanteito : participanteService.listarParticipantes()) {
                  if (participanteito.getCi() == participante.getCi()) {
                        String errorMessage = "Ya existe un participante con el ci proporcionado.";
                        model.addAttribute("error", errorMessage);
                        model.addAttribute("titulo", "Crear Participante");
                        model.addAttribute("participante", participante);
                        return "consultas/participantes/crear";
                  }
            }
            participanteService.guardarParticipante(participante);
            return "redirect:/consultas/participantes/";

      }

      @GetMapping("/editar/{id}")
      public String editarParticipante(@PathVariable Long id, Model model) {
            ParticipanteModel participante = participanteService.buscarParticipante(id);
            model.addAttribute("titulo", "Editar Participante");
            model.addAttribute("participante", participante);
            return "consultas/participantes/crear";
      }

      @GetMapping("/eliminar/{id}")
      public String eliminarParticipante(@PathVariable Long id) {
            participanteService.eliminarParticipante(id);
            return "redirect:/consultas/participantes/";
      }

}
