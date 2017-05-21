/******************************************************************************
 *  @author: Coy Sanders
 *  @version: 05/20/2017
 *
 *  Compilation:  javac-algs4 Point.java LineSegment.java BruteCollinearPoints.java
 *  Execution:    java-algs4 BruteCollinearPoints [file]
 *  Dependencies: Point.java LineSegment.java
 *  
 *  Examines 4 points at a time and checks whether they all lie on the same line segment, returning all such line segments.
 *  For points p,q,r,s, this is done by checking if the slopes between p and q, p and r, and p and s are all equal.
 *
 * Performance requirement: Order of growth of the running time of the program should be n^4 in the worst case.
 * Also, it should use space proportional to n plus the number of line segments returned.
 *
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments;
    private Point[] aux;
    /**
        Finds all line segments containing 4 points.
    */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException("Points array cannot be null.");
        aux = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException("No points in the point array can be null.");
            aux[i] = points[i];
        }


        Arrays.sort(aux);

        for (int i = 0; i < aux.length-1; i++) {
            if (aux[i].compareTo(aux[i+1]) == 0)
                throw new java.lang.IllegalArgumentException("constructor cannot accept duplicate points in the passed array");
        }

        segments = new ArrayList<LineSegment>();
        for (int p = 0; p < aux.length; p++) {
            for (int q = p+1; q < aux.length; q++) {
                for (int r = q+1; r < aux.length; r++) {
                    for (int s = r+1; s < aux.length; s++) {
                        if ((aux[p].slopeTo(aux[q]) == aux[q].slopeTo(aux[r])) &&
                            (aux[q].slopeTo(aux[r]) == aux[r].slopeTo(aux[s]))) {
                           segments.add(new LineSegment(aux[p], aux[s]));
                        }
                    }
                }
            }
        }
    }


    /**
        The number of line segments.
    */
    public int numberOfSegments() {
        return segments.size();
    }


    /**
        Includes each line segment containing 4 points exactly once.
        If 4 points appear on a line segments in the order of p->q->r->s, it should include either the line segment p->s or s->p
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
