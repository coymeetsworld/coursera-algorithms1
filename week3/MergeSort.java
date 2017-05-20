// java -ea MyProgram //enable assertions (no cost in production code.)
// java -da MyProgram //disable assertions (default setting).

public class MergeSort {

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid); // sort first half
        sort(a, aux, mid+1, hi); // sort second half
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
        for (int k = lo; k <= hi; k++) {// merge
            if (i > mid)                   a[k] = aux[j++]; // nothing left in first half
            else if (j > hi)               a[k] = aux[i++]; // nothing left in 2nd half
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }
        assert isSorted(a, lo, hi); // postcondition: a[lo..hi] sorted
    }
}