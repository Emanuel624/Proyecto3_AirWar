
package Logica;

import java.io.Serializable;

/**
 * Clase utilizada para generar las aristas del grafo creado.
 * @param <T> cada arista puede tener datos relacionados a la misma.
 */
public class Arista<T>  implements Serializable{
    private final Coordenada origen;
    private final Coordenada destino;
    private final double peso;
    private String tipo; // Nuevo campo para almacenar el tipo de arista
    
    /**
     * Inicializador de la clase Arista.
     * @param origen paramétro Coordenada relacaionado a las coordenadas donde se dirige la Arista.
     * @param destino paramétro Coordenada relacaionado al destino donde se dirige la Arista.
     * @param peso paramétro double relacaionado al peso donde se dirige la Arista.
     * @param tipo paramétro Coordenada relacaionado al tipo donde se dirige la Arista.
     */
    public Arista(Coordenada origen, Coordenada destino, double peso, String tipo) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.tipo = tipo;
    }
    
    /**
     * Coordenada pública que obtiene el Origen de la Arista.
     * @return el origen como tal.
     */
    public Coordenada getOrigen() {
        return origen;
    }
    
    /**
     * Coordenada publica que obtiene el destino de la Arista.
     * @return el destino como tal.
     */
    public Coordenada getDestino() {
        return destino;
    }
    
    /**
     * Double relacionado al Peso de cada arista.
     * @return se retorna el peso.
     */
    public double getPeso() {
        return peso;
    }
    
    /**
     * String público que obtiene el tipo de Arista que es.
     * @return el tipo como tal.
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Método publico que setea el tipo de cada Arista
     * @param tipo relacionado al tipo String que se le quiere relacionar a cada Arista
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}



