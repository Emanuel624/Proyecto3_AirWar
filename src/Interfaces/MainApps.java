
package Interfaces;

import Logica.ControladorServer; //Importar de otro packete una clase
import Logica.JuegoLógica; //Importar de otro packete una clase
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApps extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Aquí debes crear las instancias de las clases que contienen las interfaces
        JuegoLógica Juego = new JuegoLógica();
        ControladorServer Controlador = new ControladorServer();

        // Aquí puedes inicializar y mostrar las interfaces
        Juego.display();
        Controlador.display();
    }
}

