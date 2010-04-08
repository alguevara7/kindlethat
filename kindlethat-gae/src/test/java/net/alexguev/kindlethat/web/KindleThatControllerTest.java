package net.alexguev.kindlethat.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

public class KindleThatControllerTest {

    private final WebSiteDownloader downloader = mock(WebSiteDownloader.class);
    private final MobiBookAssembler assembler = mock(MobiBookAssembler.class);

    private final MobiBook book = mock(MobiBook.class);

    private KindleThatController controller;

    @Before
    public void setUp() {
        this.controller = new KindleThatController(this.downloader, this.assembler);
    }

    @Test
    public void shouldWriteGeneratedMobiInBinaryFormatToResponse() throws Exception {
        HttpServletResponse response = null;
        URI uri = new URI("http://localhost/");

        WebSite webSite = new WebSite(null);
        when(this.downloader.download(uri)).thenReturn(webSite);
        when(this.assembler.assemble(webSite)).thenReturn(this.book);

        this.controller.htmlToMobi(uri, response);

        verify(this.book).writeTo(response);
    }

}
