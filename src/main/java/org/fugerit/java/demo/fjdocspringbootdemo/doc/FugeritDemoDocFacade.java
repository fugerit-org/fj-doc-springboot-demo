package org.fugerit.java.demo.fjdocspringbootdemo.doc;

import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfig;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfigFacade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FugeritDemoDocFacade {

	private final static String PATH = "cl://fj-doc-demo/freemarker-doc-process.xml";
	
	static {
		try {
			Class.forName( "org.fugerit.java.doc.base.typehandler.markdown.SimpleMarkdownExtTypeHandler" );
		} catch (ClassNotFoundException e) {
			log.error( "Init test "+e, e );
		}
	}
	
	private static final FreemarkerDocProcessConfig CONFIG = FreemarkerDocProcessConfigFacade.loadConfigSafe( PATH );
	
	public static FreemarkerDocProcessConfig getConfig() {
		return CONFIG;
	}
	
}
