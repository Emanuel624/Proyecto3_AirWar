import com.fazecast.jSerialComm.*;
import java.util.Scanner;

/**
 * Clase utilizada para generar la interfaz serial entre computador y la plataforma arduino UNO.
 */
public class ArduinoInterface {
    private static SerialPort chosenPort;
    
    /**
     * Método estático main el cual permite la ejecución de la clase como tal.
     * @param args parametro necesario para la ejecución del main
     */
    public static void main(String[] args) {
        SerialPort[] portNames = SerialPort.getCommPorts();
        for (int i = 0; i < portNames.length; i++)
            if (portNames[i].getSystemPortName().equals("COM3")) {
                chosenPort = portNames[i];
                break;
            }
        
        if (chosenPort != null) {
            chosenPort.openPort();
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
            Thread thread = new Thread(() -> {
                Scanner scanner = new Scanner(chosenPort.getInputStream());
                while (scanner.hasNextLine()) {
                    try {
                        String line = scanner.nextLine();
                        if (line.equals("Acertaste")) {
                            printSuccess();
                        } else if (line.equals("Fallaste")) {
                            printFailure();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                scanner.close();
            });
            thread.start();
        } else {
            System.out.println("No se encontró el puerto COM5.");
        }
    }
    
    /**
     * Método estático para saber si se acerto a la LED central
     */
    public static void printSuccess() {
        System.out.println("Acertaste");
    }
    
    /**
     * Método estático para saber si falló a la LED central
     */
    public static void printFailure() {
        System.out.println("Fallaste");
    }
}
