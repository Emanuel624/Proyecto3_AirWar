
package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase pública relacionada para la creación del grafo.
 * @param <T> parametro que permite recibir nuevos datos en el grafo
 */
public class Grafo<T> implements Serializable{
    private final Map<Coordenada, List<Arista<T>>> adyacencia;
    
    /**
     * Inicializador de la clase Grafo. 
     */
    public Grafo() {
        adyacencia = new HashMap<>();
    }
    
    /**
     * Método que se encarga de agregar un nuevo vertice al grafo.
     * @param vertice parametro Coordenada que añade un nuevo vertice.
     */
    public void agregarVertice(Coordenada vertice) {
        adyacencia.put(vertice, new ArrayList<>());
    }
    
    /**
     * Lógica detras del método agregarArista en el grafo.
     * @param origen que permite coordenada origen.
     * @param destino que permite coordenada destino.
     * @param peso que permite el peso de la arista entre cada vertice.
     * @param tipo que permite el tipo de la arista entre cada vertice.
     */
    public void agregarArista(Coordenada origen, Coordenada destino, double peso, String tipo) {
        List<Arista<T>> adyacentes = adyacencia.computeIfAbsent(origen, k -> new ArrayList<>());
        Arista<T> arista = new Arista<>(origen, destino, peso, tipo);
        adyacentes.add(arista);
    }
    
    /**
     * Lista pública que obtiene los vertices del grafo ya creado.
     * @return la lista como tal.
     */
    public List<Coordenada> obtenerVertices() {
        return new ArrayList<>(adyacencia.keySet());
    }
    
    /**
     * Lista pública que obtiene las Aristas del grafo ya creado.
     * @param vertice Coordenada relacionada al vertice. 
     * @return la lista como tal.
     */
    public List<Arista<T>> obtenerAristas(Coordenada vertice) {
        return adyacencia.get(vertice);
    }
    
    /**
     * Lista pública obtenerAdyacentes al grafo.
     * @param vertice Coordenada relacionada al vertice.
     * @return la lista de adyacentes como tal.
     */
    public List<Coordenada> obtenerAdyacentes(Coordenada vertice) {
        List<Arista<T>> aristas = adyacencia.get(vertice);
        List<Coordenada> adyacentes = new ArrayList<>();
        if (aristas != null) {
            for (Arista<T> arista : aristas) {
                adyacentes.add(arista.getDestino());
            }
        }
        return adyacentes;
    }
    
    /**
     * Booleano público que permite conocer si es esAdyacente un vertice de otro.
     * @param vertice1 primer vertice a comparar.
     * @param vertice2 segunda vertice a comparar.
     * @return retorna el booleano
     */
    public boolean esAdyacente(Coordenada vertice1, Coordenada vertice2) {
        List<Arista<T>> aristas = adyacencia.get(vertice1);
        if (aristas != null) {
            for (Arista<T> arista : aristas) {
                if (arista.getDestino().equals(vertice2)) {
                    return true;
                }
            }
        }
        return false;
    }
}



