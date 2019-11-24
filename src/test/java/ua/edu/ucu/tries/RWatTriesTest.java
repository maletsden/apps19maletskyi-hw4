package ua.edu.ucu.tries;

import org.junit.Before;
import org.junit.Test;
import ua.edu.ucu.autocomplete.PrefixMatches;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class RWatTriesTest {

    private Trie trie;

    @Before
    public void init() {
        trie = new RWayTrie();
        String[] words = {"abc", "abce", "abcd", "abcde", "abcdef"};

        for (String word: words) {
            trie.add(new Tuple(word, word.length()));
        }
    }

    @Test
    public void testWords() {
        Iterable<String> result = trie.words();

        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testDelete_FromEmptyTrie() {
        Trie newTrie = new RWayTrie();
        assertFalse(newTrie.delete(""));
    }
}
