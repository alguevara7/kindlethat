package net.alexguev.kindlethat.web;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;

public class VelocityFacade {
	
	private final String templateName;
	
	private VelocityEngine engine;
	private Template template;

	VelocityFacade(String templateName) {
		this.templateName = templateName;
	}

	void merge(Map<String, Object> context, Writer writer) throws Exception, IOException {
		initEngine();
		loadTemplate();
	    try {
	    	this.template.merge(new VelocityContext(context), writer);
	    } finally {
	    	writer.flush();
	    }
	}

	private void loadTemplate() throws Exception {
		if (this.template==null) {
			this.template = this.engine.getTemplate(this.templateName);
		}
	}

	private void initEngine() throws Exception, IOException {
		if (this.engine==null) {
			this.engine = new VelocityEngine();
			this.engine.init(loadVelocityProperties());
		}
	}

	Properties loadVelocityProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("velocity.properties").getInputStream());
		return properties;
	}

}
