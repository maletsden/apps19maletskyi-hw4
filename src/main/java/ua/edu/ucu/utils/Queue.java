package ua.edu.ucu.utils;

public class Queue {
    private ImmutableLinkedList queue;

    public Queue() {
        queue = new ImmutableLinkedList();
    }

    public Queue(Object[] elements) {
        queue = new ImmutableLinkedList(elements);
    }

    // Returns the object at the beginning of the Queue without removing it
    public Object peek() {
        return queue.getFirst();
    }

    // Removes and returns the object at the beginning of the Queue
    public Object dequeue() {
        Object first = peek();
        queue = queue.removeFirst();
        return first;
    }

    // Adds an object to the end of the Queue
    public void enqueue(Object e) {
        queue = queue.addLast(e);
    }

    public Object[] toArray() {
        return queue.toArray();
    }

    public boolean isEmpty() { return  queue.isEmpty(); }

}
