/**
 * Clase principal que inicia la aplicación de la calculadora
 */
public class CalculadoraApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            CalculadoraCientifica calculadora = new CalculadoraCientifica();
            calculadora.setVisible(true);
        });
    }
}