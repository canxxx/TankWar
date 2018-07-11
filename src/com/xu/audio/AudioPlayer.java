package com.xu.audio;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
		
		File file ;
		AudioInputStream audioInput;
		public Clip clip;
		
		public  AudioPlayer(String msg){
			
		    file = new File(msg);
		    try {
				
		    	audioInput = AudioSystem.getAudioInputStream(file);
		        clip = AudioSystem.getClip();
		        clip.open(audioInput);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
}