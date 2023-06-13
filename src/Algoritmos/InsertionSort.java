package Algoritmos;

/**
 * clase que permite usar insertionsort para ordenar un array de enteros.
 */
public class InsertionSort {
    /**
     * Método estático encargado de inicializar la busquead por insertionSort.
     * @param array contiene la lista por ordenar.
     */
    public static void insertionSort(int[] array) {
        int n = array.length;
        for (int j = 1; j < n; j++) {
            int key = array[j];
            int i = j-1;
            while ( (i > -1) && ( array [i] > key ) ) {
                array [i+1] = array [i];
                i--;
            }
            array[i+1] = key;
        }
    }
}
