package ua.edu.ucu.tries;

import ua.edu.ucu.utils.Queue;

import java.util.*;

public class RWayTrie implements Trie {
    private Node root;
    private int size = 0;

    private class Node {
        String data;
        boolean visited = false;
        final HashMap<Character, Node> subtree = new HashMap<>();

        Node() {}

        void add(Character key, Node value) {
            subtree.put(key, value);
        }

        Node get(Character key) {
            return subtree.get(key);
        }

        void remove(Character key) {
            subtree.remove(key);
        }
    }

    public RWayTrie() {
        root = new Node();
    }

    @Override
    public void add(Tuple t) {
        add(root, t, 0);
        size += 1;
    }

    private Node add(Node node, Tuple t, int index) { // Change value associated with key if in subtrie rooted at x.
        if (node == null) node = new Node();

        if (index == t.term.length()) {
            node.data = t.term;
            return node;
        }

        char c = t.term.charAt(index);
        node.add(
                c,
                add(node.get(c), t, index + 1)
        );
        return node;
    }

    public String get(String word) {
        Node node = get(root, word, 0);
        if (node == null) return null;
        return node.data;
    }

    private Node get(Node node, String word, int index) {
        if (node == null) return null;
        if (index == word.length()) return node;
        char c = word.charAt(index);
        return get(node.get(c), word, index + 1);
    }

    @Override
    public boolean contains(String word) {
        return get(word) != null;
    }

    @Override
    public boolean delete(String word) {
        boolean[] wasDeleted = new boolean[1];
        delete(root, word, 0, wasDeleted);
        return wasDeleted[0];
    }

    private Object delete(Node node, String word, int index, boolean[] wasDeleted) {
        if (node == null) return null;

        if (index == word.length()) {
            wasDeleted[0] = node.data != null;
            node.data = null;
        } else {
            char c = word.charAt(index);
            if (delete(node.get(c), word, index + 1, wasDeleted) == null) {
                node.remove(c);
            }
        }

        if (node.data != null || !node.subtree.isEmpty()) return node;

        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() == 1) return null;

        ArrayList<String> words = new ArrayList<>();
        Queue queue = new Queue();

        // get all words
        Node rootNode = get(root, pref, 0);
        queue.enqueue(rootNode);

        while (!queue.isEmpty()) {
            Node node = (Node) queue.dequeue();
            // save word if possible
            if (node.data != null) words.add(node.data);

            node.subtree.forEach((key, child) -> {
                if (!child.visited) {
                    child.visited = true;
                    queue.enqueue(child);
                }
            });
        }

        // mark node back as unvisited
        markVisited(rootNode, false);

        return words;
    }

    private void markVisited(Node node, boolean visited) {
        node.visited = visited;
        node.subtree.forEach((key, child) -> {
            markVisited(child, visited);
        });
    }

    @Override
    public int size() {
        return size;
    }

}
