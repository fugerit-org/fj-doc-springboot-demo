package org.fugerit.java.demo.fjdocspringbootdemo.doc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.fugerit.java.doc.base.config.DocInput;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.process.DocProcessContext;
import org.fugerit.java.doc.base.process.DocProcessData;
import org.fugerit.java.doc.mod.fop.PdfFopTypeHandler;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DocDemoRest {

 	@GetMapping( value = "/demo.pdf" , produces = MediaType.APPLICATION_PDF_VALUE )
	public ResponseEntity<InputStreamSource> getList() {
		ResponseEntity<InputStreamSource> response = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DocProcessData data = new DocProcessData();
			FugeritDemoDocFacade.getConfig().process( "demo" ,DocProcessContext.newContext(), data );
			DocTypeHandler handler = PdfFopTypeHandler.HANDLER;
			handler.handle( DocInput.newInput( handler.getType() , data.getCurrentXmlReader() ) , DocOutput.newOutput( baos ) );
			response = new ResponseEntity<>( new InputStreamResource( new ByteArrayInputStream(baos.toByteArray()) ), HttpStatus.OK);
		} catch (Exception e) {
			log.error( "Error : "+e, e );
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
}
