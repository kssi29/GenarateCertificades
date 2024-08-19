package com.pvae.app.util;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import com.pvae.app.exception.Myexception;
import com.pvae.app.models.AutoridadModel;
import com.pvae.app.models.EventoModel;
import com.pvae.app.models.ParticipanteModel;
import com.pvae.app.models.UnidadModel;
import com.pvae.app.repositories.CertificadoRepository;
import com.pvae.app.repositories.EventoRepository;
import com.pvae.app.servicies.AutoridadService;
import com.pvae.app.servicies.UnidadService;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class GeneraPDFUtil {

    private final EventoRepository eventoRepository;
    private final CertificadoRepository certificadoRepository;
    private final UnidadService unidadService;
    private final AutoridadService autoridadService;

    public GeneraPDFUtil(EventoRepository eventoRepository, CertificadoRepository certificadoRepository,
                         UnidadService unidadService, AutoridadService autoridadService) throws Myexception {
        this.eventoRepository = eventoRepository;
        this.certificadoRepository = certificadoRepository;
        this.unidadService = unidadService;
        this.autoridadService = autoridadService;
    }

    public void generaCertificados(Long eventoId) throws Myexception {

        try (PdfDocument pdfDoc = new PdfDocument(
                new PdfWriter("C:\\workspace\\app\\src\\main\\resources\\static\\cosasGeneradas\\hola_mun2do.pdf"))) {
            PageSize pageSize = PageSize.A4.rotate();
            procesarDocumento(pdfDoc, pageSize, eventoId);
            System.err.println("Estamos en generaCertificados");

        } catch (Exception e) {
            throw new Myexception("Error con la ruta para guardar los certificados del evento con id: " + eventoId, e);
        }

    }

    private void procesarDocumento(PdfDocument pdfDoc, PageSize pageSize, Long eventoId) throws Myexception, MalformedURLException {

        try (Document doc = new Document(pdfDoc, pageSize)) {

            List<ParticipanteModel> participantes = certificadoRepository.findParticipantesByEventoId(eventoId);
            //por ahora
            List<AutoridadModel> autoridades = autoridadService.listarAutoridades();
            for (int i = 0; i < participantes.size(); i++) {
                ParticipanteModel participante = participantes.get(i);

                PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
                canvas.addImageFittedIntoRectangle(ImageDataFactory.create(buscarImagenFondoPath(eventoId)), pageSize,
                        false);

                aniadirElementosPDF(eventoId, doc, participante, autoridades);
            }


        } catch (
                Exception e) {
            throw new Myexception("Error al generar el docuemnto del evento con la id: " + eventoId, e);
        }


    }
/*
    private void separarParticipantes(List<ParticipanteModel> listaParticipantes, List<ParticipanteModel> autoridades,
            List<ParticipanteModel> receptores) {
        for (ParticipanteModel participante : listaParticipantes) {
            if ("0".equals(participante.getTipo())) {
                autoridades.add(participante);
            } else {
                receptores.add(participante);
            }
        }
    }

 */

    /*  public void aniadirElementosPDF(Long eventoid, Document document, ParticipanteModel participante,
              List<ParticipanteModel> autoridades, int index, int totalRecibe) {

     */
    public void aniadirElementosPDF(Long eventoid, Document document, ParticipanteModel participante,
                                    List<AutoridadModel> autoridades) {
        try {

            document.add(encabezadoTable(eventoid));
            document.add(cuerpoTable(participante));
            document.add(ponerFirmas(autoridades));

            /*
            if (index < totalRecibe - 1) {
                document.add(new AreaBreak());
                System.out.println("index: " + index + " totalRecibe: " + (totalRecibe - 1));
            }

             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Table encabezadoTable(Long eventoid) {
        Table headerTable = new Table(1);
        headerTable.setWidth(750);

        Optional<EventoModel> optionalEvento = eventoRepository.findById(eventoid);
        if (optionalEvento.isPresent()) {
            EventoModel evento = optionalEvento.get();

            UnidadModel unidad = evento.getUnidad();
            List<UnidadModel> unidadesPadres = unidadService.obtenerUnidadesPadres(unidad);

            for (UnidadModel unidadPadre : unidadesPadres) {
                headerTable.addCell(createCellUp(unidadPadre.getNombre(), TextAlignment.CENTER, 1, 1));
            }

            headerTable.addCell(createCellUp(evento.getNombre(), TextAlignment.CENTER, 1, 1));

        } else {
            System.out.println("No se encontró ningún evento con el ID: " + eventoid);
        }
        headerTable.setMarginBottom(40);

        return headerTable;
    }


    private Table cuerpoTable(ParticipanteModel participante) {
        Table mainTable = new Table(1);
        mainTable.setWidth(750);

        mainTable.addCell(createCell("Otorgado a:", TextAlignment.LEFT, 1, 2));

        String nombreCompleto = participante.getNombre() + " " + participante.getPaterno() + " "
                + participante.getMaterno();
        mainTable.addCell(createCell(nombreCompleto, TextAlignment.CENTER, 18));
        mainTable.addCell(salto("\n"));
        mainTable.addCell(createCell("Por haber aprobado....bla bla bla", TextAlignment.LEFT, 1, 2));
        mainTable.setMarginBottom(45);
        return mainTable;

    }

    private Table ponerFirmas(List<AutoridadModel> autoridades) {
        Table firmasTable = new Table(autoridades.size());
        firmasTable.setWidth(750);
        firmasTable.setBorder(Border.NO_BORDER);

        try {
            for (AutoridadModel autoridad : autoridades) {

                Table innerTable = new Table(1);
                innerTable.setWidth(UnitValue.createPercentValue(100));
                innerTable.setBorder(Border.NO_BORDER);

                // Celda para la firma
                String rutaFirma = "C:/workspace/app/src/main/resources/static/Recursos/Firmas/firma1.png";
                Image firma = new Image(ImageDataFactory.create(rutaFirma));
                Cell firmaCell = new Cell().add(firma.setAutoScale(true));
                firmaCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                firmaCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                firmaCell.setHeight(90);
                firmaCell.setBorder(Border.NO_BORDER);
                innerTable.addCell(firmaCell);

                String nombreCompleto = autoridad.getNombre() + " " + autoridad.getPaterno() + " "
                        + autoridad.getMaterno();
                Cell nombreCell = createCell(nombreCompleto, TextAlignment.CENTER, 12);
                innerTable.addCell(nombreCell);

                firmasTable.addCell(innerTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return firmasTable;
    }

    private String buscarImagenFondoPath(Long eventoId) {
        Optional<EventoModel> optionalEvento = eventoRepository.findById(eventoId);
        if (optionalEvento.isPresent()) {
            EventoModel evento = optionalEvento.get();
            String nombreImagenFondo = evento.getImagenFondo();
            if (nombreImagenFondo != null && !nombreImagenFondo.isEmpty()) {
                String directorioImagenes = "C:/workspace/app/src/main/resources/static/Recursos/Fondos/";
                Path rutaImagen = Paths.get(directorioImagenes + nombreImagenFondo);
                return rutaImagen.toString();
            }
        }
        return "no hay imagen de fondo";
    }

    // cargo
    private Cell createCell(String nombreCompleto, TextAlignment alignment, int fontSize) {
        Paragraph paragraph = new Paragraph(nombreCompleto)
                .setTextAlignment(alignment)
                .setFontSize(fontSize);

        Cell nombreCell = new Cell().add(paragraph);
        nombreCell.setBorder(Border.NO_BORDER);
        return nombreCell;
    }

    // header
    private Cell createCellUp(String content, TextAlignment alignment, int rowspan, int colspan) {
        Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content));
        cell.setTextAlignment(alignment);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setFontSize(20);
        cell.setBold();
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    // main table
    private Cell createCell(String content, TextAlignment alignment, int rowspan, int colspan) {
        Cell cell = new Cell(rowspan, colspan).add(new Paragraph(content));
        cell.setTextAlignment(alignment);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setFontSize(18);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    private Cell salto(String salto) {
        Cell cell = new Cell().add(new Paragraph(salto));
        cell.setBorder(Border.NO_BORDER);
        return cell;

    }

}
