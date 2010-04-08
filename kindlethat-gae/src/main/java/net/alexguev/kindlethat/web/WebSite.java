package net.alexguev.kindlethat.web;


class WebSite {

    private final WebPage rootPage;

    WebSite(WebPage rootPage) {
        this.rootPage = rootPage;
    }

    WebPage getRootPage() {
        return this.rootPage;
    }

}