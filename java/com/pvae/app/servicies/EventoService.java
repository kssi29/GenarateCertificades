package com.pvae.app.servicies;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import com.pvae.app.models.AutoridadModel;
import com.pvae.app.models.ParticipanteModel;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import com.pvae.app.models.EventoModel;

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

    public EventoService(EventoRepository eventoRepository, CertificadoService certificadoService, ParticipanteService participanteService, AutoridadService autoridadService, FirmaCertService firmaCertService) {
        this.eventoRepository = eventoRepository;
        this.certificadoService = certificadoService;
        this.participanteService = participanteService;
        this.autoridadService = autoridadService;
        this.firmaCertService = firmaCertService;
    }

    public List<EventoModel> listarEventos() {

        return (List<EventoModel>) eventoRepository.findAll();
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
    public EventoModel guardarEvento(EventoModel evento) {
        eventoRepository.save(evento);
        return evento;
    }

    @Transactional
    public void eliminarEvento(Long idevento) {
        eventoRepository.deleteById(idevento);
    }

    @Transactional
    public String procesarYGuardarExcel(MultipartFile archivo, Long eventoId, Model model) {
        try {
            System.err.println("\nEstamos procesar y guardar el excel");
            EventoModel evento = buscarEvento(eventoId);

            InputStream inputStream = archivo.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            if (iterator.hasNext()) {
                iterator.next();
            }

            List<AutoridadModel> autoridades = null;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                int ci = (int) cellIterator.next().getNumericCellValue();
                String email = cellIterator.next().getStringCellValue();
                String paterno = cellIterator.next().getStringCellValue();
                String materno = cellIterator.next().getStringCellValue();
                String nombre = cellIterator.next().getStringCellValue();
                String tipo = String.valueOf((int) cellIterator.next().getNumericCellValue());
                if (tipo.equals("0")) {
                    AutoridadModel autoridad = autoridadService.obtenerAutoridades(ci, email, paterno, materno, nombre);
                    firmaCertService.ligarFirmaCert(evento, autoridad);
                } else {
                    ParticipanteModel participante = participanteService.obtenerOPersistirParticipante(ci, email, paterno, materno, nombre, tipo);
                    certificadoService.guardarCertificado(evento, participante);
                }
            }

            workbook.close();
            inputStream.close();

            model.addAttribute("autoridades", autoridades);
            model.addAttribute("eventoId", eventoId);

            return "Se han guardado exitosamente los participantes desde el archivo Excel para el evento con id " + eventoId;
        } catch (IOException | EncryptedDocumentException ex) {
            return "Error al procesar el archivo Excel: " + ex.getMessage();
        }
    }


}
