package Listas;

import java.util.Arrays;

/**
 * @authors Randall Bryan Bolañoz López, Octavio Sanchez Soto, Emanuel Chavarría Hernández.
 * @version 1.0
 */


/**
 * Esta clase publica, se relaciona con la implementación desde 0, de la estructura lineal "ArrayList" ademas de ser serializable para ser enviada por sockets.
 * @param <E> permite añadir, modificar o eliminar elementos a la clase ArrayList
 */
public class ArrayLista<E>  {
    private transient int size = 0;
    private static final int DEFAULT_CAPACITY = 15;
    private Object[] elements;

    /**
     * La clase publica de array list, permite aplicar esta clase, fuera de esta clase como tal.
     */
    public ArrayLista() {
        elements = new Object[DEFAULT_CAPACITY];
    }


    /**
     * El metodo add, permite añadir elementos al arrayLista.
     * @param e este parametro es la información que se decia añadir en dicha array Lista.
     */
    public void add(int e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }
    /**
     * Este método se asegura de que la capacidad de la lista siempre sea la correcta.
     */
    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
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
