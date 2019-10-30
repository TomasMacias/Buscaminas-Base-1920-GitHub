import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrÃ¡ que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author Tomas Macias Castela.
 *
 */
public class ActionBoton implements MouseListener {

	int i, j;
	VentanaPrincipal ventana;

	public ActionBoton(int i, int j, VentanaPrincipal ventana) {
		this.i = i;
		this.j = j;
		this.ventana = ventana;
	}

	/**
	 * Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// Click con el boton izquierdo.
		if (e.getButton() == MouseEvent.BUTTON1) {
			// Si no tenemos bandera, desvelamos la casilla.
			if (ventana.comprobarBandera(i, j)) {
				ventana.mostrarNumMinasAlrededor(i, j);
			}
		}
		// Click con el boton derecho.
		if (e.getButton() == MouseEvent.BUTTON3) {
			// Si no tenemos bandera.
			if (ventana.comprobarBandera(i, j)) {
				// Ponemos la bandera.
				ventana.ponerBandera(i, j);
			} else {
				// Si tenemos bandera, se la quitamos.
				ventana.quitarBandera(i, j);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
