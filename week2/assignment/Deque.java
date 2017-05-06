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
       Node n = new Node();
       n.item = item;
       if (size == 0) {
           head = n;
           tail = n;
       } else {
          Node origHead = head;
          head = n;
          head.next = origHead; 
       } 
       size++;
    }


    /**
      Add the item to the end.
    */
    public void addLast(Item item) {
       Node n = new Node();
       n.item = item;
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
        if (size == 0) {  } //throw exception
        Node item = head.item; 
        size--;
        head = head.next; //even if first.next is null that's fine
        if (size == 0) tail = null; //remove reference to last node for GC
        return item;
    }


    /**
      Remove and return the item from the end.
    */
    public Item removeLast() {
        //for constant worst time removal, might need to have a pointer to prev TODO
        return null;
    }


    /**
      Return an iterator over items in order from front to end.
    */ 
    public Iterator<Item> iterator() {
       return null;
    }


    /**
      Unit testing (optional)
    */
    public static void main(String[] args) {
        
    }
}

