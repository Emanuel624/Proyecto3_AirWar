package Logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Algoritmos.InsertionSort;
import Algoritmos.ShellSort;
import Aviones.Aviones;
import Listas.ArrayLista;
import Listas.ListaEnlazada;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ControladorServer extends Stage {

    private static Socket socket;

    private final Aviones Stuka = new Aviones("Stuka",30,50);
    private final Aviones P51 = new Aviones("P51",45,20);
    private final Aviones BF109 = new Aviones("BF109",60,10);
    private final Aviones JU88 = new Aviones("JU88",40,60);
    private final Aviones Spitfire = new Aviones("Spitfire", 70,20);
    private final Aviones Hurricane = new Aviones("Hurricane",75,15);

    private final Aviones YAK9 = new Aviones("YAK9",40,35);

    /**
     * metodo que se encarga de insertar los aviones en una lista y devolverla
     * @return la lista con los aviones
     */
    private ListaEnlazada<Aviones> ListaAviones(){
        ListaEnlazada<Aviones> listaEnlazadaAviones = new ListaEnlazada<>();
        listaEnlazadaAviones.add(Stuka);
        listaEnlazadaAviones.add(P51);
        listaEnlazadaAviones.add(BF109);
        listaEnlazadaAviones.add(JU88);
        listaEnlazadaAviones.add(Spitfire);
        listaEnlazadaAviones.add(Hurricane);
        listaEnlazadaAviones.add(YAK9);

        return listaEnlazadaAviones;
    }

    public ControladorServer() throws IOException {
        Button button = new Button("¿Hago algo?");
        Label label = new Label("¡Bienvenidos al Controlador!");

        
        // Crear un contenedor para todos los nodos
        VBox vbox = new VBox(button, label);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 300, 800);
        setScene(scene);
        setTitle("Ventana del Controlador"); // Establecer el título de la ventana
        
        
        ////////////////////////////////////////////////////////////////////////
        // Crear el socket del servidor en el puerto 8080
        try {
            ServerSocket serverSocket = new ServerSocket(8070);
            
            // Iniciar un nuevo hilo para aceptar conexiones de clientes como servidor local
            Thread acceptThread = new Thread(() -> {
                try {
                    while (true) {
                        // Esperar por una conexión del cliente
                        socket = serverSocket.accept();
                        System.out.println("Cliente conectado");

                        // Crear el stream de entrada para recibir la información del Administrador
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        // Crear el stream de salida para enviar la respuesta al cliente
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        while (true){
                            Object obj = in.readObject();
                            if (obj instanceof Grafo grafoRecibido){
                                System.out.println(grafoRecibido);
                                // Actualizar el TextArea en el hilo de la interfaz de usuario

                            } else if (obj instanceof String && obj.equals("velocidad")) {
                                ListaEnlazada<Aviones> listaAviones = ListaAviones();
                                ArrayLista<Integer> arrayVelocidades = new ArrayLista<>();

                                for (Aviones aviones : listaAviones) {
                                    arrayVelocidades.add(aviones.velocidad());
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
                                        if (avion.velocidad() == velocidad) {
                                            avionesOrdenadosVelocidad.add(avion);
                                            break;
                                        }
                                    }
                                }
                                 out.writeObject(avionesOrdenadosVelocidad);
                            } else if (obj instanceof String && obj.equals("fortaleza")) {
                                ListaEnlazada<Aviones> listaAviones = ListaAviones();
                                ArrayLista<Integer> arrayFortalezas = new ArrayLista<>();

                                for (Aviones aviones : listaAviones) {
                                    arrayFortalezas.add(aviones.fortaleza());
                                }
                                //crear un int [] para almacenar las velocidades
                                int[] arrayFortalezas2 = new int[arrayFortalezas.size()];
                                for (int i = 0; i < arrayFortalezas.size(); i++) {
                                    arrayFortalezas2[i] = arrayFortalezas.get(i);
                                }
                                //ordenar las fortalezas del int[] con shell sort
                                ShellSort.shellSort(arrayFortalezas2);
                                //
                                ObservableList<Aviones> avionesOrdenadosFortaleza = FXCollections.observableArrayList();
                                for (int fortaleza : arrayFortalezas2) {
                                    for (Aviones avion : listaAviones) {
                                        if (avion.fortaleza() == fortaleza) {
                                            avionesOrdenadosFortaleza.add(avion);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            acceptThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Método para obtener la información específica del grafo
    private String obtenerInformacionGrafo(Grafo grafo) {
        StringBuilder sb = new StringBuilder();

        // Obtener la lista de vértices del grafo
        List<Coordenada> vertices = grafo.obtenerVertices();

        // Recorrer cada vértice y obtener la información de las aristas
        for (Coordenada vertice : vertices) {
            // Obtener la lista de aristas del vértice
            List<Arista<Object>> aristas = grafo.obtenerAristas(vertice);

            sb.append("Vertice: ").append(vertice).append("\n");

            // Recorrer las aristas y obtener sus detalles
            for (Arista<Object> arista : aristas) {
                Coordenada origen = arista.getOrigen();
                Coordenada destino = arista.getDestino();
                double peso = arista.getPeso();
                String tipo = arista.getTipo();

                sb.append("  - Arista: ").append(origen).append(" -> ").append(destino)
                        .append(", Peso: ").append(peso).append(", Tipo: ").append(tipo).append("\n");
            }
        }
        return sb.toString();
    }


    public void display() {
        // Mostrar la interfaz desde el hilo de la interfaz de usuario
        Platform.runLater(this::show);
    }
}
