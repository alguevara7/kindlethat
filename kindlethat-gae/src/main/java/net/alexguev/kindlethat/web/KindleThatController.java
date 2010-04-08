package net.alexguev.kindlethat.web;

import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class KindleThatController {

	private final WebSiteDownloader downloader;
	private final MobiBookAssembler assembler;

	@Autowired
	public KindleThatController(WebSiteDownloader downloader, MobiBookAssembler mobiBookAssembler) {
		this.downloader = downloader;
		this.assembler = mobiBookAssembler;
	}

	@RequestMapping(value="htmlToMobi", method=RequestMethod.GET)
	public void htmlToMobi(@RequestParam URI uri, HttpServletResponse response) throws IOException {
		WebSite site = this.downloader.download(uri);
		MobiBook book = this.assembler.assemble(site);
		book.writeTo(response);
	}
	
}