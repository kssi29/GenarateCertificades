package com.pvae.app.util;

import java.io.FileOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;


@Service
public class GeneraPDFUtil {
      public String generarPDF(String htmlprocesado) throws java.io.IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileOutputStream file = null;
    
            try {
                PdfWriter pdfWriter = new PdfWriter(baos);
                PdfDocument pdfDocument  = new PdfDocument(pdfWriter);
                pdfDocument.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4.rotate());

        

                DefaultFontProvider fontProvider = new DefaultFontProvider(false, true, false);
                ConverterProperties properties = new ConverterProperties();
                properties.setFontProvider(fontProvider);
                HtmlConverter.convertToPdf(htmlprocesado, pdfDocument, properties);
    
                // Cambia esta ruta a donde quieras guardar el archivo
                file = new FileOutputStream("C:\\workspace\\app\\src\\main\\resources\\static\\cosasGeneradas\\output.pdf");
                baos.writeTo(file);
    
                return "PDF generado con éxito";
    
            } catch (Exception e) {
                e.printStackTrace();
                return "Error al generar el PDF: " + e.getMessage();
            } finally {
                try {
                    if (baos != null) {
                        baos.close();
                    }
                    if (file != null) {
                        file.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

}
