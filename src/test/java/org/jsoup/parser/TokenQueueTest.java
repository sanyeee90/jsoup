package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Token queue tests.
 */
public class TokenQueueTest {
    @Test public void chompBalanced() {
        TokenQueue tq = new TokenQueue(":contains(one (two) three) four");
        String pre = tq.consumeTo("(");
        String guts = tq.chompBalanced('(', ')');
        String remainder = tq.remainder();

        assertEquals(":contains", pre);
        assertEquals("one (two) three", guts);
        assertEquals(" four", remainder);
    }
    
    @Test public void chompEscapedBalanced() {
        TokenQueue tq = new TokenQueue(":contains(one (two) \\( \\) \\) three) four");
        String pre = tq.consumeTo("(");
        String guts = tq.chompBalanced('(', ')');
        String remainder = tq.remainder();

        assertEquals(":contains", pre);
        assertEquals("one (two) \\( \\) \\) three", guts);
        assertEquals("one (two) ( ) ) three", TokenQueue.unescape(guts));
        assertEquals(" four", remainder);
    }

    @Test public void chompBalancedMatchesAsMuchAsPossible() {
        TokenQueue tq = new TokenQueue("unbalanced(something(or another");
        tq.consumeTo("(");
        String match = tq.chompBalanced('(', ')');
        assertEquals("something(or another", match);
    }
    
    @Test public void unescape() {
        assertEquals("one ( ) \\", TokenQueue.unescape("one \\( \\) \\\\"));
    }
    
    @Test public void chompToIgnoreCase() {
        String t = "<textarea>one < two </TEXTarea>";
        TokenQueue tq = new TokenQueue(t);
        String data = tq.chompToIgnoreCase("</textarea");
        assertEquals("<textarea>one < two ", data);
        
        tq = new TokenQueue("<textarea> one two < three </oops>");
        data = tq.chompToIgnoreCase("</textarea");
        assertEquals("<textarea> one two < three </oops>", data);
    }

    @Test public void addFirstString() {
        TokenQueue tq = new TokenQueue("One Two");
        tq.consumeWord();
        tq.addFirst("Three");
        assertEquals("Three Two", tq.remainder());
    }
    
    @Test public void addFirstChar() {
        TokenQueue tq = new TokenQueue("One Two");
        tq.addFirst('!');
        assertEquals("!One Two", tq.remainder());
    }
    @Test public void consumeTagWithAttirbute() {
        TokenQueue tq = new TokenQueue("<iframe allowfullscreen></iframe>");
        assertTrue(tq.matchesStartTag());
        tq.consume("<");
        assertEquals("iframe",tq.consumeTagName());
        assertTrue(tq.consumeWhitespace());
        assertEquals("allowfullscreen",tq.consumeAttributeKey());
        assertEquals('>',tq.peek()); //get the char but don't remove it
        tq.consume("><");
        tq.advance();//drop the '/' char
        assertEquals("iframe",tq.consumeTagName());
        assertEquals(">", tq.toString());
    }
    
    @Test(expected=IllegalStateException.class)
    public void consumeNonExistChar() {
        TokenQueue tq = new TokenQueue("iframe allowfullscreen></iframe>");
        tq.consume("<");
    }
    
    @Test
    public void peekFromEmptyQueue(){
    	TokenQueue tq = new TokenQueue("empty");
    	tq.consume("empty");
    	assertEquals(0, tq.peek());
    }
    

}
