package com.pvae.app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pvae.app.models.UnidadModel;
import com.pvae.app.servicies.UnidadService;

@Controller

@RequestMapping("consultas/unidades")
public class UnidadController {

      private final UnidadService unidadService;

      public UnidadController(UnidadService unidadService) {
            this.unidadService = unidadService;
      }

      private String attributeNameTitulo = "titulo";
      private String attributeNameError = "error";
      private String attributeNameUnidad = "unidad";
      private String attributeNameListaUnidades = "listaUnidades";


      private String attributeValueListado ="Listado de Unidades";
      private String attributeValueCrear = "Crear Unidad";
      
      

      private String rutaCrear = "consultas/unidades/crear";
      private String rutaHome = "consultas/unidades/unidad";

      @GetMapping("/")
      public String listarUnidades(Model model) {
            model.addAttribute(attributeNameTitulo, attributeValueListado);
            model.addAttribute(attributeNameListaUnidades, unidadService.listarUnidades());
            return rutaHome;
      }

      @GetMapping("/crear")
      public String crearUnidad(Model model, Long idunidad) {
            UnidadModel unidad = new UnidadModel();
            List<UnidadModel> listaUnidades = unidadService.listarUnidades();
            model.addAttribute(attributeNameTitulo, attributeValueCrear );
            model.addAttribute(attributeNameUnidad, unidad);
            model.addAttribute(attributeNameListaUnidades, listaUnidades);
            return rutaCrear;
      }

      @PostMapping("/guardar")
      public String guardarUnidad(@Valid @ModelAttribute("unidad") UnidadModel unidad,
                  BindingResult bindingResult,
                  Model model) {
            List<UnidadModel> listaUnidades = unidadService.listarUnidades();

            if (bindingResult.hasErrors()) {
   
                  model.addAttribute(attributeNameTitulo, attributeValueCrear );
                  model.addAttribute(attributeNameUnidad, unidad);
                  model.addAttribute(attributeNameListaUnidades, listaUnidades);
                  return rutaCrear;
            }

            try {
                  unidadService.guardarUnidad(unidad);
                  return "redirect:/consultas/unidades/";
            } catch (DataIntegrityViolationException e) {
                  model.addAttribute(attributeNameTitulo, attributeValueCrear );
                  String errorMessage = "Ya existe una unidad con el nombre proporcionado.";
                  model.addAttribute(attributeNameListaUnidades, listaUnidades);
                  model.addAttribute(attributeNameError, errorMessage);
                  return rutaCrear;
            }
      

      }

      @GetMapping("/editar/{id}")
      public String editarUnidad(@PathVariable("id") Long idunidad, Model model) {

            if(idunidad !=1){
                  UnidadModel unidad = unidadService.buscarUnidad(idunidad);

                  List<UnidadModel> listaUnidadesTotal = unidadService.listarUnidades();
                  List<UnidadModel> listaUnidades= new ArrayList<>();
                  for (UnidadModel unidadBucle : listaUnidadesTotal) {
                         if (!unidadBucle.getIdunidad().equals(idunidad)) {
                              listaUnidades.add(unidadBucle);
                        }
                    }
                  model.addAttribute(attributeNameTitulo, "Editar Unidad");
                  model.addAttribute(attributeNameUnidad, unidad);
                  model.addAttribute(attributeNameListaUnidades, listaUnidades);
                  return rutaCrear;

                  
            }
            String errorMessage = "No se puede editar esta unidad.";
            List<UnidadModel> listaUnidades = unidadService.listarUnidades();
            model.addAttribute(attributeNameError, errorMessage);
            model.addAttribute(attributeNameTitulo, "Lista de Unidades");
            model.addAttribute(attributeNameListaUnidades, listaUnidades);

            return rutaHome;
           
      }



      @GetMapping("/eliminar/{id}")
      public String eliminarUnidad(@PathVariable("id") Long idunidad, Model model) {
            try {
                  unidadService.eliminarUnidad(idunidad);
                  return "redirect:/consultas/unidades/";
            } catch (IllegalArgumentException e) {
                  String errorMessage = "No se puede eliminar la unidad: " + e.getMessage();
                  model.addAttribute("error", errorMessage);
                  List<UnidadModel> listaUnidades = unidadService.listarUnidades();
                  model.addAttribute(attributeNameTitulo, "Lista de Unidades");
                  model.addAttribute(attributeNameListaUnidades, listaUnidades);
                  return "consultas/unidades/unidad"; 
            }
      }

}
