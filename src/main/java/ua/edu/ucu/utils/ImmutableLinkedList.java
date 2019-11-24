package ua.edu.ucu.utils;

import java.util.Arrays;

public class ImmutableLinkedList implements ImmutableList {
    private Node head;
    private int size;
    private Node tail;

    private static class Node {
        private Node next = null;
        private Object value;

        Node(Object value) {
            setValue(value);
        }

        public void setNext(Node newNext) {
            next = newNext;
        }

        public void setValue(Object newValue) {
            value = newValue;
        }

        public Node getNext() {
            return next;
        }

        public Object getValue() {
            return value;
        }
    }

    public ImmutableLinkedList() {
        head = null;
        size = 0;
        tail = null;
    }

    public ImmutableLinkedList(Object[] elements) {
        size = elements.length;

        if (size == 0) {
            return;
        }

        Node currentNode = new Node(elements[0]);
        head = currentNode;
        tail = currentNode;

        for (int i = 1; i < size; i++) {
            Node newNode = new Node(elements[i]);
            currentNode.setNext(newNode);

            currentNode = newNode;
            tail = newNode;
        }
    }

    // додає елемент у кінець колекції
    public ImmutableLinkedList add(Object e) {
        return addAll(size, new Object[]{e});
    }

    // додає елемент до колекції за індексом, та кидає виключну ситуацію,
    // якщо індекс виходить за межі колекції
    public ImmutableLinkedList add(int index, Object e) {
        return addAll(index, new Object[]{e});
    }

    //додає масив елементів у кінець колекції
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size, c);
    }

    // додає масив елементів починаючи з зазначеного індекса,
    // та кидає виключну ситуацію, якщо індекс виходить за межі колекції
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index != size) {
            checkIndexError(index);
        }

        Object[] newArray = Arrays.copyOf(toArray(), size + c.length);
        System.arraycopy(
                newArray, index, newArray, index + c.length,
                size - index
        );
        System.arraycopy(c, 0, newArray, index, c.length);

        return new ImmutableLinkedList(newArray);
    }

    // повертає елемент за індексом, та кидає виключну ситуацію,
    // якщо індекс виходить за межі колекції
    public Object get(int index) {
        checkIndexError(index);

        Node node = head;
        for (int i = 1; i <= index; i++) {
            node = node.getNext();
        }

        return node.getValue();
    }

    // видаляє елемент за індексом, та кидає виключну ситуацію,
    // якщо індекс виходить за межі колекції
    public ImmutableLinkedList remove(int index) {
        checkIndexError(index);

        Object[] elements = toArray();
        Object[] newArray = new Object[size - 1];
        System.arraycopy(elements, 0, newArray, 0, index);
        System.arraycopy(
                elements, index + 1, newArray, index,
                elements.length - index - 1
        );

        return new ImmutableLinkedList(newArray);
    }

    // змінює значення елементу за індексом, та кидає виключну ситуацію,
    // якщо індекс виходить за межі колекції
    public ImmutableLinkedList set(int index, Object e) {
        checkIndexError(index);

        Object[] newArray = Arrays.copyOf(toArray(), size);
        newArray[index] = e;

        return new ImmutableLinkedList(newArray);
    }

    // шукає індекс елемента (повертає індекс першого який знайшов,
    // або -1 у випадку відсутності)
    public int indexOf(Object e) {
        if (isEmpty()) {
            return -1;
        }

        Node node = head;
        for (int i = 0; i < size; i++) {
            if (node.getValue().equals(e)) {
                return i;
            }

            node = node.getNext();
        }

        return -1;
    }

    // розмір колекції
    public int size() {
        return size;
    }

    // очищує вміст колекції
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    // якщо у колеції нема елементів то повертає true
    public boolean isEmpty() {
        return size == 0;
    }

    // перетворює колекцію до масиву обєктів
    public Object[] toArray() {
        Object[] array = new Object[size];

        Node node = head;
        for (int i = 0; i < size; i++) {
            array[i] = node.getValue();

            node = node.getNext();
        }

        return array;
    }


    // додає елемент у початок зв'язаного списку
    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    // додає елемент у кінець зв'язаного списку
    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        if (tail == null) {
            return null;
        }

        return tail.getValue();
    }

    // видаляє перший елемент
    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    // видаляє останній елемент
    public ImmutableLinkedList removeLast() { return remove(size - 1); }


    // повертає рядок, де через кому відображаютсься елементи колекції
    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }

    private void checkIndexError(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
}
