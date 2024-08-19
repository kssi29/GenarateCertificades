package com.pvae.app.servicies;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pvae.app.models.*;
import com.pvae.app.repositories.AutoridadRepository;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import com.pvae.app.repositories.EventoRepository;

import jakarta.transaction.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


@Service
public class EventoService {
    private final EventoRepository eventoRepository;
    private final CertificadoService certificadoService;
    private final ParticipanteService participanteService;
    private final AutoridadService autoridadService;
    private final FirmaCertService firmaCertService;
    private final AutoridadRepository autoridadRepository;
    private final UnidadService unidadService;
    private static final String DIRECTORIO_DESTINO = "C:/workspace/app/src/main/resources/static/Recursos/Fondos/";


    public EventoService(EventoRepository eventoRepository, CertificadoService certificadoService,
                         ParticipanteService participanteService, AutoridadService autoridadService,
                         FirmaCertService firmaCertService, AutoridadRepository autoridadRepository, UnidadService unidadService) {
        this.eventoRepository = eventoRepository;
        this.certificadoService = certificadoService;
        this.participanteService = participanteService;
        this.autoridadService = autoridadService;
        this.firmaCertService = firmaCertService;
        this.autoridadRepository = autoridadRepository;
        this.unidadService = unidadService;
    }

    public List<EventoModel> listarEventos() {
        return eventoRepository.findAllByOrderByIdeventoAsc();
    }


    public EventoModel buscarEvento(Long id) {

        return eventoRepository.findById(id).orElse(null);
    }


    public EventoModel buscarEventoPorNombre(String nombre) {
        List<EventoModel> listaEventos = (List<EventoModel>) this.eventoRepository.findAll();
        for (EventoModel evento : listaEventos) {
            if (evento.getNombre().equals(nombre)) {
                return evento;
            }
        }
        return null;
    }


    @Transactional
    public void eliminarEvento(Long idevento) {
        eventoRepository.deleteById(idevento);
    }


    @Transactional
    public boolean procesarYGuardarExcel(MultipartFile archivo, Long eventoId, Model model) {
        try {
            System.err.println("\nEstamos procesar y guardar el excel");
            EventoModel evento = buscarEvento(eventoId);

            InputStream inputStream = archivo.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            Map<Long, CertificadoModel> certificadosMap = new HashMap<>();
            Map<Long, AutoridadModel> autoridadesMap = new HashMap<>();

            if (iterator.hasNext()) {
                iterator.next();
            }
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                int ci = (int) cellIterator.next().getNumericCellValue();
                String email = cellIterator.next().getStringCellValue();
                String paterno = cellIterator.next().getStringCellValue();
                String materno = cellIterator.next().getStringCellValue();
                String nombre = cellIterator.next().getStringCellValue();
                String tipo = String.valueOf((int) cellIterator.next().getNumericCellValue());

                AutoridadModel autoridadExiste = autoridadRepository.findByCi(ci);
                if (tipo.equals("0")) {
                    if (autoridadExiste == null) {
                        AutoridadModel autoridad = autoridadService.guardaAutoridad(ci, email, paterno, materno, nombre);
                        autoridadesMap.put(autoridad.getIdpersona(), autoridad);
                    } else {
                        autoridadesMap.put(autoridadExiste.getIdpersona(), autoridadExiste);
                    }


                } else {

                    ParticipanteModel participante = participanteService.obtenerOPersistirParticipante(ci, email, paterno, materno, nombre, tipo);
                    if (!certificadoService.existeCertificado(participante.getIdpersona(), evento.getIdevento())) {
                        CertificadoModel certificado = certificadoService.guardarCertificado(evento, participante);
                        certificadosMap.put(certificado.getIdcertificado(), certificado);
                    }

                }
            }
            workbook.close();
            inputStream.close();

            asociarFirmas(certificadosMap, autoridadesMap);

            return true;

        } catch (IOException | EncryptedDocumentException ex) {
            System.err.println(ex.getMessage());
            model.addAttribute("error", "Error al procesar el archivo Excel: " + ex.getMessage());
            return false;
        }
    }


    public EventoModel guardarEventoConImagen(String nombre, Long unidadId, MultipartFile imagenFondo) throws IOException {
        EventoModel evento = new EventoModel();
        evento.setNombre(nombre);

        UnidadModel unidad = unidadService.buscarUnidad(unidadId);
        evento.setUnidad(unidad);

        EventoModel eventoGuardado = eventoRepository.save(evento);
        return procesarImagenYGuardarEvento(eventoGuardado, imagenFondo);
    }


    public EventoModel actualizarEventoConImagen(Long idevento, String nombre, Long unidadId, MultipartFile imagenFondo) throws IOException {
        EventoModel evento = eventoRepository.findById(idevento)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + idevento));
        evento.setNombre(nombre);

        UnidadModel unidad = unidadService.buscarUnidad(unidadId);
        evento.setUnidad(unidad);

        EventoModel eventoGuardado = eventoRepository.save(evento);
        return procesarImagenYGuardarEvento(eventoGuardado, imagenFondo);
    }


    private EventoModel procesarImagenYGuardarEvento(EventoModel evento, MultipartFile imagenFondo) throws IOException {
        if (imagenFondo != null && !imagenFondo.isEmpty()) {
            String nombreArchivo = evento.getIdevento() + "_" + imagenFondo.getOriginalFilename();
            Path rutaCompleta = Paths.get(DIRECTORIO_DESTINO + nombreArchivo);
            Files.write(rutaCompleta, imagenFondo.getBytes());

            evento.setImagenFondo(nombreArchivo);
            evento = eventoRepository.save(evento);
        }
        return evento;
    }


    private void asociarFirmas(Map<Long, CertificadoModel> certificadosMap, Map<Long, AutoridadModel> autoridadesMap) {
        for (Map.Entry<Long, CertificadoModel> certificadoEntry : certificadosMap.entrySet()) {
            Long certificadoId = certificadoEntry.getKey();
            CertificadoModel certificado = certificadoEntry.getValue();

            for (Map.Entry<Long, AutoridadModel> autoridadEntry : autoridadesMap.entrySet()) {
                Long autoridadId = autoridadEntry.getKey();
                AutoridadModel autoridad = autoridadEntry.getValue();

                if (certificado != null && autoridad != null) {
                    FirmaCertModel firma = new FirmaCertModel();
                    firma.setCertificado(certificado);
                    firma.setAutoridad(autoridad);

                    firmaCertService.guardarFirmaCert(firma);
                } else {
                    if (certificado == null) {
                        System.err.println("Certificado nulo para ID: " + certificadoId);
                    }
                    if (autoridad == null) {
                        System.err.println("Autoridad nula para ID: " + autoridadId);
                    }
                }
            }
        }

    }


}
