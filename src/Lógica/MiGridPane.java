
package Lógica;
import java.io.Serializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class MiGridPane extends GridPane implements Serializable {
    private static final int GRID_SIZE_X = 10;
    private static final int GRID_SIZE_Y = 10;

    private Button[][] gridButtons;

    public MiGridPane() {
        setPadding(new Insets(15));
        setHgap(15);
        setVgap(5);
        setOpacity(1); // Establecer la opacidad del GridPane en 1 para que sea visible

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
                add(button, col, row);
            }
        }
    }

    private void buttonClicked(int row, int col) {
        System.out.println("Button clicked: " + row + ", " + col);
    }
}
