package com.pvae.app.controllers;

/* 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.pvae.app.models.UsuarioModel;
import com.pvae.app.repositories.UnidadRepository;
import com.pvae.app.servicies.UnidadService;
import com.pvae.app.util.GeneraPDFUtil;

@Controller */
public class GeneraCertificadoController {
/* 
      @Autowired
      private GeneraPDFUtil generaPDFUtil;


      //Data Mapper es el Service
      @Autowired
      private  UnidadRepository unidadRepository;

      @Autowired
      private UnidadService unidadService;

      @Autowired
	private SpringTemplateEngine springTemplateEngine;


      @PostMapping("/generaCertificado")
      public String generaCertificado(@RequestBody List <UsuarioModel> UsuarioModel) 
      {
            String finalHtml = null;

            //no se cual
        //    Context context= unidadRepository.
         //   Context context = unidadService.
            finalHtml = springTemplateEngine.process("certificado", context);
            generaPDFUtil.generarPDF(finalHtml);
            return "Certificado generado";
      }
*/

      
}
