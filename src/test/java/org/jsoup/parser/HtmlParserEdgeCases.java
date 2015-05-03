package org.jsoup.parser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;

public class HtmlParserEdgeCases {
	  @Test public void parseInvalidTable() {
	    	String html = "<table><!DOCTYPE html><th><table></table></th><table></table>";
	    	
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	        Document doc = Jsoup.parse(html, "http://example.com", parser);
	        
	        List<ParseError> errors = parser.getErrors();
	    	
	        assertEquals(5, errors.size());
	        assertEquals("22: Unexpected token [Doctype] when in state [InTable]", errors.get(0).toString());
	        assertEquals("26: Unexpected token [StartTag] when in state [InTableBody]", errors.get(1).toString());
	        assertEquals("46: Unexpected token [EndTag] when in state [InRow]", errors.get(2).toString());
	        assertEquals("53: Unexpected token [StartTag] when in state [InTable]", errors.get(3).toString());
	        assertEquals("53: Unexpected token [EndTag] when in state [InTableBody]", errors.get(4).toString());


	    }
		
	    @Test public void tableWithStyle() {
	    	String html = "<form><select><!-- comment --></select></form>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	        Document doc = Jsoup.parse(html, "http://example.com", parser);
	        
	        assertEquals(0, parser.getErrors().size());
	        assertEquals("<html>\n <head></head>\n <body>\n  <form>\n   <select>\n    <!-- comment --></select>\n  </form>\n </body>\n</html>", doc.toString());
	    }
	    
	    @Test public void headWithHtmlName() {
	    	String html = "<html><head><html></html></head></html>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	        Document doc = Jsoup.parse(html, "http://example.com", parser);
	        
	        List<ParseError> errors = parser.getErrors();
	    	
	        assertEquals(3, errors.size());
	        assertEquals("18: Unexpected token [StartTag] when in state [InBody]", errors.get(0).toString());
	        assertEquals("32: Unexpected token [EndTag] when in state [AfterAfterBody]", errors.get(1).toString());
	        assertEquals("32: Unexpected token [EndTag] when in state [InBody]", errors.get(2).toString());

	        assertEquals("<html>\n <head></head>\n <body></body>\n</html>", doc.toString());
	    }
	    
	    @Test public void testNormalize() {
	    	String html = "<html><head></head></html>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	    	Document doc = Jsoup.parse(html, "http://example.com", parser);
	    	doc.append("<head>stg</head>");
	    	
	    	doc.normalise();
	    	
	    	assertEquals("<html>\n <head></head>\n <body>\n  stg \n </body>\n</html>", doc.toString());
	    }
	    
	    @Test public void testHtmlInsideBodyWithAttributes() {
	    	String html = "<html><head></head><body><html attribute=\"attr\"></html></body></html>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	    	Document doc = Jsoup.parse(html, "http://example.com", parser);
	    	
	    	assertEquals("<html attribute=\"attr\">\n <head></head>\n <body></body>\n</html>", doc.toString());
	    }
	    
	    @Test public void testNotHiddenInputInsideTable() {
	    	String html = "<table><input type=\"textarea\" /></table>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	    	Document doc = Jsoup.parse(html, "http://example.com", parser);
	    	
	    	assertEquals(1, parser.getErrors().size());
	    	assertEquals("32: Unexpected token [StartTag] when in state [InTable]", parser.getErrors().get(0).toString());
	    	assertEquals("<html>\n <head></head>\n <body>\n  <input type=\"textarea\">\n  <table></table>\n </body>\n</html>", doc.toString());
	    }
	    //TODO check if it is ignorable
	    @Test public void testScriptInsideTable() {
	    	String html = "<table><script type=\"text/javascript\">console.log()</script></table>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	    	Document doc = Jsoup.parse(html, "http://example.com", parser);
	    	
	    	assertEquals("<html>\n <head></head>\n <body>\n  <table>\n   <script type=\"text/javascript\">console.log()</script>\n  </table>\n </body>\n</html>", doc.toString());
	    }
	    
	    @Test public void testOptgroupInSelect() {
	    	String html = "<select><optgroup label=\"cars\"><option>audi</option></optgroup></select>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	    	Document doc = Jsoup.parse(html, "http://example.com", parser);

	    	
	    	assertEquals("<html>\n <head></head>\n <body>\n  <select><optgroup label=\"cars\"><option>audi</option></optgroup></select>\n </body>\n</html>", doc.toString());

	    }
	    
	    @Test public void testScriptInSelect() {
	    	String html = "<select><script></script></select>";
	    	Parser parser = Parser.htmlParser().setTrackErrors(500);
	    	Document doc = Jsoup.parse(html, "http://example.com", parser);
	    	
	    	assertEquals("<html>\n <head></head>\n <body>\n  <select><script></script></select>\n </body>\n</html>", doc.toString());

	    }
}

