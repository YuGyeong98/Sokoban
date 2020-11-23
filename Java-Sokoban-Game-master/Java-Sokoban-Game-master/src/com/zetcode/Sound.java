package com.zetcode;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	private InputStream in;
	private Clip clip;
	private int counter;

	public Sound(String soundFile, int counter) {
		try {
			this.counter = counter;
			this.in = new FileInputStream(soundFile);
			this.clip = AudioSystem.getClip();
			InputStream buffered = new BufferedInputStream(in);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(buffered);
			clip.open(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void play() {
		if (counter == -1) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			clip.loop(counter);
		}
	}
}
