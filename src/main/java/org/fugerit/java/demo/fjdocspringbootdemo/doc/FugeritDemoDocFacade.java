package org.fugerit.java.demo.fjdocspringbootdemo.doc;

import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfig;
import org.fugerit.java.doc.freemarker.process.FreemarkerDocProcessConfigFacade;

public class FugeritDemoDocFacade {

	private final static String PATH = "cl://fj-doc-demo/freemarker-doc-process.xml";
	
	private static final FreemarkerDocProcessConfig CONFIG = FreemarkerDocProcessConfigFacade.loadConfigSafe( PATH );
	
	public static FreemarkerDocProcessConfig getConfig() {
		return CONFIG;
	}
	
}
