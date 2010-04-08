package net.alexguev.kindlethat.web;

import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;

public class WebSiteDownloaderTest {

    @Test
    public void shouldDownloadWebPageWithNoImages() throws Exception {
        //FIXME: don't hit google, embed jetty instead.
        WebSiteDownloader downloader = new WebSiteDownloader();
        WebSite site = downloader.download(new URI("http://blog.alexguev.net"));
        assertTrue("one page with content", site.getRootPage().getContent().contains("Alexei Guevara's Blog"));
    }

}
