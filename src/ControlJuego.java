import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay
 * una mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de
 * la partida
 * 
 * @author Tomas Macias Castela.
 *
 */
public class ControlJuego {
	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int[][] tablero;
	private int puntuacion;

	public ControlJuego() {
		// Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
	}

	/**
	 * Método para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrá inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {
		int i, j;
		boolean minaOcupada = false;
		// TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero
		// anterior, lo pongo todo a cero para inicializarlo.
		for (int m = 0; m < MINAS_INICIALES; m++) {
			do {
				// Calculamos la fila
				i = (int) (Math.random() * LADO_TABLERO);
				// Calculamos la columna
				j = (int) (Math.random() * LADO_TABLERO);
				// Si la posicion del tablero contiene mina
				if (tablero[i][j] == -1) {
					minaOcupada = true;
				} else {// Si no hay mina, se coloca en la posicion.
					tablero[i][j] = -1;
					minaOcupada = false;
				}
				// Si hay mina, se realiza de nuevo el random
			} while (minaOcupada);
		}

		// Al final del m�todo hay que guardar el n�mero de minas para las casillas
		// que no son mina:
		for (i = 0; i < tablero.length; i++) {
			for (j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA) {
					tablero[i][j] = calculoMinasAdjuntas(i, j);
				}
			}
		}
		depurarTablero();
	}

	/**
	 * Cálculo de las minas adjuntas: Para calcular el número de minas tenemos que
	 * tener en cuenta que no nos salimos nunca del tablero. Por lo tanto, como
	 * mucho la i y la j valdrán LADO_TABLERO-1. Por lo tanto, como poco la i y la
	 * j valdrán 0.
	 * 
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j) {
		int minaTotal = 0;
		// v --> vertical
		// h --> horizontal.
		int v, h;

		// Comprobamos la minas de alrededor de la esquina de arriba a la izquierda.
		// Comprobamos abajo-abajo(derecha)-derecha.
		if (i == 0 && j == 0) {
			for (v = i; v <= i + 1; v++) {
				for (h = j; h <= j + 1; h++) {
					if (tablero[v][h] == MINA) {
						minaTotal++;
					}
				}
			}
		}
		
		// Comprobamos las minas de alrededor de la esquina de abajo a la izquierda.
		// Comprobamos arriba-arriba(derecha)-derecha.
		if (i == tablero.length - 1 && j == 0) {
			for (v = i - 1; v <= i; v++) {
				for (h = j; h <= j + 1; h++) {
					if (tablero[v][h] == MINA) {
						minaTotal++;
					}
				}
			}
		}
		
		// Comprobamos las minas de alrededor de la esquina de arriba a la derecha.
		// Comprobamos arriba-arriba(derecha)-derecha.
		if (i == 0 && j == tablero.length - 1) {
			for (v = i; v <= i + 1; v++) {
				for (h = j - 1; h <= j; h++) {
					if (tablero[v][h] == MINA) {
						minaTotal++;
					}
				}
			}
		}

		// Comprobamos las minas de alrededor de la esquina de abajo a la derecha.
		// Comprobamos arriba-arriba(derecha)-derecha.
		if (i == tablero.length - 1 && j == tablero.length - 1) {
			for (v = i - 1; v <= i; v++) {
				for (h = j - 1; h <= j; h++) {
					if (tablero[v][h] == MINA) {
						minaTotal++;
					}
				}
			}
		}

		// Comprobamos las minas de alrededor de las casillas fuera de los margenes.
		// Mientras "i" y "j" sea distinto a las esquinas del tablero.
		if ((i != 0 && i != tablero.length - 1) && (j != 0 && j != tablero.length - 1)) {
			// Inicializamos "v" y "h" al valor de "i" y "j" menos 1. 
			//Hasta el valor de "i"  y "j" +1.
			// Para comprobar.
			// arriba(derecha/izquierda)-derecha/izquierda-arriba/abajo-abajo(derecha/izquierda).
			for (v = i - 1; v <= i + 1; v++) {
				for (h = j - 1; h <= j + 1; h++) {
					if (tablero[v][h] == MINA) {
						minaTotal++;
					}
				}
			}
		}

		return minaTotal;
	}

	/**
	 * Método que nos permite
	 * 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por
	 *      el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j) {
		return false;
	}

	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		return false;
	}

	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza
	 * para depurar
	 */
	public void depurarTablero() {
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuación: " + puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta
	 *      calcularlo, símplemente consultarlo
	 * @param i : posición vertical de la celda.
	 * @param j : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return 0;
	}

	/**
	 * Método que devuelve la puntuación actual
	 * 
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return 0;
	}

}
