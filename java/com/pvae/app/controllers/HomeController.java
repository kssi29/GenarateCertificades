package com.pvae.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pvae.app.models.EventoModel;
import com.pvae.app.models.ParticipanteModel;
import com.pvae.app.models.UnidadModel;
import com.pvae.app.repositories.CertificadoRepository;
import com.pvae.app.servicies.EventoService;
import com.pvae.app.servicies.UnidadService;
import com.pvae.app.servicies.SubirArchivoService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class HomeController {

    private final EventoService eventoService;
    private final CertificadoRepository certificadoRepository;
    private final UnidadService unidadService;

    private final String id = "id";


    public HomeController(EventoService eventoService, CertificadoRepository certificadoRepository,
                          UnidadService unidadService) {
        this.eventoService = eventoService;
        this.certificadoRepository = certificadoRepository;
        this.unidadService = unidadService;

    }

    private final String attributeNameTitulo = "titulo";
    private final String attributeNameEvento = "evento";

    @GetMapping("/")
    public String listarEventos(Model model) {
        model.addAttribute(attributeNameTitulo, "Listado de Eventos");
        model.addAttribute("listaeventos", eventoService.listarEventos());
        return "home";
    }


    @GetMapping("/crearSteps")
    public String crearSteps(Model model) {
        System.out.println("Se presionó el botón de crear evento");
        EventoModel evento = new EventoModel();
        List<EventoModel> listaEventos = eventoService.listarEventos();
        List<UnidadModel> listaUnidades = unidadService.listarUnidades();
        model.addAttribute(attributeNameTitulo, "Crear nuevo Evento");
        model.addAttribute(attributeNameEvento, evento);
        model.addAttribute("listaEventos", listaEventos);
        model.addAttribute("unidades", listaUnidades);
        return "steps";
    }

    @PostMapping("/guardarEvento")
    public String guardarEvento(
            @RequestParam("nombre") String nombre,
            @RequestParam("unidadId") Long unidadId,
            @RequestParam("imagenFondo") MultipartFile imagenFondo,
            Model model) {

        try {
            EventoModel evento = new EventoModel();
            evento.setNombre(nombre);

            UnidadModel unidad = unidadService.buscarUnidad(unidadId);
            evento.setUnidad(unidad);


            EventoModel eventoGuardado = eventoService.guardarEvento(evento);

            if (imagenFondo != null && !imagenFondo.isEmpty()) {
                String directorioDestino = "C:/workspace/app/src/main/resources/static/Recursos/Fondos/";
                String nombreArchivo = eventoGuardado.getIdevento() + "_" + imagenFondo.getOriginalFilename();
                Path rutaCompleta = Paths.get(directorioDestino + nombreArchivo);
                Files.write(rutaCompleta, imagenFondo.getBytes());

                eventoGuardado.setImagenFondo(nombreArchivo);
                eventoService.guardarEvento(eventoGuardado);
            }

            return "redirect:/";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un problema al guardar el evento. Inténtalo de nuevo.");
            return "crear";
        }
    }


    @PostMapping("/validarNombreEvento")
    public ResponseEntity<Map<String, Object>> validateEventName(@RequestParam String name) {
        boolean nombreExistente = eventoService.buscarEventoPorNombre(name) != null;
        Map<String, Object> response = new HashMap<>();
        response.put("valido", !nombreExistente);
        return ResponseEntity.ok(response);
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
    public String agregarParticipantes(@PathVariable("id") Long idevento, Model model) {
        EventoModel evento = eventoService.buscarEvento(idevento);
        model.addAttribute("titulo", "Agregar partipantes a: " + evento.getNombre());
        model.addAttribute("evento", evento);
        return "agregar";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarUnidad(@PathVariable("id") Long idevento, Model model) {
        eventoService.eliminarEvento(idevento);
        return "redirect:/";
    }

    @GetMapping("/listarParticipantesPorEvento/{id}")
    public String listarParticipantesPorEvento(@PathVariable(id) Long eventoId, Model model) {
        List<ParticipanteModel> listaParticipantes = certificadoRepository.findParticipantesByEventoId(eventoId);
        model.addAttribute(attributeNameTitulo, "Listado de Participantes para el evento " + eventoId);
        model.addAttribute("listaparticipantes", listaParticipantes);
        return "consultas/participantes/participante";
    }

    @PostMapping("/excelP/{eventoId}")
    public String manejaSubidaExcel(@RequestParam("archivo") MultipartFile archivo,
                                    @PathVariable("eventoId") Long eventoId, Model model) {
        if (archivo.isEmpty()) {
            model.addAttribute("error", "Archivo vacío");
            return "redirect:/agregar/" + eventoId;
        }
        String resultadoProcesamiento = eventoService.procesarYGuardarExcel(archivo, eventoId, model);
        if (resultadoProcesamiento.startsWith("Error")) {
            model.addAttribute("error", resultadoProcesamiento);
            return "redirect:/agregar/" + eventoId;
        }



        return "redirect:/agregar/" + eventoId + "#step2";

    }

}
