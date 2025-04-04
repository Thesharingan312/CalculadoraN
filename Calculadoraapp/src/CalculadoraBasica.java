/**
 * Clase que implementa las operaciones básicas de una calculadora
 */
class CalculadoraBasica extends Calculadora {
    @Override
    protected double sumar(double a, double b) {
        return a + b;
    }
    
    @Override
    protected double restar(double a, double b) {
        return a - b;
    }
    
    @Override
    protected double multiplicar(double a, double b) {
        return a * b;
    }
    
    @Override
    protected double dividir(double a, double b) throws ArithmeticException {
        if (!esValidoDividir(b)) {
            throw new ArithmeticException("División por cero");
        }
        return a / b;
    }
}