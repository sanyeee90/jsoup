package org.jsoup.nodes;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeTest {
    @Test public void html() {
        Attribute attr = new Attribute("key", "value &");
        assertEquals("key=\"value &amp;\"", attr.html());
        assertEquals(attr.html(), attr.toString());
    }

    @Test public void testWithSupplementaryCharacterInAttributeKeyAndValue() {
        String s = new String(Character.toChars(135361));
        Attribute attr = new Attribute(s, "A" + s + "B");
        assertEquals(s + "=\"A" + s + "B\"", attr.html());
        assertEquals(attr.html(), attr.toString());
    }
    
    @Test public void testAttributeEquals() {
    	Attribute attr1 = new Attribute("key1", "value");
    	Attribute attr2 = new Attribute("key2", "value");
    	
    	Assert.assertFalse(attr1.equals(attr2));
    }
}
