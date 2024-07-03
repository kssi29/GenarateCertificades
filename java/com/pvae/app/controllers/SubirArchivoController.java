package com.pvae.app.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pvae.app.servicies.SubirArchivoService;

@RestController
@RequestMapping("/subirArchivo")
public class SubirArchivoController {
      private final SubirArchivoService subirArchivoService;

      public SubirArchivoController(SubirArchivoService subirArchivoService) {
            this.subirArchivoService = subirArchivoService;
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
      

}
