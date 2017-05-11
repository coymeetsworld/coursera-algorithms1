import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/**
    @author: Coy Sanders
    @version: 05/04/2017

    Compilation: javac-algs4 RandomizedQueue.java
    Execution: N/A, used in Permutation.java
*/

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;
    private int size;

    /**
      Construct an empty randomized queue. 
    */
    public RandomizedQueue() {
        q = (Item[]) new Object[1]; // make it bigger by default? can't because of memory constraint 
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
        if (q.length == size) {
           Item[] newQ = (Item[]) new Object[size*2];
           for (int i = 0; i < size; i++) newQ[i] = q[i];
           q = newQ;
           q[size++] = item;
        } else {
            for (int i = 0; i < q.length; i++) {
               if (q[i] == null) {
                   q[i] = item; 
                   size++;
                   return;
               }
            }
        }
    }


    /**
      Remove and return a random item.
    */
    public Item dequeue() {
        if (size == 0) throw new java.util.NoSuchElementException("Deque is empty, cannot remove any further items.");
        int itemNumber = StdRandom.uniform(size)+1; // [1, size]
        // System.out.println("element to remove (start at 1): " + itemNumber);
        int count = 0;
        int i = 0;
        while(count < itemNumber) {
            if (q[i] != null) {
              // System.out.println("q[" + i +"] is not null ("+q[i]+")"); 
              count++;
            } else { /* System.out.println("q[" + i + "]: is null");*/ }
            if (count != itemNumber) i++;
        }

        // System.out.println("out");
        // System.out.println("count: " + count);
        // System.out.println("index to remove: " + i);
        Item removedItem = q[i];
        q[i] = null;
        size--;
        if ((double) size/q.length == 0.25) {
           Item[] newQ = (Item[]) new Object[q.length/4];
           // System.out.println("newQ length: " + newQ.length);
           int newQIndex = 0;
           for (i = 0; i < q.length; i++) {
               if (q[i] != null) {
                  // System.out.println("Adding " + q[i] + " to newQ["+newQIndex+"].");
                  newQ[newQIndex++] = q[i];
               }
           }
           q = newQ;
        }
        return removedItem;
    }


    /**
      Return (but do not remove) a random item.
    */
    public Item sample() {
        if (size == 0) throw new java.util.NoSuchElementException("Deque is empty, cannot sample it.");
        int itemNumber = StdRandom.uniform(size)+1; // [1, size]
        // System.out.println("element to remove (start at 1): " + itemNumber);
        int count = 0;
        int i = 0;
        while (count < itemNumber) {
            if (q[i] != null) {
              // System.out.println("q[" + i +"] is not null ("+q[i]+")"); 
              count++;
            } else { /* System.out.println("q[" + i + "]: is null");*/ }
            if (count != itemNumber) i++;
        }

        return q[i];
    }


    /**
      Return an independent iterator over items in random order.
    */ 
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {

        private int i;
        private Item[] queue;
        private RandomQueueIterator() {
            i = size;
            queue = (Item[]) new Object[size];
            int count = 0;
            // System.out.println("Length of arr q: " + q.length);
            for (int i = 0; i < q.length; i++) {
               if (q[i] != null) {
                  queue[count++] = q[i]; // Make a deep copy of the queue
                  // System.out.println("Adding " + q[i] + " found at q["+i+"]" + " ("+ count + " item to add.)");
               }
            }
            StdRandom.shuffle(queue); // Shuffle it
        }

        public boolean hasNext() { return i > 0; }
        public void remove() { throw new java.lang.UnsupportedOperationException("remove() is not supported."); }
        public Item next() {
            if (i == 0) throw new java.util.NoSuchElementException("No more items to return");
            return queue[--i];
        }
    }



    private void print() {
        System.out.println("Size: " + size);
        System.out.println("Arr length: " + q.length);
        for (int i = 0; i < q.length; i++) {
            System.out.print(q[i] + " , ");
        }
        System.out.println();
    }

    /**
      Unit testing (optional)
    */ 
    public static void main(String[] args) {
        RandomizedQueue<Integer> rqueue = new RandomizedQueue<Integer>();
        for (int i = 1; i <= 10000; i++) {
            int rand = StdRandom.uniform(0, 10);
            if (rand == 9) rqueue.size();
            else if (rand == 8) rqueue.isEmpty();
            else if (rand == 7) {
                try {
                    rqueue.dequeue();
                } catch (java.util.NoSuchElementException e) { System.out.println(e); }
            }
            else if (rand == 6) rqueue.enqueue(StdRandom.uniform(1, 10000));
            else {
                try {
                    rqueue.sample();
                } catch (java.util.NoSuchElementException e) { System.out.println(e); }
            }
        }
        System.out.println("Size of queue: " + rqueue.size());
        Iterator<Integer> itr = rqueue.iterator();
        while (itr.hasNext()) {
           System.out.println(itr.next());
        }
    }
}

