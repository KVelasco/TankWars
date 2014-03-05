package artillery;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private AudioInputStream audio;
	private Clip clip;
	private URL url;

	public Sound(String ref) {
		url = Game.class.getClassLoader().getResource(ref);
	}

	public void play(boolean loop) {
		try {
			audio = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audio);

			if (loop) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			clip.start();
			if (!loop) {
				if (clip.getFramePosition() == clip.getFrameLength()) {
					clip.close();
				}
			}

		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		clip.stop();
	}

}
