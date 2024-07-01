package com.pvae.app.servicies;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SubirArchivoService {

    public String procesarExcel(MultipartFile archivo) {
        try {
            InputStream inputStream = archivo.getInputStream();

            // Crear un Workbook a partir del InputStream del archivo
            Workbook workbook = WorkbookFactory.create(inputStream);

            StringBuilder contenido = new StringBuilder();

            // Iterar por todas las hojas del libro (workbook)
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                contenido.append("Hoja ").append(i).append(":\n");

                // Iterar por todas las filas de la hoja actual
                for (Row row : sheet) {
                    // Iterar por todas las celdas de la fila actual
                    for (Cell cell : row) {
                        // Agregar el contenido de la celda al StringBuilder
                        contenido.append(cell.toString()).append("\t");
                    }
                    contenido.append("\n");
                }
                contenido.append("\n");
            }

            workbook.close(); // Cerrar el Workbook después de usarlo

            return "Contenido del archivo Excel " + archivo.getOriginalFilename() + ":\n" + contenido.toString();
        } catch (IOException | EncryptedDocumentException ex) {
            return "Error al procesar el archivo: " + ex.getMessage();
        }
    }
}
