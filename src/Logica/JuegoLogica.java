
package Logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

import Algoritmos.BinarySearch;
import Algoritmos.InsertionSort;
import Algoritmos.ShellSort;
import Aviones.Aviones;
import Listas.ArrayLista;
import Listas.ListaEnlazada;

import Listas.ListaEnlazadaView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import static java.lang.System.out;

/**
 * Clase pública relacionada la lógica del juego AirWar como tal.
 */
public class JuegoLogica extends Stage {
    private ObjectInputStream in;

    private ObjectOutputStream out1;
    private final Button[][] gridButtons = new Button[GRID_SIZE_X][GRID_SIZE_Y];

    private final String[][] gridData = new String[GRID_SIZE_X][GRID_SIZE_Y]; // Matriz adicional para almacenar los valores
    private static final int GRID_SIZE_X = 10;
    private static final int GRID_SIZE_Y = 17;

    //private final Aviones Stuka = new Aviones("Stuka",30,4,50);
    private final Aviones P51 = new Aviones("P51",45,1,40);
    private final Aviones BF109 = new Aviones("BF109",60,3,10);
    private final Aviones JU88 = new Aviones("JU88",40,5,60);
    private final Aviones Spitfire = new Aviones("Spitfire", 70,6,20);
    private final Aviones Hurricane = new Aviones("Hurricane",75,7,15);

    private final Aviones YAK9 = new Aviones("YAK9",41,2,35);

    private final  StackPane layout = new StackPane();

    private final Scene sceneMapa = new Scene(layout, 1550, 800);

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

    /**
     * Método privado que permite iniciar la comunicación de Sockets.
     */
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
    
    /**
     * Inicializador de la clase JuegoLógica.
     * @param stage parámetro relacionado la interfaz gráfica como tal.
     * @throws IOException exepción en caso de error con la comunicación serial.
     */
    public JuegoLogica(Stage stage) throws IOException {
        // Configurar los elementos de la interfaz
        setTitle("Ventana del Juego");

        GridPane gridPane = createGridPane(stage);

        // Cargar la imagen de fondo
        Image imagenFondo = new Image("file:///C:/Users/Extreme PC/Desktop/Proyecto3_AirWar/MapaMundoProyecto3.png");
        BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, false, true, true, true);
        BackgroundImage fondo = new BackgroundImage(imagenFondo, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        layout.setBackground(new Background(fondo));

        layout.getChildren().addAll(gridPane);


        
        // Crear una escena y asignarla al escenario
        setScene(sceneMapa);

        // Iniciar el socket en un nuevo hilo
        Thread socketThread = new Thread(this::iniciarSocket);
        socketThread.start();


    }
    
    /**
     * GridPane privado que se encarga de la creación de la matriz y del GUI.
     * @param stage1 escenario donde se crea este GridPane.
     * @return retorna el gridPane
     */
    private GridPane createGridPane(Stage stage1) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setOpacity(1); // Establecer la opacidad del GridPane en 1 para que sea visible
        Pane container = new Pane(); // Contenedor para las líneas
        container.setMouseTransparent(true); // Hacer el contenedor transparente a los eventos de ratón
        container.setOpacity(1);


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
        out.println("Matriz de datos:");
        for (String[] row : gridData) {
            for (String value : row) {
                out.print(value + " ");
            }
            out.println();
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

    /**
     * metodo que guarda los aviones previamente definidos.
     * @return esa lista con los aviones.
     */
    private ListaEnlazada<Aviones> ListaAviones(){
        ListaEnlazada<Aviones> listaEnlazadaAviones = new ListaEnlazada<>();
        //listaEnlazadaAviones.add();
        listaEnlazadaAviones.add(P51);
        listaEnlazadaAviones.add(BF109);
        listaEnlazadaAviones.add(JU88);
        listaEnlazadaAviones.add(Spitfire);
        listaEnlazadaAviones.add(Hurricane);
        listaEnlazadaAviones.add(YAK9);

        return listaEnlazadaAviones;
    }

    /**
     * Funcion que se encarga de manejar la logica detras del ordenamiento.
     * @param stageListaConAviones la ventana.
     * @param event presionar un boton.
     * @param row fila de la matriz de botones.
     * @param col columna de la matriz de botones.
     * @throws Exception exception excepción en caso de problemas con los sockets.
     */
    private void buttonOnClick(Stage stageListaConAviones, MouseEvent event, int row, int col) throws Exception {
        if (event.getButton() == MouseButton.SECONDARY && (gridButtons[row][col].getText().equals("X") ||
                gridButtons[row][col].getText().equals("0"))){
            //crear la listview y setear tamaño
            ListaEnlazadaView<Aviones> listViewAviones = new ListaEnlazadaView<>();
            ListaEnlazada<Aviones> listaEnlazadaAviones1 = ListaAviones();
            listaEnlazadaAviones1.forEach(listViewAviones::add);

            listViewAviones.getListView().setMaxWidth(200);
            listViewAviones.getListView().setMaxHeight(200);
            //crear labels donde se almacenan los atributos de los aviones
            Label lblNombre = new Label("Nombre: ");
            Label lblVelocidad = new Label("Velocidad: ");
            Label lblEficiencia = new Label("Eficiencia: ");
            Label lblFortaleza = new Label("Fortaleza: ");


            listViewAviones.getListView().getSelectionModel().selectedItemProperty().
                    addListener((observable, oldValue, newValue) -> {
                        if (newValue != null){
                            lblNombre.setText("Nombre: " + newValue.getNombre());
                            lblVelocidad.setText("Velocidad: " + newValue.getVelocidad());
                            lblEficiencia.setText("Eficiencia: " + newValue.getEficiencia());
                            lblFortaleza.setText("Fortaleza: " + newValue.getFortaleza());
                        }
                    });
            VBox vboxLbls = new VBox(listViewAviones.getListView(), lblNombre, lblVelocidad,lblEficiencia,lblFortaleza);
            vboxLbls.setSpacing(10);
            vboxLbls.setPadding(new Insets(30));

            //crear botones para el ordenamiento
            Label lbl = new Label("Ordenar por:");
            Button btnVelocidad = new Button("Velocidad");
            Button btnEficiencia = new Button("Eficiencia");
            Label lbl2 = new Label("Busca por nombre");
            lbl2.setAlignment(Pos.CENTER);
            TextField txtNombre = new TextField();
            txtNombre.setMaxWidth(100);
            txtNombre.setAlignment(Pos.CENTER);
            Button btnBuscar = new Button("Buscar");
            btnBuscar.setAlignment(Pos.CENTER);
            Button btnCrear = new Button("Crear");
            btnCrear.setAlignment(Pos.CENTER);
            VBox vboxBtns = new VBox(lbl, btnVelocidad, btnEficiencia, lbl2, txtNombre, btnBuscar, btnCrear);
            vboxBtns.setAlignment(Pos.TOP_CENTER);
            vboxBtns.setSpacing(10);
            vboxBtns.setPadding(new Insets(30));

            HBox hbox = new HBox(vboxLbls, vboxBtns);

            final int finalRow = row;
            final int finalCol = col;
            btnVelocidad.setOnAction(event1 -> {
                    handleBtnVelocidad(listViewAviones.getListView());
            });
            btnEficiencia.setOnAction(event2 -> {
                handleBtnEficiencia(listViewAviones.getListView());
            });
            btnBuscar.setOnAction(event3 ->{
                handleBtnNombre(txtNombre, listViewAviones.getListView());
            });
            btnCrear.setOnAction(event4 -> {
                handleBtnCrear(listViewAviones, finalRow, finalCol);
            });

            stageListaConAviones.setScene(new Scene(hbox, 450, 420));

            stageListaConAviones.show();
        }}

    /**
     * Metodo que permite organizar los aviones en la listView por su velocidad.
     * @param listViewAviones la listView de aviones.
     */
    private void handleBtnVelocidad (ListView<Aviones> listViewAviones){
       /*
        try {
            out1.writeObject("velocidad");
        }catch (IOException e){
            e.printStackTrace();
        }

        */
        ListaEnlazada<Aviones> listaAviones = ListaAviones();
        ArrayLista<Integer> arrayVelocidades = new ArrayLista<>();

        for (Aviones aviones : listaAviones) {
            arrayVelocidades.add(aviones.getVelocidad());
        }
        //crear un int [] para almacenar las velocidades
        int[] arrayVelocidades2 = new int[arrayVelocidades.size()];
        for (int i = 0; i < arrayVelocidades.size(); i++) {
            arrayVelocidades2[i] = arrayVelocidades.get(i);
        }
        //ordenar las velocidades del int[] con insertion sort
        InsertionSort.insertionSort(arrayVelocidades2);

        //
        ObservableList<Aviones> avionesOrdenadosVelocidad = FXCollections.observableArrayList();
        for (int velocidad : arrayVelocidades2) {
            for (Aviones avion : listaAviones) {
                if (avion.getVelocidad() == velocidad) {
                    avionesOrdenadosVelocidad.add(avion);
                    break;
                }
            }
            listViewAviones.getItems().clear();
            for (Aviones avion :avionesOrdenadosVelocidad) {
                listViewAviones.getItems().add(avion);
            }
        }
        //out.writeObject(avionesOrdenadosVelocidad);
    }

    /**
     * Metodo que se encarga de ordenar la listView por la eficiencia.
     * @param listViewAviones la listView de aviones.
     */
    private void handleBtnEficiencia (ListView<Aviones> listViewAviones){
        /*try {
            out1.writeObject("fortaleza");
        }catch (IOException e){
            e.printStackTrace();
        }

         */
        ListaEnlazada<Aviones> listaAviones = ListaAviones();
        ArrayLista<Integer> arrayEficiencias = new ArrayLista<>();

        for (Aviones aviones : listaAviones) {
            arrayEficiencias.add(aviones.getEficiencia());
        }
        //crear un int [] para almacenar las velocidades
        int[] arrayEficiencias2 = new int[arrayEficiencias.size()];
        for (int i = 0; i < arrayEficiencias.size(); i++) {
            arrayEficiencias2[i] = arrayEficiencias.get(i);
        }
        //ordenar las fortalezas del int[] con shell sort
        ShellSort.shellSort(arrayEficiencias2);
        //
        ObservableList<Aviones> avionesOrdenadosEficiencia = FXCollections.observableArrayList();
        for (int eficiencia : arrayEficiencias2) {
            for (Aviones avion : listaAviones) {
                if (avion.getEficiencia() == eficiencia) {
                    avionesOrdenadosEficiencia.add(avion);
                    break;
                }
            }
            listViewAviones.getItems().clear();
            for (Aviones avion :avionesOrdenadosEficiencia) {
                listViewAviones.getItems().add(avion);
            }
        }
    }
    
    /**
     * Método que permite buscar por nombre de avion.
     * @param txtNombre el campo de texto de la ventana.
     * @param listViewAviones la listView de aviones.
     */
    private void handleBtnNombre (TextField txtNombre, ListView<Aviones> listViewAviones){
        ListaEnlazada<Aviones> listaAviones = ListaAviones();
        //ArrayListaString<String> arrayNombres = new ArrayListaString<>();
        //ArrayListaString<String> arrayNombres2 = new ArrayListaString<>();

        //iterar sobre la lista de aviones y añadir cada avión a un array
        ArrayList<String> arrayNombres = new ArrayList<>();
        String nombreBuscado = txtNombre.getText();
        for (Aviones aviones : listaAviones) {
            //arrayNombres.addString(aviones.getNombre());
            arrayNombres.add(aviones.getNombre());
        }
        /*
        for (Aviones aviones : listaAviones){
            //arrayNombres2.addString(aviones.getNombre());
            arrayNombres2.add(aviones.getNombre());
        }

         */
        for (int i = 0; i < arrayNombres.size(); i++) {
            arrayNombres.set(i, arrayNombres.get(i));
        }
        //String[] arrayNombres2Array = new String[arrayNombres2.size()];
        //arrayNombres2Array = (String[]) arrayNombres2.toArray();
        //InsertionSortArray.sort_sub(arrayNombres2Array, arrayNombres2Array.length);

        //ordenar el array ya que binary search solo funciona así
        Collections.sort(arrayNombres);
        ObservableList<Aviones> avionesEncontrados= FXCollections.observableArrayList();
        int indice = BinarySearch.binarySearch(arrayNombres, nombreBuscado);
        if (indice >= 0){
            String nombreEncontrado = arrayNombres.get(indice);
            for (Aviones avion : listaAviones) {
                if (Objects.equals(avion.getNombre(), nombreEncontrado)) {
                    avionesEncontrados.add(avion);
                    break;
                }
            }
            //desplegar en la listView
        listViewAviones.getItems().clear();;
        txtNombre.clear();
        for (Aviones avion :avionesEncontrados) {
            listViewAviones.getItems().add(avion);
            }
        }
    }
    
    /**
     * Método que permite crear aviones.
     * @param listViewAviones listView de los aviones.
     * @param row fila donde se esperan crear crear los aviones
     * @param col columna donde se espearan crear los aviones.
     */
    private void handleBtnCrear (ListaEnlazadaView<Aviones> listViewAviones, int row, int col){
        double localizacionX =  gridButtons[row][col].getLayoutX(); //coordenadas del boton que hice clic derecho
        double localizacionY = gridButtons[row][col].getLayoutY();
        Aviones seleccionado = listViewAviones.getListView().getSelectionModel().getSelectedItem(); //si un avion esta seleccionado
        if (seleccionado != null){
            Circle avion = new Circle(10); //circulo con radio 10
            StackPane circulosAviones = (StackPane) sceneMapa.getRoot(); //stackpane que se crea para mandar el círculo a sceneMapa
            circulosAviones.getChildren().addAll(avion); //manda el circulo a la pantalla

            avion.setFill(Color.DARKRED);
            avion.setOpacity(1);

            avion.setLayoutX(localizacionX); //no sirve bajo esta logica.
            avion.setLayoutY(localizacionY); //Deberia de poner el circulo en [row][col] donde hice clic derecho

            //avion.setLayoutX(localizacionX); //si se pone en vez de translate, el circulo se crea en el medio siempre
            //avion.setLayoutY(localizacionY);
            //StackPane.setAlignment(avion, Pos.CENTER);
            //StackPane.setMargin(avion, new Insets(localizacionX, 0, 0, localizacionY)); //igual acá, se crea en el centro
            //layout.getChildren().add(avion);


        }
        out.println(localizacionX);
        out.println(localizacionY);
    }

//OTRA MANERA DE IMPLEMENTARLO TOMANDO EN CUENTA LAS COORDENADAS DE CADA VERTICE (botones). No funciona tampoco

    /*
    private void handleBtnCrear (ListaEnlazadaView<Aviones> listViewAviones, int row, int col){
        List<Coordenada> vertices = grafo.obtenerVertices();
        for (Coordenada vertice : vertices) {
            double x = vertice.getX();
            double y = vertice.getY();
            Aviones seleccionado = listViewAviones.getListView().getSelectionModel().getSelectedItem();
            if (seleccionado != null){
                Circle avion = new Circle(10);
                StackPane circulosAviones = (StackPane) sceneMapa.getRoot();
                circulosAviones.getChildren().addAll(avion);

                avion.setFill(Color.DARKRED);
                avion.setOpacity(1);

                avion.setTranslateX(x); //no sirve bajo esta logica
                avion.setTranslateY(y);


                //StackPane.setAlignment(avion, Pos.CENTER);
                //StackPane.setMargin(avion, new Insets(localizacionX, 0, 0, localizacionY));
                //layout.getChildren().add(avion);
            }
            out.println(x);
            out.println(y);
        }
    }

 */
    
    /**
     * Grafo privado que crea el grafo en base de la matriz de botones.
     * @param gridData el gridData creado.
     * @return el grafo como tal.
     */
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
                out.print("Vertice: (" + vertice.getX() + ", " + vertice.getY() + "), Conexiones: ");
                for (Arista<String> arista : aristas) {
                    out.print("(" + arista.getDestino().getX() + ", " + arista.getDestino().getY() + ", Peso: " + arista.getPeso() + ", Tipo: " + arista.getTipo() + ") ");
                }
                out.println();
            }
        }

        return grafo;
    }

    
    /**
     * Permite dicernir en que lugar se genera el aeropuerto / portaaviones.
     * @param origen Coordenada de origen.
     * @param destino Coordenada de destino.
     * @param americaRows Coordena que define america en fila.
     * @param americaCols Coordenad que define america en columna.
     * @return si pertence o no al parametro.
     */
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
    
    /**
     * Permite dicernir en que lugar se genera el aeropuerto / portaaviones.
     * @param origen Coordenada de origen.
     * @param destino Coordenada de destino.
     * @param africaEuropaRows Coordena que define AfricaEuropa en fila.
     * @param africaEuropaCols Coordenad que define AfricaEuropa en columna.
     * @return si pertence o no al parametro.
     */
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
    
    /**
     * Permite dicernir en que lugar se genera el aeropuerto / portaaviones.
     * @param origen coordeana de origen del vertice.
     * @param destino coordeana de destino del vertice.
     * @param asiaOceaniaRows Coordena que define asiaOceania en fila. 
     * @param asiaOceaniaCols Coordenad que define asiaOceania en columna.
     * @return retorna el tipo de conexión como tal.
     */
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

    /**
     * double privado que permite calcularDistancia entre vertices.
     * @param origen Coordenada de origen.
     * @param destino Coordenada de desitino.
     * @param gridData el dato de la matriz de la adyacencia.
     * @return retorna la distancia como tal.
     */    
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
    
    /**
     * Método que permite desplegar la GUI como tal.
     */
    public void display() {
        // Mostrar la interfaz
        show();
    }
}


