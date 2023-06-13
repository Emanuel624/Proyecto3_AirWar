package Aviones;

/**
 * Clase con los atributos del objeto Aeropuerto
 */
public class Aeropuerto {

    private final int cantidadCombustible;

    private final int capacidadMaxima;
    
    /**
     * Inicializador de la clase aeropuerto.
     * @param cantidadCombustible parametro integer relacionado a la cantidad de combusitble de cada aeropuerto.
     * @param capacidadMaxima parametro integer relacionado a la capacidad maxima de aviones de cada aeropuerto.
     */
    public Aeropuerto (int cantidadCombustible, int capacidadMaxima){
        this.cantidadCombustible = cantidadCombustible;
        this.capacidadMaxima = capacidadMaxima;
    }
    
    /**
     * Integer publico que obtiene la cantidad de combustible relacionado a cada aeropuerto.
     * @return la cantidad de combustible disponible.
     */
    public int getCantidadCombustible(){
        return cantidadCombustible;
    }
    
    /**
     * Integer publico que obtiene la cantidad maxima de aviones que puede tener cada aeropuerto.
     * @return la capacidad maxima de cada aeropuerto.
     */
    public int getCapacidadMaxima (){
        return capacidadMaxima;
    }
}
