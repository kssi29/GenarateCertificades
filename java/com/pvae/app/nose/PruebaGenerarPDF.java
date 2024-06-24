package com.pvae.app.nose;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.pvae.app.util.GeneraPDFUtil;
import com.pvae.app.util.ImageUtil;

public class PruebaGenerarPDF {

    public static void main(String[] args) {
        // Ruta del archivo HTML en tu proyecto
        String htmlFilePath = "C:\\workspace\\app\\src\\main\\resources\\templates\\generaCertificados\\prueba.html";

        try {
            // Lee el contenido del archivo HTML
            String html = new String(Files.readAllBytes(Paths.get(htmlFilePath)));

            // Ejemplo de cómo podrías usar base64 para las imágenes
            String firma1Base64 = ImageUtil.encodeImageToBase64("C:\\workspace\\app\\src\\main\\resources\\static\\img\\recursosCert\\firma1.png");
            String firma2Base64 = ImageUtil.encodeImageToBase64("C:\\workspace\\app\\src\\main\\resources\\static\\img\\recursosCert\\firma2.png");

            // Sustituye las etiquetas de imagen en el HTML con base64
            html = html.replace("{{firma1Placeholder}}", "data:image/png;base64," + firma1Base64);
            html = html.replace("{{firma2Placeholder}}", "data:image/png;base64," + firma2Base64);

            // Genera el PDF
            GeneraPDFUtil generaPDFUtil = new GeneraPDFUtil();
            String resultado = generaPDFUtil.generarPDF(html);
            System.out.println(resultado);

        } catch (IOException e) {
            // Manejo de errores al leer el archivo HTML
            e.printStackTrace();
        }
    }
}
