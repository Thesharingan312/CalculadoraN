/**
 * Clase base que define las operaciones fundamentales de una calculadora
 */
abstract class Calculadora {
    // Métodos abstractos que deben ser implementados por las subclases
    protected abstract double sumar(double a, double b);
    protected abstract double restar(double a, double b);
    protected abstract double multiplicar(double a, double b);
    protected abstract double dividir(double a, double b) throws ArithmeticException;
    
    // Método final para verificar división por cero
    protected final boolean esValidoDividir(double divisor) {
        final double EPSILON = 1E-10;
        return Math.abs(divisor) > EPSILON;
    }
}