package Listas;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
/**
 * Clase pública, que conllvea la implementación de una forma de visualizar la clase ListaEnlazada de una forma amigable con el usuario.
 * @authors Randall Bryan Bolañoz López, Octavio Sanchez Soto, Emanuel Chavarría Hernández.
 * @version 1.0
 * @param <C> este parametro permite añadir, modificar y eliminar elementos de la lista Enlazada View.
 */
public class ListaEnlazadaView<C> extends ListaEnlazada<C> {
    private final ListView<C> listView;
    private ObservableList<C> observableList;


    /**
     * Constructor de la clase ListaEnlazadaView.
     */
    public ListaEnlazadaView() {
        super();
        listView = new ListView<>();
    }

    /**
     * ListView public, que permite  obtener la "View" de la lista enlazada.
     * @return retorna la listaView como tal.
     */
    public ListView<C> getListView() {
        return listView;
    }


    /**
     * Este método permite añadir datos a la "view" de la lista enlazada.
     * @param data es la data que se desea añadir, actualizando.
     */
    @Override
    public void add(C data) {
        super.add(data);
        updateListView();
    }


    /**
     * Esta listaEnlazada publica, permite obtener loa listaEnalzada como tal sin en el formato "view".
     * @return la lista enlazada como tal.
     */
    public ListaEnlazada<C> getListaEnlazada() {
        ListaEnlazada<C> listaEnlazada = new ListaEnlazada<>();
        forEach(listaEnlazada::add);
        return listaEnlazada;
    }


    /**
     * Nodo público que permite eliminar el primer elmento de la ListaEnlazadaView.
     * @return retorna el nodo por eliminar.
     */
    @Override
    public NodeLista<C> deleteFirst() {
        NodeLista<C> deletedNode = super.deleteFirst();
        updateListView();
        return deletedNode;
    }


    /**
     * Este nodoLisa permite eliminar elementos en listaEnlazadaView.
     * @param searchValue este parametro es el elmento que como tal se quiere eliminar.
     * @return se retorna el nodo por eliminar.
     */
    @Override
    public NodeLista<C> delete(Object searchValue) {
        NodeLista<C> deletedNode = super.delete(searchValue);
        updateListView();
        return deletedNode;
    }


    /**
     * Método privado que permite actualizar la listaView como tal.
     */
    private void updateListView() {
        ObservableList<C> observableList = FXCollections.observableArrayList();
        forEach(observableList::add);
        listView.setItems(observableList);
    }


    /**
     * Este método pubico, permite setear la lista Enlazada para ser movida a la ListaEnlazadaView como tal.
     * @param lista este parametro, permite recibir la lista necesaria.
     */
    public void setListaEnlazada(ListaEnlazada<C> lista) {
        listView.getItems().clear();
        if (lista != null) {
            lista.forEach(item -> listView.getItems().add(item));
        }
    }


    /**
     * Este método permite remover cualquier elemento dentro de esta ListaEnlazadaView.
     * @param item este parametro es el item por eliminar como tal.
     */
    public void remove(C item) {
        listView.getItems().remove(item);
    }


    /**
     * Este método público permite colocar items dentro de la ListaEnlazadView.
     * @param items es el elemento quese desea colocar
     */
    public void setItems(ObservableList<C> items) {
        listView.setItems(items);
    }


    /**
     * Esta constructor permite obtener la lista observale de la ListaEnlazadaView.
     * @return se retorna la lista observable como tal.
     */
    public ObservableList<C> getObservableList() {
        ObservableList<C> observableList = FXCollections.observableArrayList();
        forEach(observableList::add);
        return observableList;
    }


    /**
     * Este método permite actualizar la listaEnlazadaView como tal.
     */
    public void refresh() {
        // Limpiar y volver a agregar los elementos a la lista observable
        List<C> tempList = new ArrayList<>(observableList);
        observableList.clear();
        observableList.addAll(tempList);
    }
}