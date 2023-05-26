/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class InterfacesGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("");

        // Crear la primera ventana en el hilo de la aplicación de JavaFX
        Button btnVentana2 = new Button("Inicio del juego");
        btnVentana2.setOnAction(e -> {
            //Cambio de formato de ventana del juego principal
            JuegoMapa(primaryStage);
            
            // Crear la segunda ventana en el hilo de la aplicación de JavaFX
            Stage ventana2 = new Stage();
            ventana2.setTitle("Controlador");

            Button btnCerrarVentana2 = new Button("Cerrar");
            btnCerrarVentana2.setOnAction(event -> ventana2.close());

            StackPane layout2 = new StackPane();
            layout2.getChildren().add(btnCerrarVentana2);

            Scene escena2 = new Scene(layout2, 300, 200);

            Platform.runLater(() -> {
                ventana2.setScene(escena2);
                ventana2.show();
            });
        });

        StackPane layout1 = new StackPane();
        layout1.getChildren().add(btnVentana2);

        Scene escena1 = new Scene(layout1, 300, 200);
        primaryStage.setScene(escena1);
        primaryStage.show();
    }
    
    
    public void JuegoMapa(Stage ventana2) {
        // Crear la segunda ventana en el hilo de la aplicación de JavaFX
        Stage ventanaNueva = new Stage();
        ventanaNueva.setTitle("Juego");

        Button btnCerrarVentana2 = new Button("Se cambió!!!!!!");
        btnCerrarVentana2.setOnAction(event -> ventana2.close());

        StackPane layout2 = new StackPane();
        layout2.getChildren().add(btnCerrarVentana2);

        // Cargar la imagen de fondo
        Image imagenFondo = new Image("file:///C:/Users/Extreme PC/Desktop/Proyecto3_AirWar/MapaMundoProyecto3.png");
        BackgroundSize backgroundSize = new BackgroundSize(500, 500, false, true, true, true);
        BackgroundImage fondo = new BackgroundImage(imagenFondo, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        layout2.setBackground(new Background(fondo));

        Scene escena2 = new Scene(layout2, 1500, 900);

        Platform.runLater(() -> {
            ventana2.setScene(escena2);
            ventana2.show();
        });
    }
}