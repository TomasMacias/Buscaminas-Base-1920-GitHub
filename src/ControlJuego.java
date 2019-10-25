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
	final int MINAS_INICIALES;
	final int LADO_TABLERO_VERTICAL;

	final int LADO_TABLERO_HORIZONTAL;
	private int[][] tablero;
	private int puntuacion;

	public ControlJuego(int i, int j) {
		// Creamos el tablero:
		tablero = new int[i][j];
		MINAS_INICIALES = ((i*j)*20)/100;
		LADO_TABLERO_VERTICAL=i;
		LADO_TABLERO_HORIZONTAL=j;
	}

	/**
	 * Método para generar un nuevo tablero de partida:
	 * i => Lado vertical
	 * j => Lado horizontal.
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrá inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {
		int i, j;
		boolean minaOcupada = false;
		puntuacion = 0;
		// TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero
		// anterior, lo pongo todo a cero para inicializarlo.
		for (i = 0; i < LADO_TABLERO_VERTICAL; i++) {
			for (j = 0; j < LADO_TABLERO_HORIZONTAL; j++) {
				tablero[i][j]=0;
			}
		}
		
		
		for (int minasTotales = 0; minasTotales < MINAS_INICIALES; minasTotales++) {
			do {
				// Calculamos la fila
				i = (int) (Math.random() * LADO_TABLERO_VERTICAL);
				// Calculamos la columna
				j = (int) (Math.random() * LADO_TABLERO_HORIZONTAL);
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
		for (i = 0; i < LADO_TABLERO_VERTICAL; i++) {
			for (j = 0; j < LADO_TABLERO_HORIZONTAL; j++) {
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

		for (v = i - 1; v <= i + 1; v++) {
			for (h = j - 1; h <= j + 1; h++) {
				// Condicion que no pase de los limites de las esquinas y los margenes.
				if (!(v < 0 || v > LADO_TABLERO_VERTICAL - 1 || h < 0
						|| h > LADO_TABLERO_HORIZONTAL - 1)) {

					if (tablero[v][h] == MINA) {
						minaTotal++;

					} // Cierre if
					
				} // Cierre if condicion
			} // Cierre for horizontal.
		} // Cierre for vertical.

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
		if(tablero[i][j]!=MINA) {
			puntuacion++;
			return true;
		}else {
			return false;
		}
		
	}

	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		return puntuacion == LADO_TABLERO_VERTICAL*LADO_TABLERO_HORIZONTAL-MINAS_INICIALES;
	}

	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza
	 * para depurar
	 */
	public void depurarTablero() {
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < LADO_TABLERO_VERTICAL; i++) {
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
		return tablero[i][j];
	}

	/**
	 * Método que devuelve la puntuación actual
	 * 
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}

}
