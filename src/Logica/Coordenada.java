
package Logica;

import java.io.Serializable;

/**
 * Clase pública relacionada a las coordenadas de cada grafo
 */
public class Coordenada implements Serializable{
    private int row;
    private int col;
     
    /**
     * Inicializador de la clase Coordenada.
     * @param x parametro en el eje x.
     * @param y parametro en el eje y.
     */
    public Coordenada(int x, int y) {
        this.row = x;
        this.col = y;
    }
    
    /**
     * Int público que obtiene el dato del eje x.
     * @return el int como tal. 
     */
    public int getX() {
        return row;
    }
    
    /**
     * Int público que obtiene el dato del eje y.
     * @return el int como tal.
     */
    public int getY() {
        return col;
    }
    
    /**
     * Método que logra setear un nuevo valor al eje x.
     * @param x el nuevo eje x por agregar.
     */
    public void setX(int x) {
        this.row = x;
    }
    
    /**
     * Método que logra setear un nuevo valor al eje y.
     * @param y el nuevo eje y por agregar.
     */
    public void setY(int y) {
        this.col = y;
    }
    
    @Override
    /**
     * String público que permite observar estos datos en formato "String"
     */
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

