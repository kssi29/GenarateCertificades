package com.pvae.app.util;


import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.pvae.app.models.EventoModel;
import com.pvae.app.repositories.EventoRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;


import org.springframework.stereotype.Component;

import com.itextpdf.layout.element.Image;
import java.nio.file.Paths;

@Component
public class GeneraPDFUtil {
    private final EventoRepository eventoRepository;


    public GeneraPDFUtil(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public void generarPDFHolaMundo(Long eventoId) {
        String filePath = "C:\\workspace\\app\\src\\main\\resources\\static\\cosasGeneradas\\hola_mundo.pdf";

        try (FileOutputStream file = new FileOutputStream(filePath)) {
            // Crear el documento PDF
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);

            //ACA SETEAS LAS COSAS
            pdf.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4.rotate()); // Ajusta el tamaño según necesites
            

            Document document = new Document(pdf);

            // Obtener el evento del repositorio
            Optional<EventoModel> optionalEvento = eventoRepository.findById(eventoId);
            if (optionalEvento.isPresent()) {
                EventoModel evento = optionalEvento.get();
                String nombreImagenFondo = evento.getImagenFondo();
                if (nombreImagenFondo != null && !nombreImagenFondo.isEmpty()) {
                    // Construir la ruta completa de la imagen de fondo
                    String directorioImagenes = "C:/workspace/app/src/main/resources/static/Recursos/Fondos/";
                    Path rutaImagen = Paths.get(directorioImagenes + nombreImagenFondo);

                    // Crear un objeto Image de iText con la imagen de fondo
                    Image imagenFondo = new Image(ImageDataFactory.create(rutaImagen.toString()));

                    // Ajustar la posición y el tamaño de la imagen de fondo si es necesario
                    imagenFondo.setAutoScale(true); // Ajusta automáticamente el tamaño de la imagen al documento

                    // Agregar la imagen de fondo al documento en la capa más baja
                    document.add(imagenFondo);

                    // Puedes agregar contenido adicional al documento
                    document.add(new Paragraph("¡Hola Mundo desde PDF!"));
                } else {
                    // Manejar caso donde no hay imagen de fondo asociada al evento
                    document.add(new Paragraph("¡Hola Mundo desde PDF sin imagen de fondo!"));
                }
            } else {
                // Manejar caso donde no se encuentra el evento
                document.add(new Paragraph("Evento no encontrado"));
            }

            // Cerrar el documento
            document.close();

            System.out.println("PDF generado exitosamente en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
}
