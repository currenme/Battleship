//Written by Matt Carey - USC CSCI201L Spring 2015

package currenme_CSCI201_Assignment5;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.*;


public class SoundLibrary {
	private static Map<String, File> soundMap;
	static{
		soundMap = new HashMap<String,File>();
		soundMap.put("Resources/cannon.wav", new File("Resources/cannon.wav"));
		soundMap.put("Resources/explode.wav", new File("Resources/explode.wav"));
		soundMap.put("Resources/sinking.wav", new File("Resources/sinking.wav"));
		soundMap.put("Resources/splash.wav", new File("Resources/splash.wav"));
	}

	public static void playSound(String sound) {
		File toPlay = soundMap.get(sound);
		if(toPlay == null) {
			toPlay = new File(sound);
			soundMap.put(sound, toPlay);
		}
		
		try {
		AudioInputStream stream = AudioSystem.getAudioInputStream(toPlay);
		AudioFormat format = stream.getFormat();
		SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class,format,(int) (stream.getFrameLength() * format.getFrameSize()));
		SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
		
		line.open(stream.getFormat());
		line.start();
		int num_read = 0;
		byte[] buf = new byte[line.getBufferSize()];
		while ((num_read = stream.read(buf, 0, buf.length)) >= 0)
		{
			int offset = 0;
			
			while (offset < num_read)
			{
				offset += line.write(buf, offset, num_read - offset);
			}
		}
		line.drain();
		line.stop();
		} catch(IOException | UnsupportedAudioFileException | LineUnavailableException ioe) {
			System.out.println("Audio file is invalid!");
		}
	}
}
