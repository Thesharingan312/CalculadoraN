/**
 * Clase de la interfaz gráfica de la calculadora científica
 */
class CalculadoraCientifica extends javax.swing.JFrame {
    private final OperacionesCientificas operaciones;
    private double valorActual = 0;
    private double memoria = 0;
    private String operacionPendiente = "";
    private boolean iniciarNuevoNumero = true;
    private boolean usarRadianes = true;
    
    // Componentes de la interfaz gráfica
    private javax.swing.JTextField pantalla;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelCientificos;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JRadioButton radioGrados;
    private javax.swing.JRadioButton radioRadianes;
    private javax.swing.JLabel labelMemoria;
    
    /**
     * Constructor de la calculadora científica
     */
    public CalculadoraCientifica() {
        operaciones = new OperacionesCientificas();
        inicializarComponentes();
        setTitle("Calculadora Científica");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Inicializa todos los componentes de la interfaz gráfica
     */
    private void inicializarComponentes() {
        // Panel principal con BorderLayout
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));
        
        // Campo de texto para mostrar resultados
        pantalla = new javax.swing.JTextField("0");
        pantalla.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        pantalla.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pantalla.setEditable(false);
        getContentPane().add(pantalla, java.awt.BorderLayout.NORTH);
        
        // Panel para los botones numéricos y operaciones básicas
        panelBotones = new javax.swing.JPanel(new java.awt.GridLayout(5, 4, 3, 3));
        
        // Botones numéricos y operaciones básicas
        String[] botonesBasicos = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };
        
        for (String texto : botonesBasicos) {
            javax.swing.JButton boton = crearBoton(texto);
            panelBotones.add(boton);
        }
        
        // Botones adicionales
        String[] botonesAdicionales = {"C", "CE", "M+", "MR"};
        for (String texto : botonesAdicionales) {
            javax.swing.JButton boton = crearBoton(texto);
            panelBotones.add(boton);
        }
        
        getContentPane().add(panelBotones, java.awt.BorderLayout.CENTER);
        
        // Panel para funciones científicas
        panelCientificos = new javax.swing.JPanel(new java.awt.GridLayout(5, 3, 3, 3));
        
        // Botones de funciones científicas
        String[] botonesCientificos = {
            "sin", "cos", "tan",
            "√", "x²", "xⁿ",
            "log", "ln", "n!",
            "π", "e", "mod"
        };
        
        for (String texto : botonesCientificos) {
            javax.swing.JButton boton = crearBoton(texto);
            panelCientificos.add(boton);
        }
        
        getContentPane().add(panelCientificos, java.awt.BorderLayout.EAST);
        
        // Panel de estado y configuración
        panelEstado = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        
        // Radio buttons para selección de modo de ángulo
        radioRadianes = new javax.swing.JRadioButton("Radianes", true);
        radioGrados = new javax.swing.JRadioButton("Grados", false);
        
        // Agrupar los radio buttons
        javax.swing.ButtonGroup grupoAngulos = new javax.swing.ButtonGroup();
        grupoAngulos.add(radioRadianes);
        grupoAngulos.add(radioGrados);
        
        // Añadir listener a los radio buttons
        radioRadianes.addActionListener(e -> cambiarModoAngulo(true));
        radioGrados.addActionListener(e -> cambiarModoAngulo(false));
        
        panelEstado.add(radioRadianes);
        panelEstado.add(radioGrados);
        
        // Etiqueta para mostrar el estado de la memoria
        labelMemoria = new javax.swing.JLabel("Memoria: 0");
        panelEstado.add(labelMemoria);
        
        getContentPane().add(panelEstado, java.awt.BorderLayout.SOUTH);
    }
    
    /**
     * Crea un botón con el texto especificado y añade el listener correspondiente
     * @param texto texto a mostrar en el botón
     * @return botón configurado
     */
    private javax.swing.JButton crearBoton(String texto) {
        javax.swing.JButton boton = new javax.swing.JButton(texto);
        boton.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        
        // Añadir listener al botón
        boton.addActionListener(e -> procesarBoton(texto));
        
        return boton;
    }
    
    /**
     * Cambia el modo de ángulo entre radianes y grados
     * @param usarRadianes true para usar radianes, false para grados
     */
    private void cambiarModoAngulo(boolean usarRadianes) {
        this.usarRadianes = usarRadianes;
        operaciones.setModoAngulo(usarRadianes);
    }
    
    /**
     * Procesa la acción del botón pulsado
     * @param texto texto del botón pulsado
     */
    private void procesarBoton(String texto) {
        try {
            switch (texto) {
                case "0": case "1": case "2": case "3": case "4":
                case "5": case "6": case "7": case "8": case "9":
                case ".":
                    procesarNumero(texto);
                    break;
                case "+": case "-": case "*": case "/":
                    procesarOperacionBasica(texto);
                    break;
                case "=":
                    calcularResultado();
                    break;
                case "C":
                    limpiarTodo();
                    break;
                case "CE":
                    limpiarEntrada();
                    break;
                case "M+":
                    guardarEnMemoria();
                    break;
                case "MR":
                    recuperarDeMemoria();
                    break;
                case "sin": case "cos": case "tan":
                case "√": case "x²": case "log": case "ln": case "n!":
                    procesarFuncionUnaria(texto);
                    break;
                case "xⁿ": case "mod":
                    procesarOperacionBinaria(texto);
                    break;
                case "π":
                    insertarConstante(Math.PI);
                    break;
                case "e":
                    insertarConstante(Math.E);
                    break;
            }
        } catch (ArithmeticException ex) {
            pantalla.setText("Error: " + ex.getMessage());
            iniciarNuevoNumero = true;
        } catch (Exception ex) {
            pantalla.setText("Error");
            iniciarNuevoNumero = true;
        }
    }
    
    /**
     * Procesa la entrada de un dígito o punto decimal
     * @param digito dígito o punto decimal
     */
    private void procesarNumero(String digito) {
        if (iniciarNuevoNumero) {
            pantalla.setText(digito);
            iniciarNuevoNumero = false;
        } else {
            String valorActual = pantalla.getText();
            // Evitar múltiples puntos decimales
            if (digito.equals(".") && valorActual.contains(".")) {
                return;
            }
            pantalla.setText(valorActual + digito);
        }
    }
    
    /**
     * Procesa una operación aritmética básica
     * @param operacion símbolo de la operación
     */
    private void procesarOperacionBasica(String operacion) {
        calcularResultado();
        operacionPendiente = operacion;
        iniciarNuevoNumero = true;
    }
    
    /**
     * Procesa una función unaria (sin, cos, tan, etc.)
     * @param funcion nombre de la función
     */
    private void procesarFuncionUnaria(String funcion) {
        double valor = Double.parseDouble(pantalla.getText());
        
        switch (funcion) {
            case "sin":
                valor = operaciones.seno(valor);
                break;
            case "cos":
                valor = operaciones.coseno(valor);
                break;
            case "tan":
                valor = operaciones.tangente(valor);
                break;
            case "√":
                valor = operaciones.raizCuadrada(valor);
                break;
            case "x²":
                valor = operaciones.potencia(valor, 2);
                break;
            case "log":
                valor = operaciones.logaritmoBase10(valor);
                break;
            case "ln":
                valor = operaciones.logaritmoNatural(valor);
                break;
            case "n!":
                valor = operaciones.factorial((int)valor);
                break;
        }
        
        pantalla.setText(formatearResultado(valor));
        valorActual = valor;
        iniciarNuevoNumero = true;
    }
    
    /**
     * Procesa una operación binaria (potencia, módulo)
     * @param operacion nombre de la operación
     */
    private void procesarOperacionBinaria(String operacion) {
        calcularResultado();
        operacionPendiente = operacion;
        iniciarNuevoNumero = true;
    }
    
    /**
     * Inserta una constante matemática
     * @param valor valor de la constante
     */
    private void insertarConstante(double valor) {
        pantalla.setText(formatearResultado(valor));
        iniciarNuevoNumero = true;
    }
    
    /**
     * Calcula el resultado de la operación pendiente
     */
    private void calcularResultado() {
        if (!operacionPendiente.isEmpty()) {
            double segundoOperando = Double.parseDouble(pantalla.getText());
            
            switch (operacionPendiente) {
                case "+":
                    valorActual = operaciones.sumar(valorActual, segundoOperando);
                    break;
                case "-":
                    valorActual = operaciones.restar(valorActual, segundoOperando);
                    break;
                case "*":
                    valorActual = operaciones.multiplicar(valorActual, segundoOperando);
                    break;
                case "/":
                    valorActual = operaciones.dividir(valorActual, segundoOperando);
                    break;
                case "xⁿ":
                    valorActual = operaciones.potencia(valorActual, segundoOperando);
                    break;
                case "mod":
                    valorActual = valorActual % segundoOperando;
                    break;
            }
            
            pantalla.setText(formatearResultado(valorActual));
            operacionPendiente = "";
        } else {
            valorActual = Double.parseDouble(pantalla.getText());
        }
        
        iniciarNuevoNumero = true;
    }
    
    /**
     * Formatea el resultado para mostrarlo en pantalla
     * @param valor valor a formatear
     * @return valor formateado como cadena
     */
    private String formatearResultado(double valor) {
        if (valor == (long) valor) {
            return String.format("%d", (long) valor);
        } else {
            return String.format("%s", valor);
        }
    }
    
    /**
     * Limpia la entrada actual
     */
    private void limpiarEntrada() {
        pantalla.setText("0");
        iniciarNuevoNumero = true;
    }
    
    /**
     * Limpia toda la calculadora
     */
    private void limpiarTodo() {
        pantalla.setText("0");
        valorActual = 0;
        operacionPendiente = "";
        iniciarNuevoNumero = true;
    }
    
    /**
     * Guarda el valor actual en memoria
     */
    private void guardarEnMemoria() {
        memoria = Double.parseDouble(pantalla.getText());
        labelMemoria.setText("Memoria: " + formatearResultado(memoria));
        iniciarNuevoNumero = true;
    }
    
    /**
     * Recupera el valor almacenado en memoria
     */
    private void recuperarDeMemoria() {
        pantalla.setText(formatearResultado(memoria));
        iniciarNuevoNumero = true;
    }
}