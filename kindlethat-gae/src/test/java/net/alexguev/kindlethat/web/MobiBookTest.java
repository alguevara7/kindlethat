package net.alexguev.kindlethat.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.alexguev.kindlethat.core.SizeAwareResource;
import net.alexguev.kindlethat.web.MobiBook.StreamCopier;

import org.junit.Test;

public class MobiBookTest {
	
	private final MobiBookGenerator generator = mock(MobiBookGenerator.class);
	private final HttpServletResponse response = mock(HttpServletResponse.class);
	
	private final ServletOutputStream servletOutputStream = mock(ServletOutputStream.class);
	private final InputStream bookInputStream = mock(InputStream.class);
	private final StreamCopier streamCopier = mock(StreamCopier.class);

	@Test
	public void should_write_downloadable_mobi_file() throws Exception {
		MobiBook book = new MobiBook("title", this.generator, this.streamCopier);
		
		SizeAwareResource resource = mock(SizeAwareResource.class);
		when(resource.length()).thenReturn(100L);
		when(resource.getInputStream()).thenReturn(this.bookInputStream);
		
		when(this.generator.generate(book)).thenReturn(resource);
		when(this.response.getOutputStream()).thenReturn(this.servletOutputStream);
		book.writeTo(this.response);
		
		verify(this.streamCopier).copy(this.bookInputStream, this.servletOutputStream);
		verify(this.response).setContentType("application/octet-stream");
		verify(this.response).setContentLength(100);
		verify(this.response).setHeader("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
		
	}

}
