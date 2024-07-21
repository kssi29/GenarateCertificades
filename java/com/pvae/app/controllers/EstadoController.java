package com.pvae.app.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pvae.app.models.EstadoModel;
import com.pvae.app.servicies.EstadoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/consultas/estados")
public class EstadoController {
      private  final EstadoService estadoService;

      public EstadoController(EstadoService estadoService) {
            this.estadoService = estadoService;
      }
 
      private String attributeNameTitulo="titulo";
      private String attributeNameEstado="estado";
      private String attributeNameError="error";

      private String rutaCrear = "consultas/estados/crear";

      @GetMapping("/")
      public String listarEventos(Model model){
            model.addAttribute(attributeNameTitulo, "Listado de Estados");
            model.addAttribute("listaestados", estadoService.listarEstados());
            return "consultas/estados/estado";
      }
      @GetMapping("/crear")
      public String crearEstado(Model model){
            EstadoModel estado = new EstadoModel();
            model.addAttribute(attributeNameTitulo
, "Crear Estado");
            model.addAttribute(attributeNameEstado, estado);
            return rutaCrear;
      }
      @PostMapping("/guardar")
      public String guardarEstado(@Valid @ModelAttribute("estado") EstadoModel estado, Model model){
            for(EstadoModel estadito : estadoService.listarEstados()){
                  if(estadito.getDescripcion().equals(estado.getDescripcion())){
                        String errorMessage = "Ya existe un estado con el nombre proporcionado.";
                        model.addAttribute(attributeNameError
, errorMessage);
                        model.addAttribute(attributeNameTitulo
, "Crear Estado");
                        model.addAttribute(attributeNameEstado, estado);
                        return rutaCrear;
                  }
            }
            estadoService.guardarEstado(estado);
            return "redirect:/consultas/estados/";
            
      }    
      @GetMapping("/editar/{id}")
      public String editarEstado(@PathVariable Long id, Model model){
            EstadoModel estado = estadoService.buscarEstado(id);
            model.addAttribute(attributeNameTitulo, "Editar Estado");
            model.addAttribute(attributeNameEstado, estado);
            return rutaCrear;
      }
      @GetMapping("/eliminar/{id}")
      public String eliminarEstado(@PathVariable Long id){
            estadoService.eliminarEstado(id);
            return "redirect:/consultas/estados/";
      }
        
      
}
