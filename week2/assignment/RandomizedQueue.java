import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/**
    @author: Coy Sanders
    @version: 05/04/2017

    Compilation: javac-algs4 RandomizedQueue.java
    Execution: N/A, used in Permutation.java
*/

public class RandomizedQueue<Item> implements Iterable<Item> {


    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    /**
      Construct an empty randomized queue. 
    */
    public RandomizedQueue() {
        head = null;
        tail = null;
        size = 0;
    }


    /**
      Is the queue empty?
    */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
      Return the number of items on the queue.
    */
    public int size() {
        return size;
    }


    /**
      Add the item.
    */
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException("Can't add a null value to Deque"); 
        Node n = new Node();
        n.item = item;
        if (size == 0) { //refactor, call isEmpty() TODO
            head = n;
            tail = n;
        } else {
            tail.next = n;
            n.prev = tail; 
            tail = n;
        } 
        size++;
    }


    /**
      Remove and return a random item.
    */
    public Item dequeue() {
        if (size == 0) throw new java.util.NoSuchElementException("Deque is empty, cannot remove any further items.");
        Node n = head;
        int count = StdRandom.uniform(size); // [0, size) //Remove TODO
        //System.out.println(count);
        for (int i = 0; i < count; i++) {
            n = n.next;
        }
        //while (count-- > 0) n = n.next;
        Item item = n.item;
        if (n.prev != null) {
            n.prev.next = n.next;
        }
        if (n.next != null) {
            n.next.prev = n.prev; 
        }
        if (n == head && size > 1) head = n.next;
        if (n == tail && size > 1) tail = n.prev;
        size--;
        if (size == 0) {
            head = null;
            tail = null;
        }
        n.next = null;
        n.prev = null;
        return n.item; //TODO check if this is properly being removed
    }


    /**
      Return (but do not remove) a random item.
    */
    public Item sample() {
        if (size == 0) throw new java.util.NoSuchElementException("Deque is empty, cannot sample it.");
        Node n = head;
        int count = StdRandom.uniform(size); // [0, size)
        while (count-- > 0) n = n.next;
        return n.item;
    }


    /**
      Return an independent iterator over items in random order.
    */ 
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
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
        RandomizedQueue<Integer> rqueue = new RandomizedQueue<Integer>();
        rqueue.enqueue(386);
        rqueue.print();
        System.out.println(rqueue.isEmpty());
        System.out.println("Removed: " + rqueue.dequeue());
        rqueue.print();
        System.out.println(rqueue.isEmpty());
        
        for (int i = 1; i <= 9; i++) {
            rqueue.enqueue(i);
        }
        for (int i = 0; i < 9; i++) {
            System.out.println("Removed: " + rqueue.dequeue());
            rqueue.print();
        }
        /*for (int i = 1; i < 101; i++) {
          System.out.print(rqueue.sample() + " ");
          if (i % 10 == 0) { System.out.println(); }
        }i*/
    }
}

