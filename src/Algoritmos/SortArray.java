package Algoritmos;

/**
 * clase que se instancia para usar el algoritmo de SortArray
 */
public class SortArray {
    
    /**
     * Método estático encargado de inicializar la busquead por sort_sub.
     * @param array parametro relacionado a la lista por ordenar como tal.
     * @param size parametro encargado de conocer el tamaño de la lista por ordenar
     */
    public static void sort_sub(String[] array, int size) {
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (array[i].compareTo(array[j]) > 0) {
                    String temp = array[j];
                    array[i] = array[j];
                    array[i] = temp;
                }
            }
        }
    }
}

