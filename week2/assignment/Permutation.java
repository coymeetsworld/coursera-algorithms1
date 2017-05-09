import edu.princeton.cs.algs4.StdIn;
/**
    @author: Coy Sanders
    @version: 05/04/2017

    Compilation: javac-algs4 Permutation.java
    Execution: java-algs4 Permutation [number]
*/
public class Permutation {
    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rqueue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < count; i++) {
            System.out.println(rqueue.dequeue());
        }
    }
}
