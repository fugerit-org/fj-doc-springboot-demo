package org.fugerit.java.demo.fjdocspringbootdemo.doc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.fugerit.java.doc.base.config.DocInput;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.config.DocTypeHandlerXMLUTF8;
import org.fugerit.java.doc.base.process.DocProcessContext;
import org.fugerit.java.doc.base.process.DocProcessData;
import org.fugerit.java.doc.base.typehandler.markdown.SimpleMarkdownExtTypeHandler;
import org.fugerit.java.doc.freemarker.html.FreeMarkerHtmlTypeHandlerUTF8;
import org.fugerit.java.doc.mod.fop.PdfFopTypeHandler;
import org.fugerit.java.doc.mod.openpdf.PdfTypeHandler;
import org.fugerit.java.doc.mod.poi5.XlsxPoi5TypeHandler;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/docsb/")
@RestController
public class DocDemoRest {

	private ResponseEntity<InputStreamSource> demoFmHelper( DocTypeHandler handler ) {
		ResponseEntity<InputStreamSource> response = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DocProcessData data = new DocProcessData();
			TestInfo testInfo = new TestInfo();
			testInfo.setName( "Luthien" );
			testInfo.setSurname( "Tinuviel" );
			DocProcessContext context = DocProcessContext.newContext( "testInfo", testInfo );
			FugeritDemoDocFacade.getConfig().process( "demo" ,context, data );
			handler.handle( DocInput.newInput( handler.getType() , data.getCurrentXmlReader() ) , DocOutput.newOutput( baos ) );
			response = new ResponseEntity<>( new InputStreamResource( new ByteArrayInputStream(baos.toByteArray()) ), HttpStatus.OK);
		} catch (Exception e) {
			log.error( "Error : "+e, e );
			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
 	@GetMapping( value = "/demo_openpdf.pdf" , produces = MediaType.APPLICATION_PDF_VALUE )
	public ResponseEntity<InputStreamSource> getDemoOpenPdf() {
		return this.demoFmHelper( PdfTypeHandler.HANDLER );
	}
 	
 	@GetMapping( value = "/demo_fop.pdf" , produces = MediaType.APPLICATION_PDF_VALUE )
	public ResponseEntity<InputStreamSource> getDemoFopPdf() {
		return this.demoFmHelper( PdfFopTypeHandler.HANDLER );
	}
 	
 	@GetMapping( value = "/demo.xlsx" , produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" )
	public ResponseEntity<InputStreamSource> getDemoXlsx() {
		return this.demoFmHelper( XlsxPoi5TypeHandler.HANDLER );
	}
 	
 	@GetMapping( value = "/demo.xml" , produces = MediaType.TEXT_XML_VALUE )
	public ResponseEntity<InputStreamSource> getDemoXml() {
		return this.demoFmHelper( DocTypeHandlerXMLUTF8.HANDLER );
	}
 	
 	@GetMapping( value = "/demo.html" , produces = MediaType.TEXT_HTML_VALUE )
	public ResponseEntity<InputStreamSource> getDemoHtml() {
		return this.demoFmHelper( FreeMarkerHtmlTypeHandlerUTF8.HANDLER );
	}

 	@GetMapping( value = "/demo.md" , produces = MediaType.TEXT_MARKDOWN_VALUE )
	public ResponseEntity<InputStreamSource> getDemoMd() {
		return this.demoFmHelper( SimpleMarkdownExtTypeHandler.HANDLER_NOCOMMENTS_UTF8 );
	}

	
}
