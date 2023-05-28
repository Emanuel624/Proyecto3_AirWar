
package Lógica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grafo<T> {
    private Map<Coordenada, List<Coordenada>> adyacencia;

    public Grafo() {
        adyacencia = new HashMap<>();
    }

    public void agregarVertice(Coordenada vertice) {
        adyacencia.put(vertice, new ArrayList<>());
    }

    public void agregarArista(Coordenada origen, Coordenada destino) {
        List<Coordenada> adyacentes = adyacencia.get(origen);
        if (adyacentes == null) {
            adyacentes = new ArrayList<>();
            adyacencia.put(origen, adyacentes);
        }
        adyacentes.add(destino);
    }



    public List<Coordenada> obtenerVertices() {
        return new ArrayList<>(adyacencia.keySet());
    }

    public List<Coordenada> obtenerAdyacentes(Coordenada vertice) {
        return adyacencia.get(vertice);
    }

    public boolean esAdyacente(Coordenada vertice1, Coordenada vertice2) {
        return adyacencia.get(vertice1).contains(vertice2);
    }
}



