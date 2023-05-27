
package Lógica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class JuegoLógica extends Stage {
    // Objetos necesarios para el funcionamiento de los sockets
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    private static final int GRID_SIZE_X = 10;
    private static final int GRID_SIZE_Y = 17;
    private Button[][] gridButtons;

    public JuegoLógica() throws IOException {
        // Configurar los elementos de la interfaz
        setTitle("Ventana del Juego");
        StackPane layout = new StackPane();
        
        GridPane gridPane = createGridPane();
        
        
        // Cargar la imagen de fondo
        Image imagenFondo = new Image("file:///C:/Users/Extreme PC/Desktop/Proyecto3_AirWar/MapaMundoProyecto3.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, false, true, true, true);
        BackgroundImage fondo = new BackgroundImage(imagenFondo, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        layout.setBackground(new Background(fondo));
        layout.getChildren().addAll(gridPane);
        
        
        // Crear una escena y asignarla al escenario
        Scene scene = new Scene(layout, 1550, 800);
        setScene(scene);

        // Iniciar el socket en un nuevo hilo
        Thread socketThread = new Thread(this::iniciarSocket);
        socketThread.start();
    }
    
    
    //Crear la matriz del GUI para crear los diferentes datos.
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setOpacity(1); // Establecer la opacidad del GridPane en 1 para que sea visible

        gridButtons = new Button[GRID_SIZE_X][GRID_SIZE_Y];

        for (int row = 0; row < GRID_SIZE_X; row++) {
            for (int col = 0; col < GRID_SIZE_Y; col++) {
                Button button = new Button();
                gridButtons[row][col] = button;

                // Configurar estilo y acción para los botones
                button.setMinSize(75, 75);
                button.setMaxSize(75, 75);
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-padding: 0;");

                final int finalRow = row; // Declarar una variable final para row
                final int finalCol = col; // Declarar una variable final para col
                button.setOnAction(e -> buttonClicked(finalRow, finalCol)); // Usar las variables finales en la expresión lambda

                // Establecer la opacidad del botón en 1 para que sea visible
                button.setOpacity(1);

                // Generar un número aleatorio entre 0 y 1
                double random = Math.random();

                // Asignar la letra "X" al botón con una probabilidad del 5%
                if (random < 0.05) {
                    button.setText("X");
                }

                // Agregar el botón al GridPane
                gridPane.add(button, col, row);
            }
        }
        return gridPane;
    }


    private void buttonClicked(int row, int col) {
        System.out.println("Button clicked: " + row + ", " + col);
    }
    
    
    private void iniciarSocket() {
        try {
            // Crear el socket para conectarse al servidor
            Socket socket = new Socket("localhost", 8080);

            // Crear el stream de salida para enviar la información al servidor
            out = new ObjectOutputStream(socket.getOutputStream());


            // Cerrar la conexión y el stream de salida
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void display() {
        // Mostrar la interfaz
        show();
    }
}


