package org.jsoup.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class HtmlParserEdgeCases {
	  @Test public void parseInvalidTable() {
	    	String html = "<table><!DOCTYPE html><th><table></table></th><table></table>";
	    	
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	        Document doc = Jsoup.parse(html, "http://example.com", parser);
	        
	        List<ParseError> errors = parser.getErrors();
	    	
	        assertEquals(5, errors.size());
//	        assertEquals("20: Attributes incorrectly present on end tag", errors.get(0).toString());
//	        assertEquals("35: Unexpected token [Doctype] when in state [InBody]", errors.get(1).toString());
//	        assertEquals("36: Invalid character reference: invalid named referenece 'arrgh'", errors.get(2).toString());
//	        assertEquals("50: Self closing flag not acknowledged", errors.get(3).toString());
//	        assertEquals("61: Unexpectedly reached end of file (EOF) in input state [TagName]", errors.get(4).toString());
//	    	  Parser.parseFragme(html, "http://example.com/");

	    }
		
	    @Test public void tableWithStyle() {
	    	String html = "<form><select name=\"html\"><!-- comment --></select></form>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	        Document doc = Jsoup.parse(html, "http://example.com", parser);
	        
	    }
	    
	    @Test public void headWithHtmlName() {
	    	String html = "<html><head><html></html></head></html>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	        Document doc = Jsoup.parse(html, "http://example.com", parser);
	        
	    }
}
