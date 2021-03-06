package com.viduan.main;

import java.io.*;
import javax.sound.sampled.*;

public class Sound {

	public static class Clips {
		public Clip[] clips;
		private int P;
		private int count;
		
		
		public Clips(byte[] buffer, int count) throws LineUnavailableException ,IOException,UnsupportedAudioFileException{
			if(buffer == null)
				return;
			
			clips = new Clip[count];
			this.count = count;
			
			for(int i = 0; i < count;i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
				
			}
		}
		 public void play() {
			 if(clips == null) return;
			 clips[P].stop();
			 clips[P].setFramePosition(0);
			 clips[P].start();
			 P++;
			 if(P >= count) P = 0;
			 
				 
		 }
		
		 public void loop() {
			 if(clips == null) return;
			 clips[P].loop(300);
		 }
		
	}
	
	public static Clips music = load("/music.wav",1);
	public static Clips hitEffect = load("/hurt.wav",1);
	public static Clips soundShoot = load("/soundShoot.wav",1);
	
	private static Clips load(String name, int count) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while((read = dis.read(buffer)) >= 0) {
				baos.write(buffer,0,read);
				
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data,count);
		}catch(Exception e) {
			try {
				return new Clips(null,0);
			}catch(Exception ee) {
				return null;
			}
		}
	}
	
	
}
