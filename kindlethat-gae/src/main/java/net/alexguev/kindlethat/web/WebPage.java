package net.alexguev.kindlethat.web;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WebPage {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebPage.class);

    private String title;
    private final String content;

    WebPage(String content) {
        this.content = content;
        this.parse();
    }

    private void parse() {
        try {
            this.doParse();
        } catch (ParserException e) {
            LOGGER.error(String.format("Unable to parse %s", this.content), e);
        }
    }

    private void doParse() throws ParserException {
        Parser parser = Parser.createParser(this.content, null);
        NodeList nodes = parser.parse(null);
        nodes.visitAllNodesWith(new NodeVisitor() {
            boolean justVisitedTitleTag = false;
            @Override
            public void visitTag(Tag tag) {
                if ("title".equalsIgnoreCase(tag.getTagName())) {
                    this.justVisitedTitleTag = true;
                }
            }
            @Override
            public void visitStringNode(Text text) {
                if (this.justVisitedTitleTag) {
                    WebPage.this.title = text.getText();
                    this.justVisitedTitleTag = false;
                }
            }
        });
    }

    String getContent() {
        return this.content;
    }

    String getTitle() {
        return this.title;
    }
}