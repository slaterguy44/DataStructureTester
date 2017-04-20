package sort;

/**
 * Big-O time of O(n*n)
 *
 * @author 55jphillip
 */
public class SimpleSorts {

    public static void bubbleSort(int[] a, String direction) {
        int out, in;
        boolean moreChanges = true;

        for (out = a.length - 1; out > 0 && moreChanges; out--) {
            moreChanges = false;
            for (in = 0; in < out; in++) {
                if (a[in] > a[in + 1] && direction.equalsIgnoreCase("A")
                        || a[in] < a[in + 1] && direction.equalsIgnoreCase("D")) {
                    int temp = a[in];
                    a[in] = a[in + 1];
                    a[in + 1] = temp;
                    moreChanges = true;
                }
            }
        }
    }

    public static void selectionSort(int[] a, String direction) {
        int out, in, min;
        for (out = 0; out < a.length - 1; out++) {
            min = out;
            for (in = out + 1; in < a.length ; in++) {
                if (a[in] < a[min] && direction.equalsIgnoreCase("A")
                        || a[in] > a[min] && direction.equalsIgnoreCase("D")) {
                    min = in;
                }
            }
            int temp = a[out];
            a[out] = a[min];
            a[min] = temp;
        }
    }

    public static void insertionSort(int[] a, String direction) {
        int in, out;
        if (direction.equalsIgnoreCase("A")) {
            for (out = 1; out < a.length; out++) {
                int temp = a[out];
                in = out;
                while (in > 0 && a[in - 1] > temp) {
                    a[in] = a[in - 1];
                    --in;
                }
                a[in] = temp;
            }
        } else if (direction.equalsIgnoreCase("D")) {
            for (out = 1; out < a.length; out++) {
                int temp = a[out];
                for (in = out - 1; (in >= 0) && (a[in] < temp); in--) {
                    a[in + 1] = a[in];
                }
                a[in + 1] = temp;
            }
        }
    }

}
