package org.jsoup.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for Attributes.
 *
 * @author Jonathan Hedley
 */
@Ignore
public class AttributesTest {
    @Test public void html() {
        Attributes a = new Attributes();
        a.put("Tot", "a&p");
        a.put("Hello", "There");
        a.put("data-name", "Jsoup");

        assertEquals(3, a.size());
        assertTrue(a.hasKey("tot"));
        assertTrue(a.hasKey("Hello"));
        assertTrue(a.hasKey("data-name"));
        assertEquals(1, a.dataset().size());
        assertEquals("Jsoup", a.dataset().get("name"));
        assertEquals("a&p", a.get("tot"));

        assertEquals(" tot=\"a&amp;p\" hello=\"There\" data-name=\"Jsoup\"", a.html());
        assertEquals(a.html(), a.toString());
    }

}
