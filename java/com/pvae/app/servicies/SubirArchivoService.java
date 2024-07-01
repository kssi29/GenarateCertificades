package com.pvae.app.servicies;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pvae.app.models.ParticipanteModel;

import jakarta.transaction.Transactional;

@Service
public class SubirArchivoService {

    @Autowired
    private ParticipanteService participanteService;

    @Transactional
    public String procesarYGuardarExcel(MultipartFile archivo) {
        try {
            InputStream inputStream = archivo.getInputStream();

            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = sheet.iterator();

            if (iterator.hasNext()) {
                iterator.next();
            }

            // List para almacenar los participantes a guardar en la base de datos
            List<ParticipanteModel> participantes = new ArrayList<>();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                int ci = (int) cellIterator.next().getNumericCellValue(); // CI
                String email = cellIterator.next().getStringCellValue(); // EMAIL
                String paterno = cellIterator.next().getStringCellValue(); // PATERNO
                String materno = cellIterator.next().getStringCellValue(); // MATERNO
                String nombre = cellIterator.next().getStringCellValue(); // NOMBRE
                int tipo = (int) cellIterator.next().getNumericCellValue(); // TIPO

                ParticipanteModel participante = new ParticipanteModel(String.valueOf(tipo), ci, email, materno, nombre,
                        paterno);
                participantes.add(participante);
            }

            for (ParticipanteModel participante : participantes) {
                participanteService.guardarParticipante(participante);
            }

            workbook.close();

            return "Se han guardado exitosamente los participantes desde el archivo Excel "
                    + archivo.getOriginalFilename();
        } catch (IOException | EncryptedDocumentException ex) {
            return "Error al procesar el archivo: " + ex.getMessage();
        }
    }

}
