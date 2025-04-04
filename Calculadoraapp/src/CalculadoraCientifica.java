/**
 * Clase de la interfaz gráfica de la calculadora científica
 */
public class CalculadoraCientifica extends javax.swing.JFrame {
    private final OperacionesCientificas operaciones;
    private double valorActual = 0;
    private double memoria = 0;
    private String operacionPendiente = "";
    private boolean iniciarNuevoNumero = true;
    private boolean usarRadianes = true;
    // Flag para tema: true = tema claro, false = tema oscuro
    private boolean temaClaro = true;

    // Componentes de la interfaz gráfica
    private javax.swing.JTextField pantalla;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelCientificos;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JRadioButton radioGrados;
    private javax.swing.JRadioButton radioRadianes;
    private javax.swing.JLabel labelMemoria;
    // Botón para alternar el tema
    private javax.swing.JButton btnCambiarTema;
    
    // Componentes para el historial
    private javax.swing.JTextArea historial;
    private javax.swing.JScrollPane scrollHistorial;
    // Variable para ir armando la operación actual
    private String operacionActual = "";

    /**
     * Constructor de la calculadora científica
     */
    public CalculadoraCientifica() {
        operaciones = new OperacionesCientificas();
        inicializarComponentes();
        setTitle("Calculadora Científica");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        
        // Cargar y establecer el icono
        try {
            java.io.File iconFile = new java.io.File("resources/calculadora_icon.png");
            if (iconFile.exists()) {
                setIconImage(javax.imageio.ImageIO.read(iconFile));
            } else {
                System.out.println("Archivo de icono no encontrado");
            }
        } catch (java.io.IOException e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());
        }
        
        // Aplicar tema por defecto (claro)
        aplicarTemaClaro();
        
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
        
        // Botón para cambiar el tema
        btnCambiarTema = new javax.swing.JButton("Tema Oscuro");
        btnCambiarTema.addActionListener(e -> cambiarTema());
        panelEstado.add(btnCambiarTema);
        
        getContentPane().add(panelEstado, java.awt.BorderLayout.SOUTH);
        
        // Inicialización del historial y su scroll
        historial = new javax.swing.JTextArea();
        historial.setEditable(false);
        scrollHistorial = new javax.swing.JScrollPane(historial);
        scrollHistorial.setPreferredSize(new java.awt.Dimension(200, 0)); // ancho fijo para el historial
        getContentPane().add(scrollHistorial, java.awt.BorderLayout.WEST);
    }
    
    /**
     * Crea un botón con el texto especificado, añade el listener para procesar la acción y el efecto visual.
     * @param texto texto a mostrar en el botón
     * @return botón configurado
     */
    private javax.swing.JButton crearBoton(String texto) {
        javax.swing.JButton boton = new javax.swing.JButton(texto);
        boton.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        // Listener principal para procesar la acción del botón
        boton.addActionListener(e -> procesarBoton(texto));
        
        // Listener adicional para el efecto "brillar en verde"
        boton.addActionListener(e -> {
            // Cambiar color a verde al pulsar
            boton.setBackground(java.awt.Color.GREEN);
            // Iniciar un Timer para restaurar el color original tras 200 ms
            javax.swing.Timer timer = new javax.swing.Timer(200, evt -> {
                if (temaClaro) {
                    boton.setBackground(new java.awt.Color(255, 255, 255));
                } else {
                    boton.setBackground(new java.awt.Color(45, 45, 45));
                }
                ((javax.swing.Timer) evt.getSource()).stop();
            });
            timer.setRepeats(false);
            timer.start();
        });
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
     * Lógica para cambiar de tema al pulsar el botón
     */
    private void cambiarTema() {
        if (temaClaro) {
            aplicarTemaOscuro();
            btnCambiarTema.setText("Tema Claro");
        } else {
            aplicarTemaClaro();
            btnCambiarTema.setText("Tema Oscuro");
        }
        temaClaro = !temaClaro;
        repaint();
    }
    
    /**
     * Aplica el tema claro a todos los componentes de la interfaz
     */
    private void aplicarTemaClaro() {
        java.awt.Color fondoClaro = new java.awt.Color(255, 255, 255);
        java.awt.Color textoOscuro = new java.awt.Color(0, 0, 0);
        
        getContentPane().setBackground(fondoClaro);
        pantalla.setBackground(fondoClaro);
        pantalla.setForeground(textoOscuro);
        
        panelBotones.setBackground(fondoClaro);
        panelCientificos.setBackground(fondoClaro);
        panelEstado.setBackground(fondoClaro);
        
        for (java.awt.Component comp : panelBotones.getComponents()) {
            if (comp instanceof javax.swing.JButton) {
                javax.swing.JButton boton = (javax.swing.JButton) comp;
                boton.setBackground(fondoClaro);
                boton.setForeground(textoOscuro);
            }
        }
        
        for (java.awt.Component comp : panelCientificos.getComponents()) {
            if (comp instanceof javax.swing.JButton) {
                javax.swing.JButton boton = (javax.swing.JButton) comp;
                boton.setBackground(fondoClaro);
                boton.setForeground(textoOscuro);
            }
        }
        
        radioGrados.setBackground(fondoClaro);
        radioGrados.setForeground(textoOscuro);
        radioRadianes.setBackground(fondoClaro);
        radioRadianes.setForeground(textoOscuro);
        labelMemoria.setBackground(fondoClaro);
        labelMemoria.setForeground(textoOscuro);
        // Actualizamos también el historial
        historial.setBackground(fondoClaro);
        historial.setForeground(textoOscuro);
    }
    
    /**
     * Aplica el tema oscuro a todos los componentes de la interfaz
     */
    private void aplicarTemaOscuro() {
        java.awt.Color fondoOscuro = new java.awt.Color(45, 45, 45);
        java.awt.Color textoClaro = new java.awt.Color(230, 230, 230);
        
        getContentPane().setBackground(fondoOscuro);
        pantalla.setBackground(fondoOscuro);
        pantalla.setForeground(textoClaro);
        
        panelBotones.setBackground(fondoOscuro);
        panelCientificos.setBackground(fondoOscuro);
        panelEstado.setBackground(fondoOscuro);
        
        for (java.awt.Component comp : panelBotones.getComponents()) {
            if (comp instanceof javax.swing.JButton) {
                javax.swing.JButton boton = (javax.swing.JButton) comp;
                boton.setBackground(fondoOscuro);
                boton.setForeground(textoClaro);
            }
        }
        
        for (java.awt.Component comp : panelCientificos.getComponents()) {
            if (comp instanceof javax.swing.JButton) {
                javax.swing.JButton boton = (javax.swing.JButton) comp;
                boton.setBackground(fondoOscuro);
                boton.setForeground(textoClaro);
            }
        }
        
        radioGrados.setBackground(fondoOscuro);
        radioGrados.setForeground(textoClaro);
        radioRadianes.setBackground(fondoOscuro);
        radioRadianes.setForeground(textoClaro);
        labelMemoria.setBackground(fondoOscuro);
        labelMemoria.setForeground(textoClaro);
        // Actualizamos también el historial
        historial.setBackground(fondoOscuro);
        historial.setForeground(textoClaro);
    }
    
    /**
     * Procesa la acción del botón pulsado y actualiza el historial
     * @param texto texto del botón pulsado
     */
    private void procesarBoton(String texto) {
        // Para la mayoría de botones (números, operadores y funciones),
        // se añade el texto a la operación actual.
        if (!texto.equals("=") && !texto.equals("C") && !texto.equals("CE")
                && !texto.equals("M+") && !texto.equals("MR")
                && !texto.equals("Tema Oscuro") && !texto.equals("Tema Claro")) {
            operacionActual += texto;
        }
        
        switch (texto) {
            case "=":
                // Añadimos el signo "=" y completamos la operación
                operacionActual += "=";
                calcularResultado();
                operacionActual += pantalla.getText();
                logOperacion(operacionActual);
                operacionActual = "";
                break;
            case "C":
                limpiarTodo();
                operacionActual = "";
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
            case "+": case "-": case "*": case "/":
                procesarOperacionBasica(texto);
                break;
            case "π":
                insertarConstante(Math.PI);
                break;
            case "e":
                insertarConstante(Math.E);
                break;
            default:
                // Para dígitos y punto, se procesa el número
                procesarNumero(texto);
                break;
        }
    }
    
    /**
     * Agrega una línea al historial con la operación completada
     * @param mensaje operación completa (expresión y resultado)
     */
    private void logOperacion(String mensaje) {
        historial.append(mensaje + "\n");
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
            String valorActualPantalla = pantalla.getText();
            // Evitar múltiples puntos decimales
            if (digito.equals(".") && valorActualPantalla.contains(".")) {
                return;
            }
            pantalla.setText(valorActualPantalla + digito);
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
        this.valorActual = valor;
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
    
    // /**
    //  * Método main para ejecutar la aplicación
    //  */
    // public static void main(String[] args) {
    //     java.awt.EventQueue.invokeLater(() -> {
    //         new CalculadoraCientifica().setVisible(true);
    //     });
    // }
}
