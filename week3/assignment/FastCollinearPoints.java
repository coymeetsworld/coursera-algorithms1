
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
public class FastCollinearPoints {

    /**
        Finds all line segments containing 4 points.
    */
    public FastCollinearPoints(Point[] points) {

    }


    /**
        The number of line segments.
    */
    public int numberOfSegments() {
        return -1;
    }


    /**
        Includes each line segment containing 4 points exactly once.
        If 4 points appear on a line segments in the order of p->q->r->s, it should include either the line segment p->s or s->p,
        but not both. It should also not include subsegments like p->r or q->r.
        For simplicity, no input given to BruteCollinearPoints will have 5 or more collinear points.
    */
    public LineSegment[] segments() {
        return null;
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
