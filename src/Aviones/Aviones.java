package Aviones;

/**
 * clase con los metodos y atributos del objeto aviones
 */
public class Aviones {
    private final String nombre;

    private final int velocidad;

    private final int eficiencia;

    private final int fortaleza;
    
    /**
     * Inicializador de la clase Aviones.
     * @param nombre parametro String relacionado al nombre de cada Avión.
     * @param velocidad parametro int relacionado a la velocidad de cada Avión.
     * @param eficiencia parametro int relacionado a la eficiencia de cada Avión.
     * @param fortaleza parametro int relacionado a la fortaleza de cada Avión.
     */
    public Aviones(String nombre, int velocidad, int eficiencia, int fortaleza){
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.eficiencia = eficiencia;
        this.fortaleza = fortaleza;
    }
    
    /**
     * String publico que obtiene el nombre de cada avión.
     * @return el nombre de cada avión como tal.
     */
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Int publico que obtiene la velocidad cada avión.
     * @return la velocidad como tal de cada avión.
     */
    public int getVelocidad(){
        return velocidad;
    }
    
    /**
     * Int publico que obtiene la velocidad cada avión.
     * @return la eficiencia como tal de cada avión.
     */
    public int getEficiencia() {
        return eficiencia;
    }
    
    /**
     * Int publico que obtiene la fotaleza cada avión.
     * @return la fortaleza como tal de cada avión.
     */
    public int getFortaleza (){
        return fortaleza;
    }
    
    
    @Override
    /**
     * String público que permite pasar los datos al formato "Strin" para ser leidos por desarrolados y usuario de mejor forma.
     */
    public String toString() {
        return this.nombre;
    }

}
