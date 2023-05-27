package Lógica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
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

public class ControladorServer extends Stage {
    private static Socket socket;
    
    public ControladorServer() throws IOException {
        Button button = new Button("Interfaz 2");
        Label label = new Label("Controlador");

        // Crear un contenedor para todos los nodos
        VBox vbox = new VBox(button, label);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 300, 200);
        setScene(scene);
        setTitle("Ventana del Controlador"); // Establecer el título de la ventana
        
        
        ////////////////////////////////////////////////////////////////////////
        // Crear el socket del servidor en el puerto 8080
        ServerSocket serverSocket = new ServerSocket(8080);
        
        // Iniciar un nuevo hilo para aceptar conexiones de clientes como serivor local
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
  
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        acceptThread.start();
    }
    


    public void display() {
        // Mostrar la interfaz desde el hilo de la interfaz de usuario
        Platform.runLater(() -> show());
    }
}
