
package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo<T> implements Serializable{
    private final Map<Coordenada, List<Arista<T>>> adyacencia;

    public Grafo() {
        adyacencia = new HashMap<>();
    }

    public void agregarVertice(Coordenada vertice) {
        adyacencia.put(vertice, new ArrayList<>());
    }

    public void agregarArista(Coordenada origen, Coordenada destino, double peso, String tipo) {
        List<Arista<T>> adyacentes = adyacencia.computeIfAbsent(origen, k -> new ArrayList<>());
        Arista<T> arista = new Arista<>(origen, destino, peso, tipo);
        adyacentes.add(arista);
    }

    public List<Coordenada> obtenerVertices() {
        return new ArrayList<>(adyacencia.keySet());
    }

    public List<Arista<T>> obtenerAristas(Coordenada vertice) {
        return adyacencia.get(vertice);
    }

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



