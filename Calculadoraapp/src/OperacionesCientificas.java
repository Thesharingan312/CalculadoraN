/**
 * Clase para operaciones científicas que extiende las operaciones básicas
 */
class OperacionesCientificas extends CalculadoraBasica {
    // Constante para conversión de grados a radianes
    private static final double DEG_TO_RAD = Math.PI / 180.0;
    
    // Flag para indicar si se trabaja en radianes o grados
    private boolean usarRadianes = true;
    
    /**
     * Establece el modo de ángulo (radianes o grados)
     * @param usarRadianes true para usar radianes, false para grados
     */
    public void setModoAngulo(boolean usarRadianes) {
        this.usarRadianes = usarRadianes;
    }
    
    /**
     * Convierte el ángulo a radianes si es necesario
     * @param angulo valor del ángulo
     * @return ángulo en radianes
     */
    private double convertirARadianes(double angulo) {
        return usarRadianes ? angulo : angulo * DEG_TO_RAD;
    }
    
    /**
     * Calcula el seno de un ángulo
     * @param angulo valor del ángulo
     * @return seno del ángulo
     */
    public double seno(double angulo) {
        return Math.sin(convertirARadianes(angulo));
    }
    
    /**
     * Calcula el coseno de un ángulo
     * @param angulo valor del ángulo
     * @return coseno del ángulo
     */
    public double coseno(double angulo) {
        return Math.cos(convertirARadianes(angulo));
    }
    
    /**
     * Calcula la tangente de un ángulo
     * @param angulo valor del ángulo
     * @return tangente del ángulo
     * @throws ArithmeticException si el ángulo corresponde a un valor indefinido
     */
    public double tangente(double angulo) throws ArithmeticException {
        double result = Math.tan(convertirARadianes(angulo));
        if (Double.isInfinite(result)) {
            throw new ArithmeticException("Tangente indefinida");
        }
        return result;
    }
    
    /**
     * Calcula la potencia de un número
     * @param base base de la potencia
     * @param exponente exponente
     * @return base elevada al exponente
     */
    public double potencia(double base, double exponente) {
        return Math.pow(base, exponente);
    }
    
    /**
     * Calcula la raíz cuadrada de un número
     * @param numero valor para calcular la raíz
     * @return raíz cuadrada del número
     * @throws ArithmeticException si el número es negativo
     */
    public double raizCuadrada(double numero) throws ArithmeticException {
        if (numero < 0) {
            throw new ArithmeticException("No se puede calcular la raíz cuadrada de un número negativo");
        }
        return Math.sqrt(numero);
    }
    
    /**
     * Calcula el logaritmo natural de un número
     * @param numero valor para calcular el logaritmo
     * @return logaritmo natural del número
     * @throws ArithmeticException si el número es negativo o cero
     */
    public double logaritmoNatural(double numero) throws ArithmeticException {
        if (numero <= 0) {
            throw new ArithmeticException("No se puede calcular el logaritmo de un número no positivo");
        }
        return Math.log(numero);
    }
    
    /**
     * Calcula el logaritmo en base 10 de un número
     * @param numero valor para calcular el logaritmo
     * @return logaritmo en base 10 del número
     * @throws ArithmeticException si el número es negativo o cero
     */
    public double logaritmoBase10(double numero) throws ArithmeticException {
        if (numero <= 0) {
            throw new ArithmeticException("No se puede calcular el logaritmo de un número no positivo");
        }
        return Math.log10(numero);
    }
    
    /**
     * Calcula el factorial de un número
     * @param n número para calcular el factorial
     * @return factorial del número
     * @throws ArithmeticException si el número es negativo
     */
    public double factorial(int n) throws ArithmeticException {
        if (n < 0) {
            throw new ArithmeticException("No se puede calcular el factorial de un número negativo");
        }
        if (n > 170) {
            throw new ArithmeticException("Resultado demasiado grande para representar");
        }
        
        double resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }
}