package Listas;

import java.util.Arrays;

public class ArrayListaString<E>  {
    private transient int size = 0;
    private String[] elements;

    /**
     * Clase ArrayListaString que crea un array de strings especificamente
     */
    public ArrayListaString() {

        elements = new String[15];
    }

    /**
     * Este método se asegura de que la capacidad de la lista siempre sea la correcta.
     */
    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }
    /**
     * El metodo add, permite añadir elementos (strings) al arrayLista.
     * @param e este parametro es la información que se decia añadir en dicha array Lista.
     */
    public void addString(String e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }
    /**
     * metodo que devuelve el indice del array donde se encuentra un elemento
     * @param index indice
     * @return el indice donde se encuentra un elemento
     */
    public E get(int index) {

        return (E) elements[index];
    }
    /**
     * metodo que accede al tamano del array
     * @return el tamano
     */
    public int size() {
        return this.size;
    }
}