
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;

import java.util.Arrays;

/**
 *
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_extraLargeK() {
        String pref = "abc";
        int k = 100;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testDelete() {
        String word = "abc";
        boolean deleteResult = pm.delete(word);
        assertTrue(deleteResult);

        word = "notExistingWord";
        deleteResult = pm.delete(word);
        assertFalse(deleteResult);

        // delete from empty trie
        PrefixMatches newPM = new PrefixMatches(new RWayTrie());
        deleteResult = newPM.delete("");
        assertFalse(deleteResult);

        Iterable<String> result = pm.wordsWithPrefix("");
        String[] expResult = {"abce", "abcd", "abcde", "abcdef"};
        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testContains() {
        String word = "abc";
        boolean containsResult = pm.contains(word);
        assertTrue(containsResult);

        word = "notExistingWord";
        containsResult = pm.contains(word);
        assertFalse(containsResult);
    }

}
