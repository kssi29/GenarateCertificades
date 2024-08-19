package com.pvae.app.servicies;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pvae.app.models.AutoridadModel;
import com.pvae.app.repositories.AutoridadRepository;

import jakarta.transaction.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AutoridadService {

    private final AutoridadRepository autoridadRepository;

    public AutoridadService(AutoridadRepository autoridadRepository) {
        this.autoridadRepository = autoridadRepository;
    }

    public List<AutoridadModel> listarAutoridades() {
        return (List<AutoridadModel>) autoridadRepository.findAll();
    }

    public AutoridadModel buscarAutoridad(Long id) {
        return autoridadRepository.findById(id).orElse(null);
    }

    @Transactional
    public void guardarAutoridad(AutoridadModel autoridad) {
        System.out.println("supuestamente se actualizo");
        autoridadRepository.save(autoridad);
    }

    @Transactional
    public void eliminarAutoridad(Long idautoridad) {
        autoridadRepository.deleteById(idautoridad);
    }



    @Transactional
    public AutoridadModel guardaAutoridad(int ci, String email, String paterno, String materno, String nombre) {
        AutoridadModel autoridad = autoridadRepository.findByCi(ci);

        if (autoridad == null) {
            autoridad = new AutoridadModel();
            autoridad.setci(ci);
            autoridad.setemail(email);
            autoridad.setpaterno(paterno);
            autoridad.setmaterno(materno);
            autoridad.setnombre(nombre);
            autoridadRepository.save(autoridad);
        } else {
            autoridad.setemail(email);
            autoridad.setpaterno(paterno);
            autoridad.setmaterno(materno);
            autoridad.setnombre(nombre);
            autoridadRepository.save(autoridad);
        }
        return autoridad;
    }
    @Transactional
    public void guardarFirma(MultipartFile imagenFirma, AutoridadModel autoridad, Model model, String cargo) {
        try {


            if (imagenFirma != null && !imagenFirma.isEmpty()) {
                String directorioDestino = "C:/workspace/app/src/main/resources/static/Recursos/Firmas/";

                Path directorioPath = Paths.get(directorioDestino);
                if (!Files.exists(directorioPath)) {
                    Files.createDirectories(directorioPath);
                }

                String nombreArchivo = "Firma_"
                        + autoridad.getPaterno() + '_'
                        + autoridad.getNombre() + "_"
                        + autoridad.getCargo() + ".jpg";

                Path rutaCompleta = Paths.get(directorioDestino + nombreArchivo);
                Files.write(rutaCompleta, imagenFirma.getBytes());
                System.out.println("Se asigna el nombre del archivo "+nombreArchivo);
                autoridad.setImagenFirma(nombreArchivo);
                autoridad.setCargo(cargo);
            }

        } catch (IOException e) {
            // Captura IOException para errores específicos de I/O
            System.out.println("Error al guardar el archivo: " + e.getMessage());
            model.addAttribute("error", "Error al guardar el archivo: " + e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otra excepción
            System.out.println("Hubo un error: " + e.getMessage());
            model.addAttribute("error", "Hubo un error: " + e.getMessage());
        }

    }
}
