package net.alexguev.kindlethat.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({MobiBookAssemblerTest.WebSiteWithOnePageTest.class})
public class MobiBookAssemblerTest {
	
	public static class WebSiteWithOnePageTest {
		
		private final MobiBookAssembler assembler = new MobiBookAssembler();
		
		private WebSite site;
		private WebPage rootPage;

		@Before
		public void setUp() {
			this.rootPage = new WebPage("<html><title></title><body></body></html>");
			this.site = new WebSite(this.rootPage);
		}

		@Test
		public void book_title_should_match_root_page_title() throws Exception {
			MobiBook book = this.assembler.assemble(this.site);
			assertEquals("title", book.getTitle(), this.site.getRootPage().getTitle());
		}
		
		@Test
		public void book_should_have_one_part() throws Exception {
			MobiBook book = this.assembler.assemble(this.site);
			assertEquals("one part", 1, book.getParts().size());
		}

		@Test
		public void book_should_have_part_with_root_page_content() throws Exception {
			MobiBook book = this.assembler.assemble(this.site);
			assertEquals("part with root page content", this.rootPage.getContent(), book.getParts().get(0).getContent());
		}
		
	}

}
