
package Lógica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;
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

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;


public class JuegoLógica extends Stage {
    // Objetos necesarios para el funcionamiento de los sockets
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    private static final int GRID_SIZE_X = 10;
    private static final int GRID_SIZE_Y = 17;
    private Button[][] gridButtons;
    
    
    //Clasificar si el grid es tierra(true) o agua (false).
    private boolean[][] TierraMar = {
    {false, false, false, true, true, true, true, false, false, true, true, true, true, false, false, false, false},
    {true, true, true, false, true, false, false, false, true, true, true, true, true, true, true, false, false},
    {false, true, true, true, false, false, false, true, true, true, true, true, true, true, true, false, false},
    {false, true, true, false, false, false, false, true, true, true, true, true, true, true, false, false, false},
    {false, false, true, false, false, false, true, true, true, true, false, true, true, false, false, false, false},
    {false, false, false, true, true, false, false, false, true, true, false, false, true, true, false, false, false},
    {false, false, false, true, true, false, false, false, true, false, false, false, false, false, true, false, false},
    {false, false, false, true, true, false, false, false, true, false, false, false, false, true, true, false, false},
    {false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false},
    {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
    };
    
    
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
        String[][] gridData = new String[GRID_SIZE_X][GRID_SIZE_Y]; // Matriz adicional para almacenar los valores

        for (int row = 0; row < GRID_SIZE_X; row++) {
            for (int col = 0; col < GRID_SIZE_Y; col++) {
                Button button = new Button();
                gridButtons[row][col] = button;

                // Configurar estilo y acción para los botones
                button.setMinSize(75, 75);
                button.setMaxSize(75, 75);
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-padding: 0;");

                boolean esUbicacion = TierraMar[row][col];

                // Generar un número aleatorio entre 0 y 1
                double random = Math.random();

                final int finalRow = row; // Declarar una variable final para row
                final int finalCol = col; // Declarar una variable final para col
                button.setOnAction(e -> buttonClickedLeft(finalRow, finalCol)); // Usar las variables finales en la expresión lambda

                // Saber qué tipo de casilla es al generar el valor aleatorio.
                if (random < 0.05) {
                    if (esUbicacion) {
                        button.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-padding: 0;");
                        button.setText("X"); // Aeropuertos
                        gridData[row][col] = "X"; // Asignar "X" a la matriz de datos
                    } else {
                        button.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-padding: 0;");
                        button.setText("0"); // Porta-Aviones
                        gridData[row][col] = "0"; // Asignar "0" a la matriz de datos
                    }
                } else {
                    gridData[row][col] = "*"; // Dejar vacío en la matriz de datos si no se generó un valor especial
                }

                // Establecer la opacidad del botón en 1 para que sea visible
                button.setOpacity(1);

                // Agregar el botón al GridPane
                gridPane.add(button, col, row);
            }
        }
        // Imprimir la matriz de datos
        System.out.println("Matriz de datos:");
        for (String[] row : gridData) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        createGraphFromGridData(gridData);
        return gridPane;
    }
    
    
    private Grafo<String> createGraphFromGridData(String[][] gridData) {
        int rows = gridData.length;
        int cols = gridData[0].length;

        Grafo<String> grafo = new Grafo<>();

        Random random = new Random();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String value = gridData[row][col];

                if (value.equals("X") || value.equals("0")) {
                    Coordenada coordenada = new Coordenada(row, col);
                    grafo.agregarVertice(coordenada);

                    // Generar aristas hacia algunos nodos que tengan "X" o "0"
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            String otherValue = gridData[i][j];
                            if ((otherValue.equals("X") || otherValue.equals("0")) && (i != row || j != col)) {
                                // Generar un número aleatorio entre 0 y 1
                                double randomValue = random.nextDouble();
                                if (randomValue < 0.5) {
                                    Coordenada destino = new Coordenada(i, j);
                                    grafo.agregarArista(coordenada, destino);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Imprimir el grafo
        List<Coordenada> vertices = grafo.obtenerVertices();
        for (Coordenada vertice : vertices) {
            List<Coordenada> adyacentes = grafo.obtenerAdyacentes(vertice);
            if (adyacentes != null && !adyacentes.isEmpty()) {
                System.out.println("Vertice: " + vertice + ", Adyacentes: " + adyacentes);
            }
        }

        return grafo;
    }


    
    private void buttonClickedLeft(int row, int col) {
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


