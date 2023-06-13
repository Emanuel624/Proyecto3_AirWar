package Logica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * Clase relacionado al Controlador junto con el servidor del programar.
 */
public class ControladorServer extends Stage {

    private static Socket socket;
    private TextArea textArea;
    
    /**
     * Inicializador de la clase ControladorSever.
     * @throws IOException en caso de error con la conexión serial del servidor.
     */
    public ControladorServer() throws IOException {
        Button button = new Button("¿Hago algo?");
        Label label = new Label("¡Bienvenidos al Controlador!");

        textArea = new TextArea();
        textArea.setEditable(false);
        
        // Crear un contenedor para todos los nodos
        VBox vbox = new VBox(button, label, textArea);
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

                            // Recibir el grafo del cliente
                            Grafo grafoRecibido = (Grafo) in.readObject();
                            System.out.println(grafoRecibido);

                            // Actualizar el TextArea en el hilo de la interfaz de usuario
                            Platform.runLater(() -> {
                                // Obtener la información específica del grafo y agregarla al TextArea
                                String informacionGrafo = obtenerInformacionGrafo(grafoRecibido);
                                textArea.appendText(informacionGrafo + "\n");
                            });
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
    

        /**
         * Método para obtener la información recibida por sockets y que sea leida y mostrada de una forma facil de interpretar.
         * @param grafo recibe el grafo enviado por sockets
         * @return la información como tal en un nuevo formato.
         */
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


    /**
     * Método que permite ejectuar esta interfaz de JavaFX.
     */    
    public void display() {
        // Mostrar la interfaz desde el hilo de la interfaz de usuario
        Platform.runLater(this::show);
    }
}
