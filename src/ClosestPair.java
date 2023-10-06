import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Pair {
    public int x;
    public int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

public class ClosestPair {

    public static double closestPair(Pair[] P, int n) {
        //Sort By 'x' co-oridante
        Pair[] xSorted = Arrays.copyOf(P, n);
        Arrays.sort(xSorted, (p1, p2) -> p1.x - p2.x);

        //Sort by 'y' co-ordinate
        Pair[] ySorted = Arrays.copyOf(P, n);
        Arrays.sort(ySorted, (p1, p2) -> p1.y - p2.y);

        // Use recursive function closestUtil() to find the smallest distance
        return divideNConquer(xSorted, ySorted, n);
    }

    private static double divideNConquer(Pair[] xPair, Pair[] yPair, int n) {

        // If there are no more than 3 points, use brute force
        if (n <= 3) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < n; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    double dist = eucledianDistance(xPair[i], xPair[j]);
                    if (dist < min) {
                        min = dist;
                    }
                }
            }
            return min;
        }

        // Determine the mid-point
        int mid = n / 2;
        Pair midPoint = xPair[mid];

        Pair[] ySortedLeft = Arrays.copyOfRange(yPair, 0, mid);// 'y' sorted points till the mid-point (left-side)
        Pair[] ySortedRight = Arrays.copyOfRange(yPair, mid, n);//'y' sorted points on after the mid-point (right-side)

        //Passing the left half
        double dl = divideNConquer(xPair, ySortedLeft, mid);
        //Passing the right half
        double dr = divideNConquer(Arrays.copyOfRange(yPair, mid, n), ySortedRight, n - mid);

        // Pick the smaller of the two distances
        double d = Math.min(dl, dr);

        // Begin process of creating bands within 'd' distance of the vertical line
        //Use the 'y' presorted pairs
        List<Pair> band = new ArrayList<Pair>();
        for (Pair p : yPair) {
            if (Math.abs(p.x - midPoint.x) < d) {
                band.add(p);
            }
        }

        return closestInBand(band.toArray(new Pair[band.size()]), band.size(), d);
    }

    private static double closestInBand(Pair[] strip, int size, double d) {
        double min = d; // Initialize the minimum distance as 'd'

        // Traverse through the adjacent points only  (which will simply take 'n' time), and then compare the required distance
        for (int i = 0; i < size; ++i) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; ++j) {
                double dist = eucledianDistance(strip[i], strip[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    private static double eucledianDistance(Pair p1, Pair p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }


    public static void main(String[] args) {


        Pair[] P1 = { new Pair(2, 3), new Pair(12, 30), new Pair(40, 50),
                new Pair(5, 1), new Pair(12, 10), new Pair(3, 5)};
        Pair[] P2 = { new Pair(2, 3), new Pair(12, 30), new Pair(40, 50),
                new Pair(5, 1), new Pair(12, 10), new Pair(3, 5), new Pair(43,22)};
        Pair[] P3 = { new Pair(2, 3), new Pair(12, 30), new Pair(40, 50),
                new Pair(5, 1), new Pair(12, 10), new Pair(3, 5), new Pair(43,22), new Pair(19,9)};
        Pair[] P4 = { new Pair(2, 3), new Pair(12, 30), new Pair(40, 50),
                new Pair(5, 1), new Pair(12, 10), new Pair(3, 5), new Pair(43,22), new Pair(19,9), new Pair(14, 2)};
        Pair[] P5 = { new Pair(2, 3), new Pair(12, 30), new Pair(40, 50),
                new Pair(5, 1), new Pair(12, 10), new Pair(3, 5), new Pair(43,22), new Pair(19,9), new Pair(14, 2),
                new Pair(29,32)};

        long start = System.nanoTime();

        /*====FOR DIFFERENT INPUT VALUES, CHANGE HERE=====*/
        System.out.println("Input Size - " + P1.length);
        System.out.println("The smallest distance is " + closestPair(P1, P1.length));

        long end = System.nanoTime();

        long elapsedTime = end-start;

        //double elapsedTimeInSecond = (double) elapsedTime / 1000;
        System.out.println(elapsedTime);

    }

}