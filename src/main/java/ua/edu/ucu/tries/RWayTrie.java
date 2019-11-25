package ua.edu.ucu.tries;

import ua.edu.ucu.utils.Queue;

import java.util.ArrayList;
import java.util.HashMap;


public class RWayTrie implements Trie {
    private Node root;
    private int size = 0;

    private static class Node {
        private String data;
        private boolean visited = false;
        private final HashMap<Character, Node> subtree = new HashMap<>();

        Node() {

        }

        void add(Character key, Node value) {
            subtree.put(key, value);
        }

        Node get(Character key) {
            return subtree.get(key);
        }

        void remove(Character key) {
            subtree.remove(key);
        }

        public String getData() {
            return data;
        }

        public void setData(String newData) {
            data = newData;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean isVisited) {
            visited = isVisited;
        }

        public HashMap<Character, Node> getSubtree() {
            return subtree;
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

    private Node add(Node node, Tuple t, int index) {
        if (index == t.weight) {
            node.setData(t.term);
            return node;
        }

        char c = t.term.charAt(index);
        node.add(
                c,
                add(node.getSubtree().getOrDefault(c, new Node()), t, index + 1)
        );
        return node;
    }

    public String get(String word) {
        Node node = get(root, word, 0);
        if (node == null) {
            return null;
        }
        return node.getData();
    }

    private Node get(Node node, String word, int index) {
        if (node == null) {
            return null;
        }
        if (index == word.length()) {
            return node;
        }
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

    private Object delete(Node node, String word, int index, boolean[] exist) {
        if (node == null) {
            return null;
        }

        if (index == word.length()) {
            exist[0] = node.getData() != null;
            node.setData(null);
        } else {
            char c = word.charAt(index);
            if (delete(node.get(c), word, index + 1, exist) == null) {
                node.remove(c);
            }
        }

        if (node.getData() != null || !node.getSubtree().isEmpty()) {
            return node;
        }

        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() == 1) {
            return null;
        }

        ArrayList<String> words = new ArrayList<>();
        Queue queue = new Queue();

        // get all words
        Node rootNode = get(root, pref, 0);
        queue.enqueue(rootNode);

        while (!queue.isEmpty()) {
            Node node = (Node) queue.dequeue();
            // save word if possible
            if (node.getData() != null) {
                words.add(node.getData());
            }

            node.getSubtree().forEach((key, child) -> {
                if (!child.isVisited()) {
                    child.setVisited(true);
                    queue.enqueue(child);
                }
            });
        }

        // mark node back as unvisited
        markUnvisited(rootNode, false);

        return words;
    }

    private void markUnvisited(Node node, boolean visited) {
        node.setVisited(false);
        node.getSubtree().forEach((key, child) -> {
            markUnvisited(child, visited);
        });
    }

    @Override
    public int size() {
        return size;
    }

}
