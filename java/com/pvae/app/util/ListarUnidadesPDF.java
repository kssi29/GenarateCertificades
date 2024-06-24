package com.pvae.app.util;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.pvae.app.models.UnidadModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("consultas/unidades/unidad")
public class ListarUnidadesPDF extends AbstractPdfView {
      @Override
      protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                  HttpServletRequest request,
                  HttpServletResponse response) throws Exception {

            @SuppressWarnings("unchecked")
            List<UnidadModel> listaUnidades = (List<UnidadModel>) model.get("listaunidades");

            document.setPageSize(PageSize.LETTER.rotate());
            document.addTitle("Listado de Unidades");
            document.open();

            Table titulo = new Table(1);
          
            Font font = FontFactory.getFont("Comic Sans MS", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
            Cell cell = null;
            cell = new Cell(new Paragraph("Listado de Unidades",font));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHeader(true);
            titulo.addCell(cell);
            cell.setBorder(0);
          


           
            document.add(titulo);

            Table tabla = new Table(3);
            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Pertenece a");
            listaUnidades.forEach(unidad -> {
                  tabla.addCell(unidad.getIdunidad().toString());
                  tabla.addCell(unidad.getNombre());

                  String nombreUnidadPadre = (unidad.getUnidadPadre() != null) ? unidad.getUnidadPadre().getNombre()
                              : "Ninguno";
                  tabla.addCell(nombreUnidadPadre);
            });

            document.add(tabla);
      }

}
