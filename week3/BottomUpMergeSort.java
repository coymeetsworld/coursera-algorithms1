/**

More consise industrial-strength code, if space is available.

                      MERGESORTEXAMPLE
sz = 1
merge(a, 0, 0, 1)     EM RGESORTEXAMPLE [0-1]
merge(a, 2, 2, 3)     EM GR ESORTEXAMPLE [2-3]
merge(a, 4, 4, 5)     EMGR ES ORTEXAMPLE [4-5]
merge(a, 6, 6, 7)     EMGRES OR TEXAMPLE [6-7]
merge(a, 8, 8, 9)     EMGRESOR ET XAMPLE [8-9]
merge(a, 10, 10, 11)  EMGRESORET AX MPLE [10-11]
merge(a, 12, 12, 13)  EMGRESORETAX MP LE [12-13]
merge(a, 14, 14, 15)  EMGRESORETAXMP EL [14-15]

sz = 2
merge(a, 0, 1, 3)     EGMR ESORETAXMPEL [0-3]
merge(a, 4, 5, 7)     EGMR EORS ETAXMPEL [4-7]
merge(a, 8, 9, 11)    EGMREORS AETX MPEL [8-11]
merge(a, 12, 13, 15)  EGMREORSAETX ELMP [12-15]

sz = 4
merge(a, 0, 3, 7)     EEGMORRS AETXELMP [0-7]
merge(a, 8, 11, 15)   EEGMORRS AEELMPTX [8-15]

sz = 8
merge(a, 0, 7, 15)    AEEEEGLMMOPRRSTX [0-15]

*/
public class BottomsUpMergeSort {

    private static Comparable[] aux;

    private static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz+sz)
            for (int lo = 0; lo < N-sz; lo += sz+sz)
                merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
    }

    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length]; // need to declare aux?
        sort(a, aux, 0, a.length - 1);
    }

    // Expected two halfs that are sorted.
    private static void merge(Comparable[] a, int low, int mid, int hi) {
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
