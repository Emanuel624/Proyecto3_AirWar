
package Lógica;

public class Arista<T> {
    private Coordenada origen;
    private Coordenada destino;
    private double peso;
    private String tipo; // Nuevo campo para almacenar el tipo de arista

    public Arista(Coordenada origen, Coordenada destino, double peso, String tipo) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.tipo = tipo;
    }

    public Coordenada getOrigen() {
        return origen;
    }

    public Coordenada getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}



