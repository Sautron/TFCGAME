//Audio reprodution and management Class 
//Classe que gere e reproduz os ficheiros de audio
package Audio;

import javax.sound.sampled.*;

public class AudioPlayer {
	
	private Clip clip;
	
	//Class constructor  
	//Construtor da classe	
	public AudioPlayer(String s) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().
								   getResourceAsStream(s));
			
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Starts the audio
	//Começa o audio
	public void play() {
		if(clip == null){
			return;
		}
		
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	//Stops the audio
	//Para a reprodução do audio
	public void stop() {
		if(clip.isRunning()){
			clip.stop();
		}
	}
	
	//Stops the audio and closes audio player
	//Para o audio e fecha o audio-player
	public void close() {
		stop();
		clip.close();
	}	
}