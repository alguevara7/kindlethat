package net.alexguev.kindlethat.web;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class WebSiteDownloader {

    private final RestTemplate template = new RestTemplate();

    WebSite download(URI uri) {
        String content = this.template.getForObject(uri, String.class);
        WebPage rootPage = new WebPage(content);
        return new WebSite(rootPage);
    }
}