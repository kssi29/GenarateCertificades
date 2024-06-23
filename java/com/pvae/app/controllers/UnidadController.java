package com.pvae.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

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
      public String crearUnidad(Model model) {
            UnidadModel unidad = new UnidadModel();
            List<UnidadModel> listaUnidades = unidadService.listarUnidades();
            model.addAttribute("titulo", "Crear Unidad");
            model.addAttribute("unidad", unidad);
            model.addAttribute("listaUnidades", listaUnidades);
            return "consultas/unidades/crear";
      }

      @PostMapping("/guardar")
      public String guardarUnidad(@ModelAttribute UnidadModel unidad) {
            unidadService.guardarUnidad(unidad);
            return "redirect:/consultas/unidades/";
      }

      @GetMapping("/editar/{id}")
      public String editarUnidad(@PathVariable("id") Long idunidad, Model model) {
            UnidadModel unidad = unidadService.buscarUnidad(idunidad);
            List<UnidadModel> listaUnidades = unidadService.listarUnidades();
            model.addAttribute("titulo", "Editar Unidad");
            model.addAttribute("unidad", unidad);
            model.addAttribute("listaUnidades", listaUnidades);
            return "consultas/unidades/crear";
      }

      //te envia al listado de unidades
      @GetMapping("/eliminar/{id}")
      public String eliminarUnidad(@PathVariable("id") Long idunidad) {
            unidadService.eliminarUnidad(idunidad);
            return "redirect:/consultas/unidades/";
      }
}
