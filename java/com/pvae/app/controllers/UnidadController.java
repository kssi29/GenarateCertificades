package com.pvae.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import com.pvae.app.models.UnidadModel;
import com.pvae.app.servicies.UnidadService;

@Controller

@RequestMapping("consultas/unidades")
public class UnidadController {
      @Autowired
      private UnidadService unidadService;

      @GetMapping("/")
      public String listarUnidades(Model model) {
            model.addAttribute("titulo", "Listado de Unidades");
            model.addAttribute("listaunidades", unidadService.listarUnidades());
            return "consultas/unidades/unidad";
      }

      @GetMapping("/crear")
      public String crearUnidad(Model model, Long idunidad) {
            UnidadModel unidad = new UnidadModel();
            List<UnidadModel> listaUnidades = unidadService.listarUnidades();
            model.addAttribute("titulo", "Crear Unidad");
            model.addAttribute("unidad", unidad);
            model.addAttribute("listaUnidades", listaUnidades);
            return "consultas/unidades/crear";
      }

      @PostMapping("/guardar")
      public String guardarUnidad(@Valid @ModelAttribute("unidad") UnidadModel unidad,
                  BindingResult bindingResult,
                  Model model) {
            List<UnidadModel> listaUnidades = unidadService.listarUnidades();

            if (bindingResult.hasErrors()) {
   
                  model.addAttribute("titulo", "Crear Unidad");
                  model.addAttribute("unidad", unidad);
                  model.addAttribute("listaUnidades", listaUnidades);
                  return "consultas/unidades/crear";
            }

            // Verificar si la unidad ya existe en la lista
            for (UnidadModel unidadcita : listaUnidades) {
                  if (unidadcita.getIdunidad().equals(unidad.getIdunidad())) { // Aquí comparas con el ID (o el campo único)
                        // Mostrar mensaje de error
                        model.addAttribute("titulo", "Crear Unidad");
                        String errorMessage = "Una misma unidad no puede pertenecer a sí misma.";
                        model.addAttribute("listaUnidades", listaUnidades);
                        model.addAttribute("error", errorMessage);
                        return "consultas/unidades/crear";
                  }
            }

            try {
                  unidadService.guardarUnidad(unidad);
                  return "redirect:/consultas/unidades/";
            } catch (DataIntegrityViolationException e) {
                  // Capturar excepción de violación de restricción de unicidad (por ejemplo,
                  // nombre duplicado)
                  model.addAttribute("titulo", "Crear Unidad");
                  String errorMessage = "Ya existe una unidad con el nombre proporcionado.";
                  model.addAttribute("listaUnidades", listaUnidades);
                  model.addAttribute("error", errorMessage);
                  return "consultas/unidades/crear";
            }
      

      }

      @GetMapping("/editar/{id}")
      public String editarUnidad(@PathVariable("id") Long idunidad, Model model) {

            if(idunidad !=1){
                  UnidadModel unidad = unidadService.buscarUnidad(idunidad);

                  List<UnidadModel> listaUnidades = unidadService.listarUnidades();
                  model.addAttribute("titulo", "Editar Unidad");
                  model.addAttribute("unidad", unidad);
                  model.addAttribute("listaUnidades", listaUnidades);
                  return "consultas/unidades/crear";

                  
            }
            String errorMessage = "No se puede editar esta unidad.";
            model.addAttribute("error", errorMessage);
            return "redirect:/consultas/unidades/";
           
      }



      @GetMapping("/eliminar/{id}")
      public String eliminarUnidad(@PathVariable("id") Long idunidad, Model model) {
            try {
                  unidadService.eliminarUnidad(idunidad);
                  return "redirect:/consultas/unidades/";
            } catch (IllegalArgumentException e) {
                  // Capturar excepción de unidad no eliminable
                  String errorMessage = "No se puede eliminar la unidad: " + e.getMessage();
                  model.addAttribute("error", errorMessage);

                  // Cargar nuevamente la página de unidades con el mensaje de error
                  List<UnidadModel> listaUnidades = unidadService.listarUnidades();
                  model.addAttribute("titulo", "Lista de Unidades");
                  model.addAttribute("listaunidades", listaUnidades);

                  return "consultas/unidades/unidad"; // Ajusta la vista según tu estructura
            }
      }

}
