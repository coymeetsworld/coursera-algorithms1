import java.util.Comparator;

/**
    @author: Coy Sanders
    @version: 05/17/2017

    Compilation: javac-algs4 Point.java
    Execution: N/A, used in Permutation.java

    Immutable data type that represents a point in the plane.

*/
public class Point implements Comparable<Point> {

    /**
        Constructs the point (x, y).
    */
    public Point(int x, int y) {

    }

    /**
       Draws this point. 
    */
    public void draw() {

    }

    /**
        Draws the line segment from this point to that point.
    */
    public void drawTo(Point that) {

    }

    /**
        String representation
    */
    public String toString() {
        return null;
    }

    /**
        Compare two points by y-coordinates, breaking ties by x-coordinates.
    */
    public int compareTo(Point that) {
        return -1;
    }

    /**
        The slope between this point and that point i.e. (y1-y0)/(x1-x0).
        A horizontal line should be slope=0 and a vertical line should be positive infinity.
        The slope of a degenerate line segment (between a point and itself) should be negative infinity.
    */
    public double slopeTo(Point that) {
        return -1.0;
    }

    /**
        Compare two points by slopes they make with this point.
        Point (x1, y1) is less than the point (x2, y2) iff the slope (y1-y0)/(x1-x0) is less than the slope (y2-y0)/(x2-x0).
        Horizontal, vertical, and degenerate line segments should be treated same way as in the slopeTo() method.
    */
    public Comparator<Point> slopeOrder() {
       return null;
    }
}
