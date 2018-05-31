package kr.coding.team.util;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SoundUtil {

	public void bgmPlay(File file){
	    try{
	        final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
	        clip.addLineListener(new LineListener(){
	            @Override
	            public void update(LineEvent event){
	                if (event.getType() == LineEvent.Type.STOP)
	                    clip.close();
	            }
	        });

	        clip.open(AudioSystem.getAudioInputStream(file));
	        clip.start();
	    }
	    catch (Exception exc){
	        exc.printStackTrace(System.out);
	    }
	}
}
