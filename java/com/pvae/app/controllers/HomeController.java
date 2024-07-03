package com.pvae.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.pvae.app.models.EventoModel;
import com.pvae.app.models.ParticipanteModel;
import com.pvae.app.repositories.CertificadoRepository;
import com.pvae.app.servicies.EventoService;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private CertificadoRepository certificadoRepository;

    @GetMapping("/")
    public String listarEventos(Model model) {
        model.addAttribute("titulo", "Listado de Eventos");
        model.addAttribute("listaeventos", eventoService.listarEventos());
        return "home";
    }

    @GetMapping("/crear")
    public String crearEvento(Model model) {
        EventoModel evento = new EventoModel();
        List<EventoModel> listaEventos = eventoService.listarEventos();
        model.addAttribute("titulo", "Crear Evento");
        model.addAttribute("evento", evento);
        model.addAttribute("listaEventos", listaEventos);
        return "crear";
    }

    @PostMapping("/guardar")
    public String guardarEvento(@Valid @ModelAttribute("evento") EventoModel evento,
            BindingResult bindingResult,
            Model model) {
        List<EventoModel> listaEventos = eventoService.listarEventos();

        for (EventoModel eventito : listaEventos) {
            if (eventito.getNombre().equals(evento.getNombre())) {
                String errorMessage = "Ya existe un evento con el nombre proporcionado.";
                model.addAttribute("error", errorMessage);
                model.addAttribute("titulo", "Crear Evento");
                model.addAttribute("evento", evento);
                model.addAttribute("listaEventos", listaEventos);
                return "crear";
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear Evento");
            model.addAttribute("evento", evento);
            model.addAttribute("listaEventos", listaEventos);
            return "crear";
        }

        eventoService.guardarEvento(evento);
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editarUnidad(@PathVariable("id") Long idevento, Model model) {
        EventoModel evento = eventoService.buscarEvento(idevento);
        List<EventoModel> listaEventos = eventoService.listarEventos();
        model.addAttribute("titulo", "Editar Evento");
        model.addAttribute("evento", evento);
        model.addAttribute("listaEventos", listaEventos);
        return "crear";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUnidad(@PathVariable("id") Long idevento, Model model) {
        eventoService.eliminarEvento(idevento);
        return "redirect:/";
    }

    @GetMapping("/listarParticipantesPorEvento/{id}")
    public String listarParticipantesPorEvento(@PathVariable("id") Long eventoId, Model model) {
        List<ParticipanteModel> listaParticipantes = certificadoRepository.findParticipantesByEventoId(eventoId);
        model.addAttribute("titulo", "Listado de Participantes para el evento "+eventoId);
        model.addAttribute("listaparticipantes", listaParticipantes);
        return "consultas/participantes/participante";
    }

}
