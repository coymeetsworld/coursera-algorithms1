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
        q = (Item[]) new Object[1];
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
        if (q.length == size) resizeArray(size*2);
        q[size++] = item;
    }


    /**
        Remove and return a random item.
    */
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque is empty, cannot remove any further items.");
        int itemNumber = StdRandom.uniform(size); // [0, size)
        Item removedItem = q[itemNumber];
        q[itemNumber] = q[size-1];
        q[size-1] = null;
        size--;
        if ((double) size/q.length == 0.25) resizeArray(q.length/2);
        return removedItem;
    }


    /**
        Return (but do not remove) a random item.
    */
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Deque is empty, cannot sample it.");
        return q[StdRandom.uniform(size)];
    }


    /**
       Helper method that will resize the array.
       It will either double in size with enqueue if the array is filled, or cut it in half if its 1/4 full.
    */
    private void resizeArray(int newSize) {
        Item[] newQ = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) newQ[i] = q[i];
        q = newQ;
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
            queue = (Item[]) new Object[size];
            i = size;
            for (int j = 0; j < size; j++) queue[j] = q[j];
            StdRandom.shuffle(queue);
        }

        public boolean hasNext() { return i > 0; }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("remove() is not supported.");
        }

        public Item next() {
            if (i == 0) throw new java.util.NoSuchElementException("No more items to return");
            return queue[--i];
        }
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
           System.out.print(itr.next() + ",");
        }
    }
}

