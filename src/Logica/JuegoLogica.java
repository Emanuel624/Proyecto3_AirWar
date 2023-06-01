
package Logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;

import Algoritmos.InsertionSort;
import Aviones.Aviones;
import Listas.ArrayLista;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class JuegoLogica extends Stage {
    private ObjectInputStream in;
    private final Button[][] gridButtons = new Button[GRID_SIZE_X][GRID_SIZE_Y];
    private static final int GRID_SIZE_X = 10;
    private static final int GRID_SIZE_Y = 17;

    private final Aviones Stuka = new Aviones("Stuka",30,50);
    private final Aviones P51 = new Aviones("P51",45,20);

    private final Aviones BF109 = new Aviones("BF109",60,10);

    //Clasificar si el grid es tierra(true) o agua (false).
    private final boolean[][] TierraMar = {
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
    
    private Grafo<String> grafo;
    
    public JuegoLogica(Stage stage) throws IOException {
        // Configurar los elementos de la interfaz
        setTitle("Ventana del Juego");
        StackPane layout = new StackPane();
        GridPane gridPane = createGridPane(stage);

        // Cargar la imagen de fondo
        Image imagenFondo = new Image("file:D:\\TEC\\Datos I\\Proyecto\\Proyecto3_AirWar\\MapaMundoProyecto3.png");
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
    
    
    private GridPane createGridPane(Stage stage1) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setOpacity(1); // Establecer la opacidad del GridPane en 1 para que sea visible
        Pane container = new Pane(); // Contenedor para las líneas
        container.setMouseTransparent(true); // Hacer el contenedor transparente a los eventos de ratón
        container.setOpacity(1);
        String[][] gridData = new String[GRID_SIZE_X][GRID_SIZE_Y]; // Matriz adicional para almacenar los valores

        for (int row = 0; row < GRID_SIZE_X; row++) {
            for (int col = 0; col < GRID_SIZE_Y; col++) {
                Button button = new Button();
                gridButtons[row][col] = button;
                // Configurar estilo y acción para los botones
                button.setMinSize(75, 75);
                button.setMaxSize(75, 75);
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-padding: 0;");
                //button.setUserData();
                final int finalRow = row; // Declarar una variable final para row
                final int finalCol = col; // Declarar una variable final para col

                // Establecer el evento de clic en el botón
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    try {
                        buttonOnClick(stage1, event, finalRow, finalCol);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
                boolean esUbicacion = TierraMar[row][col];

                // Generar un número aleatorio entre 0 y 1
                double random = Math.random();

                // Saber qué tipo de casilla es al generar el valor aleatorio.
                if (random < 0.03) {
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
        Grafo<String> grafo = createGraphFromGridData(gridData);
        List<Coordenada> vertices = grafo.obtenerVertices();
        for (Coordenada vertice : vertices) {
            List<Coordenada> adyacentes = grafo.obtenerAdyacentes(vertice);
            if (adyacentes != null && !adyacentes.isEmpty()) {
                Button button1 = gridButtons[vertice.getX()][vertice.getY()];
                for (Coordenada adyacente : adyacentes) {
                    Button button2 = gridButtons[adyacente.getX()][adyacente.getY()];
                    Line line = new Line();
                    line.setStroke(Color.RED);
                    line.setStrokeWidth(2);
                    line.startXProperty().bind(button1.layoutXProperty().add(button1.widthProperty().divide(2)));
                    line.startYProperty().bind(button1.layoutYProperty().add(button1.heightProperty().divide(2)));
                    line.endXProperty().bind(button2.layoutXProperty().add(button2.widthProperty().divide(2)));
                    line.endYProperty().bind(button2.layoutYProperty().add(button2.heightProperty().divide(2)));
                    line.setMouseTransparent(true); // Hacer las líneas transparentes a los eventos de ratón
                    container.getChildren().add(line); // Agregar la línea al contenedor
                }
            }
        }

        gridPane.getChildren().add(container); // Agregar el contenedor de líneas al GridPane
        return gridPane;
    }
    /*
    private boolean isAirportOrCarrier (){
        for (int row = 0; row < GRID_SIZE_X; row++) {
            for (int col = 0; col < GRID_SIZE_Y; col++) {
                if (gridButtons[row][col].getText().equals("X") || gridButtons[row][col].getText().equals("0")){
                    return true;
                }
            }
        }
        return false;
    }

     */
    private void buttonOnClick(Stage stage, MouseEvent event, int row, int col) throws Exception {
        if (event.getButton() == MouseButton.SECONDARY && (gridButtons[row][col].getText().equals("X") ||
                gridButtons[row][col].getText().equals("0"))){
            ListView<String> listViewAviones = new ListView<>();
            listViewAviones.getItems().addAll(Stuka.nombre(), P51.nombre(), BF109.nombre());

            Button btnVelocidad = new Button("Velocidad");
            Button btnFortaleza = new Button("Fortaleza");
            VBox vbox = new VBox(listViewAviones, btnVelocidad, btnFortaleza);

            btnVelocidad.setOnAction(event1 -> {
                    handleBtnVelocidad(listViewAviones);
            });
            btnFortaleza.setOnAction(event2 -> {
                //handleBtnFortaleza();
            });
            HBox hbox = new HBox(vbox);

            stage.setScene(new Scene(hbox, 300, 200));

            stage.show();
        }
    }
    private void handleBtnVelocidad (ListView<String> listViewAviones){
        ArrayLista<Aviones> array = new ArrayLista<Aviones>();
        array.add(Stuka);
        array.add(P51);
        array.add(BF109);

        int [] arr1 = {Stuka.velocidad(), P51.velocidad(), BF109.velocidad()};
        InsertionSort.insertionSort(arr1);
        //listViewAviones.getItems().setAll(arr1);
        for (int num : arr1) {
            System.out.print(num + " ");
        }

    }

    private Grafo<String> createGraphFromGridData(String[][] gridData) {
        int rows = gridData.length;
        int cols = gridData[0].length;

        grafo = new Grafo<>();

        Random random = new Random();

        // Definir coordenadas de América
        int[] americaRows = {0,0,0,0,0,0,1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,3,3,3,4,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,7,7,7,7,7,7,8,8,8,8,8,8,9,9,9,9,9,9};
        int[] americaCols = {0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5};
        
       // Definir coordenadas de Africa y Europa
        int[] africaEuropaRows = {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7,8,8,8,8,9,9,9,9};
        int[] africaEuropaCols = {6,7,8,9,6,7,8,9,6,7,8,9,6,7,8,9,6,7,8,9,6,7,8,9,6,7,8,9,6,7,8,9,6,7,8,9,6,7,8,9}; 
        
        // Clasificar nodos específicos como "Asia y oceania"
        int[] asiaOceaniaRows = {0,0,0,0,0,0,0,1,1,1,1,1,1,1,2,2,2,2,2,2,2,3,3,3,3,3,3,3,4,4,4,4,4,4,4,5,5,5,5,5,5,5,6,6,6,6,6,6,6,7,7,7,7,7,7,7,8,8,8,8,8,8,8,9,9,9,9,9,9,9}; // Filas de los nodos a clasificar como "América"
        int[] asiaOceaniaCols = {10,11,12,13,14,15,16,10,11,12,13,14,15,16,10,11,12,13,14,15,16,10,11,12,13,14,15,16,10,11,12,13,14,15,16,10,11,12,13,14,15,16,10,11,12,13,14,15,16,10,11,12,13,14,15,16,10,11,12,13,14,15,16,10,11,12,13,14,15,16}; // Columnas de los nodos a clasificar como "América"

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
                                Coordenada destino = new Coordenada(i, j);
                                double distancia = calcularDistancia(coordenada, destino, gridData);

                                String edgeType = "Interoceanica"; // Default edge type
                                // Clasificar las aristas en tipo continental
                                if (isContinentalAmerica(coordenada, destino, americaCols, americaRows)) {
                                    edgeType = "ContinentalAmerica";
                                }else if (isContinentalEuAfrica(coordenada, destino, africaEuropaCols, africaEuropaRows)) {
                                    edgeType = "ContinentalEuAfrica";
                                }else if (isContinentalAsiaOcea(coordenada, destino, asiaOceaniaCols, asiaOceaniaRows)) {
                                    edgeType = "ContinentalAsiaOcea";
                                }else{
                                    distancia += 10; // Increase the weight by 10 (adjust the value as needed)
                                }
                                                              
                                // Increase the weight if the destination node contains "0"
                                if (otherValue.equals("0")) {
                                    distancia += 10; // Increase the weight by 10 (adjust the value as needed)
                                }

                                // Generate a random number between 0 and 1
                                double randomValue = random.nextDouble();
                                if (randomValue < 0.5) {
                                    grafo.agregarArista(coordenada, destino, distancia, edgeType);
                                }
                            }
                        }
                    }
                }
            }
        }

        //Imprimer el grafo en consola
        List<Coordenada> vertices = grafo.obtenerVertices();
        for (Coordenada vertice : vertices) {
            List<Arista<String>> aristas = grafo.obtenerAristas(vertice);
            if (aristas != null && !aristas.isEmpty()) {
                System.out.print("Vertice: (" + vertice.getX() + ", " + vertice.getY() + "), Conexiones: ");
                for (Arista<String> arista : aristas) {
                    System.out.print("(" + arista.getDestino().getX() + ", " + arista.getDestino().getY() + ", Peso: " + arista.getPeso() + ", Tipo: " + arista.getTipo() + ") ");
                }
                System.out.println();
            }
        }

        return grafo;
    }

    private boolean isContinentalAmerica(Coordenada origen, Coordenada destino, int[] americaRows, int[] americaCols) {
        int origenRow = origen.getY();
        int origenCol = origen.getX();
        int destinoRow = destino.getY();
        int destinoCol = destino.getX();

        boolean origenEnAmerica = false;
        boolean destinoEnAmerica = false;

        for (int i = 0; i < americaCols.length; i++) {
            if (origenRow == americaRows[i] && origenCol == americaCols[i]) {
                origenEnAmerica = true;
            }
            if (destinoRow == americaRows[i] && destinoCol == americaCols[i]) {
                destinoEnAmerica = true;
            }

            // Si se encontró una pareja de coordenadas en América, se puede salir del bucle
            if (origenEnAmerica && destinoEnAmerica) {
                break;
            }
        }

        return (origenEnAmerica && destinoEnAmerica);
    }
   
    private boolean isContinentalEuAfrica(Coordenada origen, Coordenada destino, int[] africaEuropaRows, int[] africaEuropaCols) {
        int origenRow = origen.getY();
        int origenCol = origen.getX();
        int destinoRow = destino.getY();
        int destinoCol = destino.getX();

        boolean origenEnEuAfrica = false;
        boolean destinoEnEuAfrica = false;

        for (int i = 0; i < africaEuropaCols.length; i++) {
            if (origenRow == africaEuropaRows[i] && origenCol == africaEuropaCols[i]) {
                origenEnEuAfrica = true;
            }
                  
            if (destinoRow == africaEuropaRows[i] && destinoCol == africaEuropaCols[i]) {
                destinoEnEuAfrica = true;
            }
            
            if (origenEnEuAfrica && destinoEnEuAfrica){
                break;
            }
        }

        return (origenEnEuAfrica && destinoEnEuAfrica);
    }
    
    private boolean isContinentalAsiaOcea(Coordenada origen, Coordenada destino, int[] asiaOceaniaRows, int[] asiaOceaniaCols) {
        int origenRow = origen.getY();
        int origenCol = origen.getX();
        int destinoRow = destino.getY();
        int destinoCol = destino.getX();

        boolean origenEnAsiaOcea = false;
        boolean destinoEnAsiaOcea = false;

        for (int i = 0; i < asiaOceaniaCols.length; i++) {
            if (origenRow == asiaOceaniaRows[i] && origenCol == asiaOceaniaCols[i]) {
                origenEnAsiaOcea = true;
            }
            if (destinoRow == asiaOceaniaRows[i] && destinoCol == asiaOceaniaCols[i]) {
                destinoEnAsiaOcea = true;
            }
            
            if (origenEnAsiaOcea && destinoEnAsiaOcea){
                break;
            }
        }

        return (origenEnAsiaOcea && destinoEnAsiaOcea);
    }

        
    private double calcularDistancia(Coordenada origen, Coordenada destino, String[][] gridData) {
        int x1 = origen.getY();
        int y1 = origen.getX();
        int x2 = destino.getY();
        int y2 = destino.getX();

        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        // Calcula la distancia utilizando la fórmula de distancia euclidiana
        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Aumenta el peso si el destino tiene valor "0"
        if (gridData[destino.getX()][destino.getY()].equals("0")) {
            distancia += 0; // Aumenta el peso en 10
        }

        // Redondea la distancia al entero más cercano

        return (double) Math.round(distancia);
    }

    

    
    private void iniciarSocket() {
        try {
            // Crear el socket para conectarse al servidor
            Socket socket = new Socket("localhost", 8070);

            // Crear el stream de salida para enviar la información al servidor
            // Objetos necesarios para el funcionamiento de los sockets
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Enviar el grafo al servidor
            out.writeObject(grafo);
            out.flush();

            // Cerrar la conexión y el stream de salida


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void display() {
        // Mostrar la interfaz
        show();
    }
}


