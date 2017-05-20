// java -ea MyProgram //enable assertions (no cost in production code.)
// java -da MyProgram //disable assertions (default setting).

public class MergeSort {

    private static final int CUTOFF = 7; // mergesort has too much overhead for tiny subarrays.

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo + CUTOFF - 1) {
          Insertion.sort(a, lo, hi);
          return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid); // sort first half
        sort(aux, a, mid+1, hi); // sort second half
        // If biggest item in first half <= smallest item in second, stop since it's already sorted.
        // Helps for partially-ordered array
        if (!less(a[mid+1], a[mid])) return;
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length]; // need to declare aux?
        sort(a, aux, 0, a.length - 1);
    }

    // Expected two halfs that are sorted.
    private static void merge(Comparable[] a, Comparable[] aux, int low, int mid, int hi) {
        assert isSorted(a, lo, mid); // precondition a[lo..mid] sorted
        assert isSorted(a, mid+1, hi); // precondition a[mid+1..hi] sorted

        for (int k = lo; k <= hi; k++) // copy
           aux[k] = a[k];

        int i = lo, j = mid+1;
        
        for (int k = lo; k <= hi; k++) { // merge
            if (i > mid)                   aux[k] = a[j++]; // nothing left in first half
            else if (j > hi)               aux[k] = a[i++]; // nothing left in 2nd half
            else if (less(aux[j], aux[i])) aux[k] = a[j++];
            else                           aux[k] = a[i++];
        }
        assert isSorted(a, lo, hi); // postcondition: a[lo..hi] sorted
    }
}
