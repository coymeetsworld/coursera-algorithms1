/******************************************************************************
 *  @author: Coy Sanders
 *  @version: 05/20/2017
 *
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        // for horizontal slope
        if (that.y == y && that.x == x) return Double.NEGATIVE_INFINITY;
        if (that.x - x == 0)            return Double.POSITIVE_INFINITY;
        if (that.y - y == 0)            return +0.0; // check if division below does this.
        // return (double)(that.y - y)/(that.x - x);
        return 1.0*(that.y - y)/(that.x - x); // which is faster to cast?
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (y == that.y) return x - that.x;
        return y - that.y;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * The slopeOrder() method should return a comparator that compares its two argument points by the slopes they make with
     * the invoking point (x0, y0). Formally, the point (x1, y1) is less than the point (x2, y2)
     * if and only if the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0).
     *
     * Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        class SlopeOrder implements Comparator<Point> {
            public int compare(Point q1, Point q2) {
                if (slopeTo(q1) > slopeTo(q2)) return 1;
                else if (slopeTo(q1) < slopeTo(q2)) return -1;
                return 0; // They are equal;
            }
        }
        return new SlopeOrder();
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }


    private static void testSlopeTo() {
        System.out.println("--------------------------------------------------");
        System.out.println("Testing slopeTo()");
        System.out.println("--------------------------------------------------");
        Point p = new Point(3, 5);
        Point q = new Point(7, 9);
        System.out.println("Should be slope of 1.0: " + p.slopeTo(q));

        p = new Point(0, 0);
        q = new Point(2, 4);
        System.out.println("Should be slope of 2.0: " + p.slopeTo(q));

        p = new Point(1, 4);
        q = new Point(4, 25);
        System.out.println("Should be slope of 7.0: " + p.slopeTo(q));

        p = new Point(1, 4);
        q = new Point(3, 13);
        System.out.println("Should be slope of 4.5: " + p.slopeTo(q));

        // Horizontal line (y2 = y1);
        p = new Point(0, 10);
        q = new Point(5, 10);
        System.out.println("Should be slope of +0.0: " + p.slopeTo(q));

        // Same point
        p = new Point(0, 0);
        q = new Point(0, 0);
        System.out.println("Should be slope of -Infinity: " + p.slopeTo(q));

        p = new Point(-2, -50);
        q = new Point(-2, -50);
        System.out.println("Should be slope of -Infinity: " + p.slopeTo(q));

        // Same point
        p = new Point(5, 5);
        q = new Point(5, 5);
        System.out.println("Should be slope of -Infinity: " + p.slopeTo(q));

        // Vertical line (x2 = x1);
        p = new Point(5, 10);
        q = new Point(5, 25); 
        System.out.println("Should be slope of Infinity: " + p.slopeTo(q));

        // Vertical line (x2 = x1);
        p = new Point(-5, 10);
        q = new Point(-5, 25); 
        System.out.println("Should be slope of Infinity: " + p.slopeTo(q));
    }

    private static void testCompareTo() {
        System.out.println("--------------------------------------------------");
        System.out.println("Testing compareTo()");
        System.out.println("--------------------------------------------------");

        // returns negative if this point less than argument point
        // returns positive int if point greather than argument point
        // point is greater if arg.y > this.y, or if both equal arg.x > this.x
        System.out.println("Testing when x values are equal, y values are not");
        Point p = new Point(0, 0);
        Point q = new Point(0, 1);
        System.out.println("should return negative value: " + p.compareTo(q));

        p = new Point(1, 1);
        q = new Point(1, 3);
        System.out.println("should return negative value: " + p.compareTo(q));

        p = new Point(0, -3);
        q = new Point(0, -1);
        System.out.println("should return negative value: " + p.compareTo(q));

        p = new Point(0, -1);
        q = new Point(0, -3);
        System.out.println("should return positive value: " + p.compareTo(q));

        p = new Point(0, 10);
        q = new Point(0, 5);
        System.out.println("should return positive value: " + p.compareTo(q));

        p = new Point(0, 1);
        q = new Point(0, 0);
        System.out.println("should return positive value: " + p.compareTo(q));

        System.out.println("Testing when y values are equal, x values are not");

        p = new Point(1, 1);
        q = new Point(3, 1);
        System.out.println("should return negative value: " + p.compareTo(q));

        p = new Point(0, -3);
        q = new Point(5, -3);
        System.out.println("should return negative value: " + p.compareTo(q));

        p = new Point(-2, 0);
        q = new Point(1, 0);
        System.out.println("should return negative value: " + p.compareTo(q));

        p = new Point(1, 0);
        q = new Point(0, 0);
        System.out.println("should return positive value: " + p.compareTo(q));

        p = new Point(5, 10);
        q = new Point(-5, 10);
        System.out.println("should return positive value: " + p.compareTo(q));

        p = new Point(2, -10);
        q = new Point(1, -10);
        System.out.println("should return positive value: " + p.compareTo(q));

        p = new Point(-2, -10);
        q = new Point(-8, -10);
        System.out.println("should return positive value: " + p.compareTo(q));

        System.out.println("Testing when points are equal");

        p = new Point(1, 1);
        q = new Point(1, 1);
        System.out.println("should return 0: " + p.compareTo(q));

        p = new Point(-3, -3);
        q = new Point(-3, -3);
        System.out.println("should return 0: " + p.compareTo(q));

        p = new Point(55, 55);
        q = new Point(55, 55);
        System.out.println("should return 0: " + p.compareTo(q));

    }

    private static void testSlopeOrder() {
        System.out.println("--------------------------------------------------");
        System.out.println("Testing slopeOrder()");
        System.out.println("--------------------------------------------------");

        System.out.println("Testing when x values are equal, y values are not");
        Point p = new Point(0, 0);
        Point q = new Point(1, 2);
        Point r = new Point(1, 4);
        System.out.println("should return negative value: " + p.slopeOrder().compare(q,r));

        /*p = new Point(1, 1);
        q = new Point(1, 3);
        r = new Point(1, 3);
        System.out.println("should return negative value: " + p.compareTo(q));

        p = new Point(0, -3);
        q = new Point(0, -1);
        System.out.println("should return negative value: " + p.compareTo(q));*/
    }


    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        testSlopeTo();
        testCompareTo();
        testSlopeOrder();
    }
}
