package com.pvae.app.controllers;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pvae.app.models.AutoridadModel;
import com.pvae.app.servicies.AutoridadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/consultas/autoridades")
public class AutoridadControlller {

      private String rutaHome="consultas/autoridades/autoridad";
      private String rutaCrear="consultas/autoridades/crear";
      private final AutoridadService autoridadService;      
      public AutoridadControlller(AutoridadService autoridadService) {
            this.autoridadService = autoridadService;
      }

      @GetMapping("/")
      public String listarAutoridad(Model model){
            List<AutoridadModel> listaAutoridades = autoridadService.listarAutoridades();
            model.addAttribute("titulo", "Listado de Autoridades");
            model.addAttribute("listaautoridades", listaAutoridades);
            return rutaHome;
      }

      @GetMapping("/crear")
      public String crearAutoridad(Model model){
            AutoridadModel autoridad = new AutoridadModel();
            model.addAttribute("titulo", "Crear Autoridad");
            model.addAttribute("autoridad", autoridad);
            return rutaCrear;
      }

      @PostMapping("/guardar")
      public String guardarAutoridad(@Valid @ModelAttribute("autoridad") AutoridadModel autoridad, Model model){
            for (AutoridadModel autoridadito : autoridadService.listarAutoridades()) {
                  if (autoridadito.getci() == autoridad.getci()) {
                        model.addAttribute("error", "Ya existe un autoridad con ese ci");
                        model.addAttribute("autoridad", autoridad);
                        return rutaCrear;
                  }
            }
            autoridadService.guardarAutoridad(autoridad);
            return rutaHome;
      }

      @GetMapping("/editar/{id}")
      public String editarAutoridad(@PathVariable Long id, Model model){
            AutoridadModel autoridad = autoridadService.buscarAutoridad(id);
            model.addAttribute("titulo", "Editar Autoridad");
            model.addAttribute("autoridad", autoridad);
            return rutaCrear;
      }

      @GetMapping("/eliminar/{id}")
      public String eliminarAutoridad(@PathVariable Long id){
            autoridadService.eliminarAutoridad(id);
            return "redirect:/consultas/autoridades/";
      }


      

      
}
