package Algoritmos;

public class SortArray {
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

