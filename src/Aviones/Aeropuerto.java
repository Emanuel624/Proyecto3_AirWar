package Aviones;

public class Aeropuerto {

    private final int cantidadCombustible;

    private final int capacidadMaxima;

    public Aeropuerto (int cantidadCombustible, int capacidadMaxima){
        this.cantidadCombustible = cantidadCombustible;
        this.capacidadMaxima = capacidadMaxima;
    }
    public int getCantidadCombustible(){
        return cantidadCombustible;
    }
    public int getCapacidadMaxima (){
        return capacidadMaxima;
    }
}
