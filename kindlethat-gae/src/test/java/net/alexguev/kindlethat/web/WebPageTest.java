package net.alexguev.kindlethat.web;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WebPageTest {

    @Test
    public void shouldParseTitle() throws Exception {
        WebPage page = new WebPage("<html><title>t’tulo</title><body></body></html>");
        assertEquals("t’tulo", page.getTitle());
    }

}
