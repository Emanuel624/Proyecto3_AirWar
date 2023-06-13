package Algoritmos;

import java.util.ArrayList;

/**
 * clase con la logica de BinarySearch para buscar aviones por su nombre
 */
public class BinarySearch {
        // Returns index of x if it is present in arr[],
        // else return -1
        public static int binarySearch(ArrayList<String> arr, String x)
        {
            int l = 0, r = arr.size() - 1;

            // Loop to implement Binary Search
            while (l <= r) {

                // Calculating mid
                int m = l + (r - l) / 2;

                int res = x.compareTo(arr.get(m));

                // Check if x is present at mid
                if (res == 0)
                    return m;

                // If x greater, ignore left half
                if (res > 0)
                    l = m + 1;

                    // If x is smaller, ignore right half
                else
                    r = m - 1;
            }
            return -1;
        }
}

