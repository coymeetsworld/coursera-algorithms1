import java.util.Iterator;

/**
    @author: Coy Sanders
    @version: 05/04/2017

    Compilation: javac-algs4 Deque.java
    Execution: N/A, used in Permutation.java

    Double-ended queue (pronounced "deck") is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure.

*/
public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    /**
      Construct an empty queue
    */
    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }


    /**
      Checks if the Deque is empty.
    */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
      Return the number of items on the deque.
    */
    public int size() {
        return size;
    }


    /**
      Add the item to the front of the Deque.
    */
    public void addFirst(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Can't add a null value to Deque");
        Node n = new Node();
        n.item = item;
        if (size == 0) {
            head = n;
            tail = n;
        } else {
            Node origHead = head; //todo check if necessary
            origHead.prev = n;
            n.next = origHead; 
            head = n;
        } 
        size++;
    }


    /**
      Add the item to the end.
    */
    public void addLast(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Can't add a null value to Deque");
        Node n = new Node();
        n.item = item;
        n.prev = tail; //todo check if we need to create origTail?
        if (size == 0) {
            head = n;
        } else {
            tail.next = n;
        } 
        tail = n;
        size++;
    }


    /**
      Remove and return the item from the front.
    */
    public Item removeFirst() {
        if (size == 0) throw new java.util.NoSuchElementException("Deque is empty, cannot remove any further items.");
        Item item = head.item; 
        head = head.next; //even if first.next is null that's fine
        if (size > 1) {
            head.prev = null; // Remove connection to front node
        } 
        size--;
        if (size == 0) tail = null; //remove reference to last node for GC
        return item;
    }


    /**
      Remove and return the item from the end.
    */
    public Item removeLast() {
        if (size == 0) throw new java.util.NoSuchElementException("Deque is empty, cannot remove any further items.");
        Item item = tail.item;
        tail = tail.prev;
        if (size > 1) {
            tail.next = null; // Remove connection to last node
        } 
        size--;
        if (size == 0) head = null; //remove reference to last node for GC
        return item;
    }


    /**
      Return an iterator over items in order from front to end.
    */ 
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new java.lang.UnsupportedOperationException("remove() is not supported."); }
        public Item next() {
            if (current == null) throw new java.util.NoSuchElementException("No more items to return");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private void print() {
        Node n = head;
        System.out.println("Front-to-back");
        while (n != null) {
            System.out.print(n.item + " --> "); 
            n = n.next;
        } 
        System.out.println();
        n = tail;
        System.out.println("Back-to-front");
        while (n != null) {
            System.out.print(n.item + " --> "); 
            n = n.prev;
        }
        System.out.println();
    }

    /**
      Unit testing (optional)
    */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.print();
        for (int i = 0; i < 6; i++) {
            System.out.println("Removed: " + deque.removeFirst());
            deque.print();
        }
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        for (int i = 0; i < 6; i++) {
            System.out.println("Removed: " + deque.removeLast());
            deque.print();
        }
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.print();
    }
}

