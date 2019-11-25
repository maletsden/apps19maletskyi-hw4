package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Tuple;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private RWayTrie trie;


    private static class PrefixIterable implements Iterable<String> {
        private final Iterator<String> wordsIterator;
        private final int limit;

        PrefixIterable(Iterator<String> newWordsIterator, int newLimit) {
            wordsIterator = newWordsIterator;
            limit = newLimit;
        }

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                private int counter = -1;
                private int maxLength = 0;
                private String nextValue;

                @Override
                public boolean hasNext() {
                    if (!wordsIterator.hasNext()) {
                        return false;
                    }

                    if (nextValue == null) {
                        nextValue = wordsIterator.next();
                        if (nextValue.length() > maxLength) {
                            maxLength = nextValue.length();
                            counter += 1;
                        }
                    }

                    return counter < limit;
                }

                @Override
                public String next() {
                    if (hasNext() && nextValue != null) {
                        String value = nextValue;
                        nextValue = null;
                        return value;
                    }

                    throw new NoSuchElementException();
                }
            };
        }
    }

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
        return new PrefixIterable(
                wordsWithPrefix(pref).iterator(),
                k
        );

    }

    public int size() {
        return trie.size();
    }
}
