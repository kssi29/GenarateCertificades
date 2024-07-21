package com.pvae.app.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pvae.app.models.EventoModel;
import com.pvae.app.repositories.EventoRepository;
import com.pvae.app.servicies.SubirArchivoService;

@RestController
@RequestMapping("/subirArchivo")
public class SubirArchivoController {
    private final SubirArchivoService subirArchivoService;
    private final EventoRepository eventoRepository;



    public SubirArchivoController(SubirArchivoService subirArchivoService,EventoRepository eventoRepository) {
        this.subirArchivoService = subirArchivoService;
        this.eventoRepository = eventoRepository;
       
    }

    @PostMapping("/excel")
    public ResponseEntity<String> manejaSubidaExcel(@RequestParam("archivo") MultipartFile archivo,
            @RequestParam("eventoId") Long eventoId) {
        if (archivo.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo vacío");
        }
        String resultadoProcesamiento = subirArchivoService.procesarYGuardarExcel(archivo, eventoId);
        return ResponseEntity.ok(resultadoProcesamiento);
    }

        @PostMapping("/imagen")
        public ResponseEntity<String> manejaSubidaImagen(@RequestParam("imagen") MultipartFile imagen,
                @RequestParam("eventoId") Long eventoId) {
            try {
                    if (imagen.isEmpty()) {
               
                    Optional<EventoModel> optionalEvento = eventoRepository.findById(eventoId);
                    if (optionalEvento.isPresent()) {
                        EventoModel evento = optionalEvento.get();
                        evento.setImagenFondo(null); 
                        eventoRepository.save(evento); 
                        return ResponseEntity.ok("Imagen eliminada correctamente del evento");
                    } else {
                        return ResponseEntity.badRequest().body("Evento no encontrado");
                    }
                } else {

                    String directorioDestino = "C:/workspace/app/src/main/resources/static/Recursos/Fondos/";
                    String nombreArchivo = eventoId + "_" + imagen.getOriginalFilename();
                    Path rutaCompleta = Paths.get(directorioDestino + nombreArchivo);
                    Files.write(rutaCompleta, imagen.getBytes());

                    Optional<EventoModel> optionalEvento = eventoRepository.findById(eventoId);
                    if (optionalEvento.isPresent()) {
                        EventoModel evento = optionalEvento.get();
                        evento.setImagenFondo(nombreArchivo);
                        eventoRepository.save(evento); 
                        return ResponseEntity.ok("Imagen subida y asociada correctamente al evento");
                    } else {
                        return ResponseEntity.badRequest().body("Evento no encontrado");
                    }
                }
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error al subir o eliminar la imagen: " + e.getMessage());
            }
        }
}

