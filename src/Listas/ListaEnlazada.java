package Listas;


import java.util.function.Consumer;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;


    /**
     * Clase publica asociada a la implementación desde 0, de una Lista Enlazada.
     * @authors Randall Bryan Bolañoz López, Octavio Sanchez Soto, Emanuel Chavarría Hernández.
     * @version 1.0
     * @param <C> este parametro permite añadir, modificar y eliminar elementos de la lista Enlazada.
     */
    public class ListaEnlazada<C> implements Iterable<C> {

        private NodeLista<C> head;
        private int size;

        /**
         * constructor de la clase lista enlazada
         */
        public ListaEnlazada() {
            this.head = null;
            this.size = 0;

        }


        /**
         * metodo que verifica si la lista esta vacia
         * @return devuelve si el primer elemento es nulo
         */
        public boolean isEmpty() {
            return this.head == null;
        }


        /**
         * metodo que dice el tamano de la lista
         * @return tamano int
         */
        public int size() {
            return this.size;
        }


        /**
         * inserta una celda a la lista
         * @param data celda
         */
        public void add(C data) {
            NodeLista<C> newNode = new NodeLista<C>(data);
            newNode.setNext(this.head);
            this.head = newNode;
            this.size++;
        }


        /**
         * borra el primer elemento de la lista
         * @return nulo node
         */
        public NodeLista<C> deleteFirst() {
            if (this.head != null) {
                NodeLista<C> temp = this.head;
                this.head = this.head.getNext();
                this.size--;
                return temp;
            } else {
                return null;
            }
        }


        /**
         * imprime la lista con sus elementos
         */
        public void displayList() {
            NodeLista<C> current = this.head;
            while (current != null) {
                System.out.println(current.getData());
                current = current.getNext();
            }

        }


        /**
         * metodo que elimina cierto valor especifico
         * @param searchValue valor a eliminar
         * @return nulo node
         */
        public NodeLista<C> delete(Object searchValue) {
            NodeLista<C> current = this.head;
            NodeLista<C> previous = this.head;
            while (current != null) {
                if (current.getData().equals(searchValue)) {
                    if (current == this.head) {
                        this.head = this.head.getNext();
                    } else {
                        previous.setNext(current.getNext());
                    }
                    return current;
                } else {
                    previous = current;
                    current = current.getNext();
                }
            }
            return null;
        }


        /**
         * Convertir la lista enlazada a un Array
         * @param array necesita un arraya para poder convertir la lista en array.
         * @return retorna el array con la información obtenida.
         */
        public C[] toArray(C[] array) {
            if (array.length < size) {
                array = (C[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), size);
            }

            NodeLista<C> current = head;
            for (int i = 0; i < size; i++) {
                array[i] = current.getData();
                current = current.getNext();
            }

            return array;
        }


        /**
         * metodo que aplica una accion a cada uno de los nodos de la lista
         * @param action accion
         */
        public void forEach(Consumer<? super C> action) {
            for (NodeLista<C> current = head; current != null; current = current.getNext()) {
                action.accept(current.getData());
            }
        }


        /**
         * Iterador público utilizado para recorrer la lista Enlazada
         * @return la lista enlazada iterada
         */
        public Iterator<C> iterator() {
            return new ListIterator();
        }


        /**
         * Clase privada del iterador, la cual contiene toda la lógica del contructor publico "iterator()".
         */
        private class ListIterator implements Iterator<C> {
            private NodeLista<C> current = head;

            /**
             * Booleano publico el cual obtien si hay un valor despues del actual.
             * @return un valor booleano si tiene o no siguiente.
             */
            @Override
            public boolean hasNext() {
                return current != null;
            }

            /**
             * Obtiene el valor del nodo siguiente despues de verficar si, si tiene un valor disponible
             * @return el valor obtenido como tal
             */
            @Override
            public C next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                C data = current.getData();
                current = current.getNext();
                return data;
            }
        }


        /**
         * Remueve el primer valor del arbol binario.
         * @return retorna el arbol binario sin el primer nodo.
         */
        public C removeFirst() {
            if (head != null) {
                C data = head.getData();
                head = head.getNext();
                size--;
                return data;
            }
            return null;
        }
    }

