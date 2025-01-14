
import java.applet.*;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SonidoBoton extends Applet {

	private static final long serialVersionUID = -1394012196131936189L;
	/**
	 * Constructor de la clase para sacar el audio.
	 * @param i 0 = Casilla sin mina. 1 = Casilla con mina.
	 * @author Tomas Macias Castela
	 */
	public SonidoBoton(int i) {

		if (i == 0) {
			URL sound = getClass().getResource("/material/click.wav");
			System.out.println(sound.getPath());
			try {
				Clip sonido = AudioSystem.getClip();
				sonido.open(AudioSystem.getAudioInputStream(sound));
				sonido.start();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			URL sound = getClass().getResource("/material/explosion.wav");
			try {
				Clip sonido = AudioSystem.getClip();
				sonido.open(AudioSystem.getAudioInputStream(sound));
				sonido.start();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
