import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 * @author Tomas Macias Castela.
 *
 */
public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	int vertical = 0, horizontal = 0;
	boolean error;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame("Buscaminas de Tom�s");
		ventana.setBounds(100, 100, 700, 500);
		error = false;
		while (vertical < 4 || error) {
			error = false;
			try {
				vertical = Integer.parseInt(JOptionPane.showInputDialog("�Cuantas filas deseas?"));
			} catch (NumberFormatException e) {
				error = true;
			}
		}
		error = false;
		while (horizontal < 4 || error) {
			error = false;
			try {
				horizontal = Integer.parseInt(JOptionPane.showInputDialog("�Cuantas columnas deseas?"));
			} catch (NumberFormatException e) {
				error = true;
			}
		}
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego(vertical, horizontal);
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(vertical, horizontal));

		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.ipady = 40;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = horizontal;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[vertical][horizontal];
		for (int i = 0; i < vertical; i++) {
			for (int j = 0; j < horizontal; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[vertical][horizontal];
		for (int i = 0; i < vertical; i++) {
			for (int j = 0; j < horizontal; j++) {
				botonesJuego[i][j] = new JButton("");
				panelesJuego[i][j].add(botonesJuego[i][j]);

			}
		}
		habilitarBotones(false);

		// BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {

		botonEmpezar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Inicializamos una nueva partida
				habilitarBotones(true);
				iniciarPartida();
				refrescarPantalla();
			}
		});

		for (int i = 0; i < vertical; i++) {
			for (int j = 0; j < horizontal; j++) {
				botonesJuego[i][j].addMouseListener(new ActionBoton(i, j, this));
			}
		}
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda Saca
	 * el botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto
	 * según la siguiente correspondecia (consultar la variable
	 * correspondeciaColor): - 0 : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 ó
	 * más : rojo
	 * 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		// TODO
		int numero = juego.getMinasAlrededor(i, j);
		if (juego.abrirCasilla(i, j)) {
			JLabel nMina = new JLabel();
			panelesJuego[i][j].removeAll();
			// Opciones para el jlabel (Texto, color y alineacion).
			nMina.setText(String.valueOf(juego.getMinasAlrededor(i, j)));
			nMina.setForeground(correspondenciaColores[numero]);
			nMina.setHorizontalAlignment(JLabel.CENTER);

			panelesJuego[i][j].add(nMina);
			SonidoBoton sonido = new SonidoBoton(0);
			actualizarPuntuacion();
			refrescarPantalla();
			mostrarFinJuego(false);
		} else {
			SonidoBoton sonido = new SonidoBoton(1);
			mostrarFinJuego(true);

		}

	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha
	 *                     explotado una mina (true) o bien porque hemos desactivado
	 *                     todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		// TODO
		int op = -2;
		// Si hemos perdido.
		if (porExplosion) {
			mostrarInformacionTablero();
			habilitarBotones(false);
			op = JOptionPane.showConfirmDialog(ventana, "�Quieres volver a jugar?", "HAS PERDIDO",
					JOptionPane.YES_NO_OPTION, 0, new ImageIcon("perder.png"));
		}
		// Si hemos ganado.
		if (!porExplosion && juego.esFinJuego()) {
			habilitarBotones(false);
			op = JOptionPane.showConfirmDialog(ventana, "�Quieres volver a jugar?", "HAS GANADO.",
					JOptionPane.YES_NO_OPTION, 0, new ImageIcon("ganar.png"));
		}
		// Si es si iniciamos de nuevo el juego.
		if (op == 0) {

			iniciarPartida();
			refrescarPantalla();
		}
		// Si es no cerramos el juego.
		if (op == 1 || op == -1) {
			ventana.dispose();
		}

	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(String.valueOf(juego.getPuntuacion()));
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

	/**
	 * Metodo para activar desactivar los botones.
	 * 
	 * @param activo si activo es true de habilitan, si es false se deshabilitan.
	 */
	public void habilitarBotones(boolean activo) {
		for (int i = 0; i < vertical; i++) {
			for (int j = 0; j < horizontal; j++) {
				botonesJuego[i][j].setEnabled(activo);
			}
		}
	}

	/**
	 * Metodo para iniciar la partida de buscaminas.
	 */
	public void iniciarPartida() {
		pantallaPuntuacion.setText("0");
		habilitarBotones(true);
		juego.inicializarPartida();
		for (int i = 0; i < vertical; i++) {
			for (int j = 0; j < horizontal; j++) {
				panelesJuego[i][j].removeAll();
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

	}

	/**
	 * Metodo para cuando perdamos mostramos todas las casillas y su informaci�n.
	 */
	public void mostrarInformacionTablero() {
		for (int i = 0; i < vertical; i++) {
			for (int j = 0; j < horizontal; j++) {
				if (!juego.abrirCasilla(i, j)) {
					// Mostramos una imagen en el panel.
					JLabel imagenMina = new JLabel();
					ImageIcon mina = new ImageIcon("mina.png");
					imagenMina.setIcon(mina);
					imagenMina.setHorizontalAlignment(JLabel.CENTER);
					panelesJuego[i][j].removeAll();
					panelesJuego[i][j].add(imagenMina);
				} else {
					JLabel nMina = new JLabel();
					panelesJuego[i][j].removeAll();
					// Opciones para el jlabel (Texto, color y alineacion).
					nMina.setText(String.valueOf(juego.getMinasAlrededor(i, j)));
					nMina.setForeground(correspondenciaColores[juego.getMinasAlrededor(i, j)]);
					nMina.setHorizontalAlignment(JLabel.CENTER);

					panelesJuego[i][j].add(nMina);
				}
			}
		}
		refrescarPantalla();
	}

	/**
	 * Metodo para poner una bandera en el boton.
	 */
	public void ponerBandera(int i, int j) {
		JLabel imagenBandera = new JLabel();
		ImageIcon bandera = new ImageIcon("bandera.png");
		imagenBandera.setIcon(bandera);
		botonesJuego[i][j].setIcon(bandera);
		panelesJuego[i][j].add(botonesJuego[i][j]);
		refrescarPantalla();
	}

	/**
	 * Metodo para quitar bandera.
	 */
	public void quitarBandera(int i, int j) {
		botonesJuego[i][j].setIcon(null);
		panelesJuego[i][j].add(botonesJuego[i][j]);
		refrescarPantalla();
	}

	/**
	 * Metodo para comprobar si hay bandera.
	 * 
	 * @param i
	 * @param j
	 * @return true si no hay bandera, false si hay bandera.
	 */
	public boolean comprobarBandera(int i, int j) {
		return botonesJuego[i][j].getIcon() == null;
	}
}
