
package Logica;

import java.io.Serializable;

public class Coordenada implements Serializable{
    private int row;
    private int col;
    
    public Coordenada(int x, int y) {
        this.row = x;
        this.col = y;
    }
    
    public int getX() {
        return row;
    }
    
    public int getY() {
        return col;
    }
    
    public void setX(int x) {
        this.row = x;
    }
    
    public void setY(int y) {
        this.col = y;
    }
    
    // Sobrescribir el método toString() para imprimir las coordenadas de forma legible
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}

