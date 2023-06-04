package Listas;

import java.util.Arrays;

public class ArrayListaString<E>  {
    private transient int size = 0;
    private String[] elements;

    /**
     * La clase publica de array list, permite aplicar esta clase, fuera de esta clase como tal.
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
     * El metodo add, permite añadir elementos al arrayLista.
     * @param e este parametro es la información que se decia añadir en dicha array Lista.
     */
    public void addString(String e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }
    public E get(int index) {

        return (E) elements[index];
    }
    public int size() {
        return this.size;
    }
}