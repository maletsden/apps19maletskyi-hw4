package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.Arrays;
import java.util.Iterator;


/**
 *
 * @author andrii
 */
public class PrefixMatches {

    public RWayTrie trie;

    public PrefixMatches(RWayTrie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (String string: strings) {
            trie.add(new Tuple(string, string.length()));
        }

        return size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        Iterator<String> wordsIterator = wordsWithPrefix(pref).iterator();

        return () -> new Iterator<String>() {
            private int counter = -1;
            private int maxLength = 0;

            private String nextValue;

            @Override
            public boolean hasNext() {
                if (wordsIterator.hasNext()) {
                    nextValue = wordsIterator.next();
                    if (nextValue.length() > maxLength) {
                        maxLength = nextValue.length();
                        counter += 1;
                    }
                    return counter < k;
                } else {
                    return false;
                }
            }

            @Override
            public String next() {
                return nextValue;
            }
        };
    }

    public int size() {
        return trie.size();
    }
}
