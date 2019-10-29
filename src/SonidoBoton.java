
import java.applet.*;
import java.io.File;

public class SonidoBoton extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1394012196131936189L;
	
	
	public SonidoBoton(int i) {
	
		if(i==0) {
			File sound = new File("click.wav");
			try {
				AudioClip sonido = Applet.newAudioClip(sound.toURL());
				sonido.play();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			File sound = new File("explosion.wav");
			try {
				AudioClip sonido = Applet.newAudioClip(sound.toURL());
				sonido.play();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
}
