package com.pvae.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.pvae.app.models.EventoModel;
import com.pvae.app.models.ParticipanteModel;
import com.pvae.app.models.UnidadModel;
import com.pvae.app.repositories.CertificadoRepository;
import com.pvae.app.servicies.EventoService;
import com.pvae.app.servicies.UnidadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final EventoService eventoService;
    private final CertificadoRepository certificadoRepository;
    private final UnidadService unidadService;

    public HomeController(EventoService eventoService, CertificadoRepository certificadoRepository,
            UnidadService unidadService) {
        this.eventoService = eventoService;
        this.certificadoRepository = certificadoRepository;
        this.unidadService = unidadService;
    }

    private final String attributeNameTitulo = "titulo";
    private  final String attributeNameEvento = "evento";

    @GetMapping("/")
    public String listarEventos(Model model) {
        model.addAttribute(attributeNameTitulo, "Listado de Eventos");
        model.addAttribute("listaeventos", eventoService.listarEventos());
        return "home";
    }

    @GetMapping("/crear")
    public String crearEvento(Model model) {
        EventoModel evento = new EventoModel();
        List<EventoModel> listaEventos = eventoService.listarEventos();
        model.addAttribute(attributeNameTitulo, "Crear Evento");
        model.addAttribute(attributeNameEvento, evento);
        model.addAttribute("listaEventos", listaEventos);
        return "crear";
    }

    @GetMapping("/crearSteps")
    public String crearSteps(Model model) {
        EventoModel evento = new EventoModel();
        List<EventoModel> listaEventos = eventoService.listarEventos();
        model.addAttribute(attributeNameTitulo, "Crear nuevo Evento");
        model.addAttribute(attributeNameEvento, evento);
        model.addAttribute("listaEventos", listaEventos);
        return "steps";
    }

    @PostMapping("/guardar")
    public String guardarEvento(@Valid @ModelAttribute("evento") EventoModel evento, Model model) {
        List<EventoModel> listaEventos = eventoService.listarEventos();

        for (EventoModel eventito : listaEventos) {
            if (eventito.getNombre().equals(evento.getNombre())
                    && !eventito.getIdevento().equals(evento.getIdevento())) {
                String errorMessage = "Ya existe un evento con el nombre proporcionado.";
                String attributeNameError = "error";
                model.addAttribute(attributeNameError, errorMessage);
                model.addAttribute(attributeNameTitulo, "Crear Evento");
                model.addAttribute(attributeNameEvento, evento);
                model.addAttribute("listaEventos", listaEventos);
                return "crear";
            }
        }
        eventoService.guardarEvento(evento);
        return "redirect:/";
    }

    @PostMapping("/guardarSupremo")
    public String guardarSupremo(@ModelAttribute("evento") EventoModel evento, Model model) {
        List<EventoModel> listaEventos = eventoService.listarEventos();
        for (EventoModel eventito : listaEventos) {
            if (eventito.getNombre().equals(evento.getNombre())
                    && !eventito.getIdevento().equals(evento.getIdevento())) {
                String errorMessage = "Ya existe un evento con el nombre proporcionado.";
                String attributeNameError = "error";
                model.addAttribute(attributeNameError, errorMessage);
                model.addAttribute(attributeNameEvento, evento);
                model.addAttribute("listaEventos", listaEventos);
                return "crear";
            }
        }
        eventoService.guardarEvento(evento);
        return "redirect:/";
    }


    @PostMapping("/guardarElementos")
    public String guardarElementos(@Valid @ModelAttribute("evento") EventoModel evento,
                                   @RequestParam("unidadId") Long unidadId,
                                   Model model) {
        System.err.println("Se presionó el botón de guardar elementos");
    
        // Obtener el evento existente por su ID
        EventoModel eventoExistente = eventoService.buscarEvento(evento.getIdevento());
    
        if (eventoExistente == null) {
            System.err.println("No se encontró el evento con ID: " + evento.getIdevento());
            return "redirect:/"; // Redirigir a donde corresponda en tu lógica de aplicación
        }
    
        // Buscar la unidad por su ID
        UnidadModel unidad = unidadService.buscarUnidad(unidadId);
    
        // Asociar la unidad al evento si no está ya asociada
        if (eventoExistente.getUnidad() == null) {
            eventoExistente.setUnidad(unidad);
            System.out.println("Unidad asociada al evento correctamente.");
        } else {
            System.out.println("El evento ya tiene una unidad asociada.");
        }
    
        // Guardar el evento actualizado
        eventoService.guardarEvento(eventoExistente);
    
        return "redirect:/";
    }
    
    @GetMapping("/editar/{id}")
    public String editarUnidad(@PathVariable("id") Long idevento, Model model) {

        EventoModel evento = eventoService.buscarEvento(idevento);
        List<EventoModel> listaEventos = eventoService.listarEventos();
        model.addAttribute(attributeNameTitulo, "Editar Evento");
        model.addAttribute(attributeNameEvento, evento);
        model.addAttribute("listaEventos", listaEventos);
        return "crear";
    }

    @GetMapping("/agregar/{id}")
    public String agregarElementos(@PathVariable("id") Long idevento, Model model) {

        EventoModel evento = eventoService.buscarEvento(idevento);
        List<UnidadModel> listaUnidades = unidadService.listarUnidades();
        model.addAttribute(attributeNameTitulo, "Agregar Elementos al " + evento.getNombre());
        model.addAttribute(attributeNameEvento, evento);
        model.addAttribute("unidades", listaUnidades);
        return "agregar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUnidad(@PathVariable("id") Long idevento, Model model) {
        eventoService.eliminarEvento(idevento);
        return "redirect:/";
    }

    @GetMapping("/listarParticipantesPorEvento/{id}")
    public String listarParticipantesPorEvento(@PathVariable("id") Long eventoId, Model model) {
        List<ParticipanteModel> listaParticipantes = certificadoRepository.findParticipantesByEventoId(eventoId);
        model.addAttribute(attributeNameTitulo, "Listado de Participantes para el evento " + eventoId);
        model.addAttribute("listaparticipantes", listaParticipantes);
        return "consultas/participantes/participante";
    }

    /*
    @GetMapping("/steps/{id}")
    public String steps(@PathVariable("id") Long idevento, Model model) {
        return "steps";
    }
*/
}
