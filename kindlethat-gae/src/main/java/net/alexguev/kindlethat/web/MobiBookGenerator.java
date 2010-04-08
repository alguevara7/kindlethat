package net.alexguev.kindlethat.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.alexguev.kindlethat.core.SizeAwareResource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

class MobiBookGenerator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MobiBookGenerator.class);
	
	SizeAwareResource generate(MobiBook book) {
		File outputFolder = createOutputFolder(book.getTitle());
		File opfFile = writeBookSourceToFileSystem(book, outputFolder);
		compileBookSource(opfFile, outputFolder);
		
		System.out.println("writen to: " + outputFolder.getAbsolutePath());
		
		return new ClasspathSizeAwareResource(new ClassPathResource("Understanding_Git.mobi"));
	}

	private File createOutputFolder(String title) {
		File outputFolder = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
		outputFolder.mkdirs();
		return outputFolder;
	}

	private void compileBookSource(File opfFile, File outputFolder) {
		ProcessBuilder builder = new ProcessBuilder("/bin/sh", "-c", "/Users/alexguev/Library/Amazon/KindleGen/kindlegen " + opfFile.getName());
		builder.directory(new File(outputFolder, "/"));
		try {
			Process process = builder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = reader.readLine();
			while (line!=null) {
				System.out.println(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File writeBookSourceToFileSystem(MobiBook book, File outputFolder) {
		List<File> parts = writeParts(book.getParts(), outputFolder);
		try {
			File toc = writeToc(parts, outputFolder);
			return writeOpf(book.getTitle(), parts, toc, outputFolder);
		} catch (Exception e) {
			LOGGER.error("Unable to write OPF file for " + book, e);
			throw new RuntimeException(e);
		}
	}

	private File writeToc(List<File> parts, File outputFolder) throws Exception {
		File file = File.createTempFile("toc", ".html", outputFolder);
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("parts", parts);

		Writer writer = new FileWriter(file);
		
		final VelocityFacade velocity = new VelocityFacade("toc.vm");
		try {
			velocity.merge(context, writer);
		} finally {
			writer.close();
		}
        
		return file;
	}

	private File writeOpf(String title, List<File> parts, File toc, File outputFolder) throws Exception {
		File file = File.createTempFile("opf", ".opf", outputFolder);
		
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("title", title);
        context.put("parts", parts);

        FileWriter writer = new FileWriter(file);
        
		final VelocityFacade velocity = new VelocityFacade("opf.vm");
		try {
			velocity.merge(context, writer);
		} finally {
			writer.close();
		}
        
		return file;
	}

	private List<File> writeParts(List<MobiBookPart> parts, File outputFolder) {
		ArrayList<File> files = new ArrayList<File>();
		for (MobiBookPart part : parts) {
			try {
				files.add(writePart(part, outputFolder));
			} catch (IOException e) {
				LOGGER.error("Unable to write part: " + part, e);
			}
		}
		return files;
	}

	private File writePart(MobiBookPart part, File outputFolder) throws IOException {
		File file = File.createTempFile("item", ".html", outputFolder);
		FileUtils.writeStringToFile(file, part.getContent());
		return file;
	}

	static class ClasspathSizeAwareResource implements SizeAwareResource {
		
		private final Resource resource;
		
		ClasspathSizeAwareResource(Resource resource) {
			this.resource = resource;
		}

		@Override
		public boolean exists() {
			return this.resource.exists();
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return this.resource.getInputStream();
		}

		@Override
		public boolean isReadable() {
			return this.resource.isReadable();
		}

		@Override
		public boolean isOpen() {
			return this.resource.isOpen();
		}

		@Override
		public URL getURL() throws IOException {
			return this.resource.getURL();
		}

		@Override
		public URI getURI() throws IOException {
			return this.resource.getURI();
		}

		@Override
		public File getFile() throws IOException {
			return this.resource.getFile();
		}

		@Override
		public long lastModified() throws IOException {
			return this.resource.lastModified();
		}

		@Override
		public Resource createRelative(String relativePath) throws IOException {
			return this.resource.createRelative(relativePath);
		}

		@Override
		public String getFilename() {
			return this.resource.getFilename();
		}

		@Override
		public String getDescription() {
			return this.resource.getDescription();
		}

		@Override
		public long length() {
			try {
				return getFile().length();
			} catch (IOException e) {
				return 0;
			}
		}

	}
	
	public static void main(String[] args) {
		MobiBookGenerator generator = new MobiBookGenerator();
		MobiBook book = new MobiBook("Test");
		book.getParts().add(new MobiBookPart("<html><head>title</head><body>Hello world :)</body></html>"));
		generator.generate(book);
	}
}
