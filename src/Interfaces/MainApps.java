
package Interfaces;

import Logica.ControladorServer; //Importar de otro packete una clase
import Logica.JuegoLogica;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Esta clase, es la encargada de la ejecución de todas las interfaces gráficas para evitar problemas con los hilo de JavaFx
 * @authors Randall Bryan Bolañoz López, Octavio Sanchez Soto, Emanuel Chavarría Hernández.
 * @version 1.0
 */
public class MainApps extends Application {
    
    /**
     * Método estático para poder lanzar la ejecución de la GUI como tal.
     * @param args parametro necesario para la ejecuín del main. 
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    /**
     * Método encargado de iniciar la aplicación GUI como tal.
     */
    public void start(Stage primaryStage) throws IOException {
        // Instancias de las clases que contienen las interfaces
        JuegoLogica Juego = new JuegoLogica(primaryStage);
        ControladorServer Controlador = new ControladorServer();

        //  Inicializar y mostrar las interfaces
        Juego.display();
        Controlador.display();
    }
}
