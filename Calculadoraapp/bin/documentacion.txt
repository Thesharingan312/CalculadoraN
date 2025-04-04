🧮 Documentación de la Calculadora Científica
📂 Estructura del Proyecto
El proyecto está dividido en varios archivos Java, cada uno con una función específica:

CalculadoraApp.java → Es el corazón de la aplicación, el punto de entrada.

Calculadora.java → Es una plantilla base que define cómo debe funcionar una calculadora.

CalculadoraBasica.java → Implementa las operaciones básicas como suma, resta, multiplicación y división.

OperacionesCientificas.java → Agrega funciones avanzadas como trigonometría, potencias y logaritmos.

CalculadoraCientifica.java → Maneja la interfaz gráfica y la interacción con el usuario.

🔹 Descripción de las Clases
🏁 CalculadoraApp.java (Punto de entrada)
Esta clase es la que inicia todo. Contiene el famoso main(), que se encarga de arrancar la interfaz gráfica usando SwingUtilities.

🔹 Lo que hace:
✔️ Lanza la aplicación.
✔️ Se asegura de que la interfaz se ejecute en el hilo adecuado.

🏗️ Calculadora.java (Base de todo)
Es una clase abstracta que define cómo deben ser las calculadoras en este proyecto. No tiene una implementación directa, sino que obliga a sus "hijas" a completar su funcionalidad.

🔹 Métodos abstractos (deben ser implementados en las subclases):
✔️ sumar(a, b) → Suma dos números.
✔️ restar(a, b) → Resta dos números.
✔️ multiplicar(a, b) → Multiplica dos números.
✔️ dividir(a, b) → Divide dos números (con validación).

🔹 Método que ya viene hecho:
✔️ esValidoDividir(divisor) → Verifica si el divisor no es 0 para evitar errores.

💡 Características:

Usa abstract para forzar la implementación en las subclases.

Usa protected para que los métodos sean accesibles solo dentro de las subclases.

Usa final para evitar que ciertos métodos sean sobrescritos.

➕➖ CalculadoraBasica.java (Operaciones Básicas)
Esta clase extiende la calculadora base y completa las funciones matemáticas simples.

🔹 Métodos que implementa:
✔️ sumar(a, b) → Devuelve la suma de dos números.
✔️ restar(a, b) → Devuelve la resta de dos números.
✔️ multiplicar(a, b) → Devuelve la multiplicación de dos números.
✔️ dividir(a, b) → Realiza la división, pero primero verifica que el divisor no sea 0.

💡 Extras:

Lanza excepciones en caso de división por cero.

📐 OperacionesCientificas.java (Funciones Avanzadas)
Extiende la calculadora básica y añade más herramientas matemáticas.

🔹 Constantes y atributos:
✔️ DEG_TO_RAD → Convierte grados a radianes.
✔️ usarRadianes → Bandera para elegir entre grados o radianes.

🔹 Métodos avanzados:
✔️ setModoAngulo(usarRadianes) → Cambia entre grados y radianes.
✔️ convertirARadianes(angulo) → Convierte un ángulo a radianes si es necesario.
✔️ seno(angulo), coseno(angulo), tangente(angulo) → Calculan funciones trigonométricas.
✔️ potencia(base, exponente) → Calcula potencias.
✔️ raizCuadrada(numero) → Calcula la raíz cuadrada.
✔️ logaritmoNatural(numero), logaritmoBase10(numero) → Calculan logaritmos.
✔️ factorial(n) → Calcula el factorial de un número entero.

💡 Extras:

Maneja excepciones para evitar errores matemáticos.

Usa la clase Math de Java para hacer los cálculos.

🎨 CalculadoraCientifica.java (Interfaz Gráfica)
Aquí es donde sucede la magia visual. Esta clase maneja la pantalla de la calculadora y la interacción del usuario.

🔹 Atributos principales:
✔️ operaciones → Instancia de OperacionesCientificas para realizar los cálculos.
✔️ valorActual → Almacena el número que está en pantalla.
✔️ memoria → Permite guardar un valor temporal.
✔️ operacionPendiente → Almacena la operación en curso (+, -, *, etc.).
✔️ iniciarNuevoNumero → Indica si el usuario está ingresando un nuevo número.
✔️ usarRadianes → Bandera para cambiar entre grados y radianes.

🔹 Componentes de la interfaz:
✔️ pantalla → Un JTextField que muestra los números y resultados.
✔️ panelBotones → Panel con botones numéricos y operaciones básicas.
✔️ panelCientificos → Panel con funciones avanzadas (seno, coseno, etc.).
✔️ panelEstado → Panel de configuraciones como el cambio de modo de ángulo.
✔️ radioGrados y radioRadianes → Botones para seleccionar grados o radianes.
✔️ labelMemoria → Etiqueta que indica si hay un número guardado en memoria.

🔹 Métodos principales:
✔️ inicializarComponentes() → Crea y configura todos los elementos gráficos.
✔️ crearBoton(texto) → Crea un botón con su acción correspondiente.
✔️ cambiarModoAngulo(usarRadianes) → Alterna entre grados y radianes.
✔️ procesarBoton(texto) → Decide qué hacer cuando se presiona un botón.
✔️ procesarNumero(texto) → Agrega números a la pantalla.
✔️ procesarOperacionBasica(operacion) → Maneja suma, resta, multiplicación y división.
✔️ procesarFuncionUnaria(funcion) → Maneja funciones como raíz cuadrada y logaritmos.
✔️ procesarOperacionBinaria(operacion) → Maneja operaciones como potencias.
✔️ insertarConstante(valor) → Inserta constantes como π o e.
✔️ calcularResultado() → Realiza el cálculo de la operación actual.
✔️ formatearResultado(valor) → Ajusta la forma en que se muestra el resultado.
✔️ limpiarEntrada() → Borra la entrada actual sin resetear todo.
✔️ limpiarTodo() → Reinicia completamente la calculadora.
✔️ guardarEnMemoria() → Guarda el valor en la memoria de la calculadora.
✔️ recuperarDeMemoria() → Recupera el valor almacenado.

