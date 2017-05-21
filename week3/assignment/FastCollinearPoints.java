
/**
    @author: Coy Sanders
    @version: 05/17/2017

    Compilation: javac-algs4 FastCollinearPoints.java
    Execution: N/A

    Examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments.
    For points p,q,r,s, this is done by checking if the slopes between p and q, p and r, and p and s are all equal.

    Performance requirement: Order of growth of the running time of the program should be (n^2)log(n) in the worst case.
    Also, it should use space proportional to n plus the number of line segments returned.
    FastCollinearPoints should work properly even if the input has 5 or more collinear points.

*/
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments;
    private Point[] aux;

    /**
        Finds all line segments containing 4 points.
    */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException("Points array cannot be null.");        aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException("No points in the point array can be null.");
            aux[i] = points[i];
        }

        Arrays.sort(aux); // check if needed later TODO 

        // System.out.println("Sorted arr");
        // for (int i = 0; i < aux.length; i++) System.out.println(aux[i]);
        // System.out.println("End sort\n\n");

        for (int i = 0; i < aux.length-1; i++) {
            if (aux[i].compareTo(aux[i+1]) == 0)
                throw new java.lang.IllegalArgumentException("constructor cannot accept duplicate points in the passed array");
        }
        segments = new ArrayList<LineSegment>();

        //Think of p as the origin.
        //For each other point q, determine the slope it makes with p.
        //Sort the points according to the slopes they makes with p.
        //Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.
        //Applying this method for each of the n points in turn yields an efficient algorithm to the problem. The algorithm solves the problem because points that have equal slopes with respect to p are collinear, and sorting brings such points together. The algorithm is fast because the bottleneck operation is sorting.


        for (int p = 0; p < aux.length-1; p++) {
            // p is the origin, find slope of all other points q.
            // System.out.println(aux[p] + " is the origin");
            Comparator<Point> comp = aux[p].slopeOrder();
            // sort sub arrays
            //Arrays.sort(a, lo, hi) sorts the subarray from a[lo] to a[hi-1] according to the natural order of a[]. You can use a Comparator as the fourth argument to sort according to an alternate order.
            Arrays.sort(aux, p, aux.length, comp);
            // System.out.println("Sorted sub array:");
            // for (int i = p+1; i < aux.length; i++) {
                // System.out.println(aux[i] + " " + aux[p].slopeTo(aux[i]));
            // }
            
            int count = 0;
            int begin = p;
            int end = -1;

            for (int q = p+1; q < aux.length; q++) {
                if (end == -1) {
                    end = q;
                    // System.out.println("Setting end to " + aux[q] + ". slope between begin and end: " + aux[begin].slopeTo(aux[end])); 
                    count = 1;
                } else {

                    // System.out.println("slope from p to q: " + aux[p].slopeTo(aux[q]));
                    // System.out.println("slope from p to end: " + aux[p].slopeTo(aux[end]));

                    if (aux[p].slopeTo(aux[q]) == aux[p].slopeTo(aux[end])) {
                        // System.out.println("Equal!");
                        count++;
                        if (aux[p].compareTo(aux[q]) < 0) end = q;
                        else if (aux[q].compareTo(aux[end]) < 0) begin = q;
                    } else {

                        if (count >= 3) {
                            // System.out.println("Found a segment: between " + aux[begin] + " and " + aux[end]);
                            segments.add(new LineSegment(aux[begin], aux[end]));
                        }
                        //reset count
                        count = 1;
                        end = q;
                        begin = q;
                    }

                }
            }
            // System.out.println("\n");
        }

        // System.out.println("Sorted arr end");
        // for (int i = 0; i < aux.length; i++) System.out.println(aux[i]);
        // System.out.println("End sort\n\n");
    }


    /**
        The number of line segments.
    */
    public int numberOfSegments() {
        return segments.size();
    }


    /**
        Includes each line segment containing 4 points exactly once.
        If 4 points appear on a line segments in the order of p->q->r->s, it should include either the line segment p->s or s->p,
        but not both. It should also not include subsegments like p->r or q->r.
        For simplicity, no input given to BruteCollinearPoints will have 5 or more collinear points.
    */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
