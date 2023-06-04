package Aviones;


public class Aviones {
    private final String nombre;

    private final int velocidad;

    private final int eficiencia;

    private final int fortaleza;

    public Aviones(String nombre, int velocidad, int eficiencia, int fortaleza){
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.eficiencia = eficiencia;
        this.fortaleza = fortaleza;
    }
    public String getNombre(){
        return nombre;
    }
    public int getVelocidad(){
        return velocidad;

    }
    public int getEficiencia() {
        return eficiencia;
    }
    public int getFortaleza (){
        return fortaleza;
    }
    @Override
    public String toString() {
        return this.nombre;
    }

}
