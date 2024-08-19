package com.pvae.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.pvae.app.models.AutoridadModel;
import com.pvae.app.servicies.AutoridadService;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/consultas/autoridades")
public class AutoridadControlller {

    private String rutaHome = "consultas/autoridades/autoridad";
    private String rutaCrear = "consultas/autoridades/crear";
    private final AutoridadService autoridadService;

    public AutoridadControlller(AutoridadService autoridadService) {
        this.autoridadService = autoridadService;
    }

    @GetMapping("/")
    public String listarAutoridad(Model model) {
        List<AutoridadModel> listaAutoridades = autoridadService.listarAutoridades();
        model.addAttribute("titulo", "Listado de Autoridades");
        model.addAttribute("listaautoridades", listaAutoridades);
        return rutaHome;
    }

    @GetMapping("/crear")
    public String crearAutoridad(Model model) {
        AutoridadModel autoridad = new AutoridadModel();
        model.addAttribute("titulo", "Crear Autoridad");
        model.addAttribute("autoridad", autoridad);
        return rutaCrear;
    }

    /*
          @PostMapping("/guardar2")
          public String guardarAutoridad2(@Valid @ModelAttribute("autoridad") AutoridadModel autoridad, Model model){
                for (AutoridadModel autoridadito : autoridadService.listarAutoridades()) {
                      if (autoridadito.getci() == autoridad.getci()) {
                            model.addAttribute("error", "Ya existe un autoridad con ese ci");
                            model.addAttribute("autoridad", autoridad);
                            return rutaCrear;
                      }
                }
                autoridadService.guardarAutoridad(autoridad);
                model.addAttribute("autoridad", autoridad);
                return "redirect:/agregar/1#step2";
          }


     */
    //este es sola para editar
    //c reare otro evento para ver si workea asi
    @PostMapping("/guardar")
    public String guardarAutoridad(@Valid @ModelAttribute("autoridad") AutoridadModel autoridad, Model model) {
        autoridadService.guardarAutoridad(autoridad);
        model.addAttribute("autoridad", autoridad);
        return "redirect:/agregar/1#step2";


    }

    @PostMapping("/guardarAutoridad")
    public String guardarAutoridad(

            @RequestParam("idpersona") Long idpersona,
            @RequestParam("cargo") String cargo,
            @RequestParam("imagenFirma") MultipartFile imagenFirma,
            Model model) {

        AutoridadModel autoridad = autoridadService.buscarAutoridad(idpersona);

        try {
            autoridadService.guardarFirma(imagenFirma, autoridad, model,cargo);


        } catch (Exception e) {
            model.addAttribute("error", "Hubo un error: " + e.getMessage());
            return "redirect:/error";
        }
        return "redirect:/agregar/1#step2";

    }

    @GetMapping("/editar/{id}")
    public String editarAutoridad(@PathVariable Long id, Model model) {
        AutoridadModel autoridad = autoridadService.buscarAutoridad(id);
        model.addAttribute("titulo", "Editar Autoridad");
        model.addAttribute("autoridad", autoridad);
        return rutaCrear;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAutoridad(@PathVariable Long id) {
        autoridadService.eliminarAutoridad(id);
        return "redirect:/consultas/autoridades/";
    }


}
