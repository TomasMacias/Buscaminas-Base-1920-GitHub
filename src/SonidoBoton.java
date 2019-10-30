
import java.applet.*;
import java.io.File;

public class SonidoBoton extends Applet {

	private static final long serialVersionUID = -1394012196131936189L;
	/**
	 * Constructor de la clase para sacar el audio.
	 * @param i 0 = Casilla sin mina. 1 = Casilla con mina.
	 * @author Tomas Macias Castela
	 */
	public SonidoBoton(int i) {

		if (i == 0) {
			File sound = new File("sonido//click.wav");
			try {
				AudioClip sonido = Applet.newAudioClip(sound.toURL());
				sonido.play();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			File sound = new File("sonido//explosion.wav");
			try {
				AudioClip sonido = Applet.newAudioClip(sound.toURL());
				sonido.play();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
