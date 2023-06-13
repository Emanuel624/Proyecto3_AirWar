package Algoritmos;

/**
 * clase que se instancia para usar el algoritmo de ShellSort
 */
public class ShellSort {
    /**
     * Método estático encargado de inicializar la busquead por shellSort.
     * @param arrayToSort contiene la lista por ordenar.
     */
    public static void shellSort(int[] arrayToSort) {
        int n = arrayToSort.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = arrayToSort[i];
                int j = i;
                while (j >= gap && arrayToSort[j - gap] > key) {
                    arrayToSort[j] = arrayToSort[j - gap];
                    j -= gap;
                }
                arrayToSort[j] = key;
            }
        }
    }
}
