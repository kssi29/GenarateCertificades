package com.pvae.app.servicies;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pvae.app.models.EventoModel;
import com.pvae.app.models.ParticipanteModel;

import jakarta.transaction.Transactional;

@Service
public class SubirArchivoService {

    @Autowired
    private ParticipanteService participanteService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private CertificadoService certificadoService;
    


    @Transactional
    public String procesarYGuardarExcel(MultipartFile archivo, Long eventoId) {
        try {
            EventoModel evento = eventoService.buscarEvento(eventoId);
            if (evento == null) {
                throw new RuntimeException("Evento no encontrado");
            }

            InputStream inputStream = archivo.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            // Saltar la primera fila si es un encabezado
            if (iterator.hasNext()) {
                iterator.next();
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                int ci = (int) cellIterator.next().getNumericCellValue(); // CI
                String email = cellIterator.next().getStringCellValue(); // EMAIL
                String paterno = cellIterator.next().getStringCellValue(); // PATERNO
                String materno = cellIterator.next().getStringCellValue(); // MATERNO
                String nombre = cellIterator.next().getStringCellValue(); // NOMBRE
                int tipo = (int) cellIterator.next().getNumericCellValue(); // TIPO

                // Buscar o crear el participante
                ParticipanteModel participante = participanteService.obtenerOPersistirParticipante(ci, email, paterno, materno, nombre, String.valueOf(tipo));

                // Crear y guardar el certificado asociado al participante y evento
                certificadoService.guardarCertificado(evento, participante);
            }

            workbook.close();
            inputStream.close();

            return "Se han guardado exitosamente los participantes desde el archivo Excel para el evento con id " + eventoId;
        } catch (IOException | EncryptedDocumentException ex) {
            return "Error al procesar el archivo Excel: " + ex.getMessage();
        }
    }

   

}
