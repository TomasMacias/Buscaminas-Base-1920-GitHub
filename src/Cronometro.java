import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class Cronometro extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6032396181329085798L;
	JLabel contador;
	JPanel azul;
	Thread hilo = null;
	DecimalFormat df = new DecimalFormat("#");
	String tiempoM, tiempoS;

	double tiempoTranscurrido;
	double tiempoOriginal;

	Border border;
	/**
	 * {@link #m} Minutos.
	 * {@link #s} Segundos. 
	 */
	int m = 0, s = 0;
	/** {@link #temporizador}
	 * (m<=9?"0":"")+m+":"+(s<=9?"0":"")+s Significado: si m(Minutos es menor o
	 * igual que 9 =&gt (m<=9?"0":"") se remplazará por "0". sino, se reemplazará
	 * por una cadena vacia. De esta manera si "m" es menor o igual que 9 valdrá "0"
	 * 
	 */
	String temporizador = (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s;
	boolean cronoActivo = false;

	public Cronometro() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);

		azul = new JPanel();
		azul.setBackground(Color.WHITE);
		
		// Contador.
		contador = new JLabel();
		contador.setFont(new Font("Arial", Font.BOLD, 20));
		// Escribir el JLabel centrado.
		contador.setHorizontalAlignment(SwingUtilities.CENTER);
		contador.setText(temporizador);
		azul.add(contador);

		this.add(azul);

		// Opciones a la hora de elegir el boton seleccionado.

	}

	/**
	 * Metodo donde recorremos el hilo de cronometro.
	 */
	@Override
	public void run() {
		tiempoOriginal = System.nanoTime();

		while (cronoActivo) {
			calcularTiempoTranscurrido();
			actualizarPantalla();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * Metodo para comenzar el cronometro.
	 */
	public void comenzar() {
		if (hilo == null) {
			cronoActivo = true;
			hilo = new Thread(this);
			hilo.start();
		} else {
			resetear();
		}
	}

	/**
	 * Metodo para parar el cronometro.
	 */
	public void parar() {
		if (hilo != null) {
			cronoActivo = false;
			hilo = null;
		}
	}

	/**
	 * Metodo para resetear el cronometro.
	 */
	public void resetear() {
		if (hilo == null) {
			contador.setText(temporizador);
		} else {
			parar();
			comenzar();
		}
	}

	/**
	 * Metodo para calcular el tiempo que ha transcurrido.
	 */
	private void calcularTiempoTranscurrido() {
		tiempoTranscurrido = (System.nanoTime() - tiempoOriginal) / 1_000_000_000;
	}

	/**
	 * Metodo para actualizar los segundos en el cronometro.
	 * 
	 */
	private void actualizarPantalla() {
		m = (int) Math.floor(tiempoTranscurrido / 60);
		s = (int) Math.floor(tiempoTranscurrido % 60);
		if (tiempoTranscurrido / 60 < 10) {
			tiempoM = "0" + m + ":";
		} else {
			tiempoM = m + ":";
		}
		if (tiempoTranscurrido % 60 < 10) {
			tiempoS = "0" + s;
		} else {
			tiempoS = String.valueOf(s);
		}
		contador.setText(tiempoM + tiempoS);
	}
}
