package Algoritmos;

import java.util.ArrayList;

/**
 * clase con la logica de BinarySearch para buscar aviones por su nombre
 */
public class BinarySearch {

        /**
         * Método de busqueda binario desarrollado desde 0
         * @param arr este parametro esta relacionado con el arreglo que se quiere comparar
         * @param x este parametro permite la comparación entre 
         * @return retorna el indice de la busqueda
         */
        public static int binarySearch(ArrayList<String> arr, String x)
        {
            int l = 0, r = arr.size() - 1;

            // Ciclo
            while (l <= r) {

                // Calcular la mitad.
                int m = l + (r - l) / 2;

                int res = x.compareTo(arr.get(m));


                // Revisar que x esta presente en la mitad.
                if (res == 0)
                    return m;

                // Si x es mayor, ignore la mitad izquierda. 
                if (res > 0)
                    l = m + 1;


                    // Si x es menor, ignore la mitad.
                else
                    r = m - 1;
            }
            return -1;
        }
}

