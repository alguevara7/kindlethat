package net.alexguev.kindlethat.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.alexguev.kindlethat.core.SizeAwareResource;

import org.apache.commons.io.IOUtils;

class MobiBook {

	private final String title;
	private final List<MobiBookPart> parts = new ArrayList<MobiBookPart>();
	
	private final MobiBookGenerator generator;
	private final StreamCopier streamCopier;

	MobiBook(String title) {
		this(title, new MobiBookGenerator(), new StreamCopier());
	}

	MobiBook(String title, MobiBookGenerator generator, StreamCopier streamCopier) {
		this.title = title;
		this.generator = generator;
		this.streamCopier = streamCopier;
	}

	void writeTo(HttpServletResponse response) throws IOException {
		SizeAwareResource resource = this.generator.generate(this);
		response.setContentType("application/octet-stream");
		response.setContentLength((int) resource.length());
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", resource.getFilename()));
		this.streamCopier.copy(resource.getInputStream(), response.getOutputStream());
		
	}

	String getTitle() {
		return this.title;
	}

	List<MobiBookPart> getParts() {
		return this.parts;
	}
	
	static class StreamCopier {
		void copy(InputStream input, ServletOutputStream output) throws IOException {
			IOUtils.copy(input, output);
		}
	}
	
}