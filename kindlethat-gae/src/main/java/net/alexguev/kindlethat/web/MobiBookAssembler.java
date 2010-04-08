package net.alexguev.kindlethat.web;

import org.springframework.stereotype.Component;

@Component
class MobiBookAssembler {
	
    MobiBook assemble(WebSite site) {
        MobiBook book = new MobiBook(site.getRootPage().getTitle());
        book.getParts().add(toPart(site.getRootPage()));
        return book;
    }

	private MobiBookPart toPart(WebPage page) {
		return new MobiBookPart(page.getContent());
	}
}