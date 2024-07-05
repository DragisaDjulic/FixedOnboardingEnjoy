package com.onboarding.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import com.onboarding.service.PDFGeneratorService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Controller
public class PDFExportController {
	
	//@Autowired
	//private PDFGeneratorService pdfGeneratorService;
	
	@GetMapping("/pdf/generate")
	public void generatePDF(HttpServletResponse response) throws IOException{
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime=dateFormatter.format(new Date());
		
		
		String headerKey="Content-Disposition";
		String headerValue = "attachment;filename=pdf_" + currentDateTime + ".pdf";
		
		response.setHeader(headerKey, headerValue);
		
//		this.pdfGeneratorService.export();
	}
}
