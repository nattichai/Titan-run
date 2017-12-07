package game.model;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BackgroundMusic {
	private static final Media mainMenuBGM = new Media(
			ClassLoader.getSystemResource("sounds/songs/05_Stage Select.mp3").toString());
	private static final Media normalStageBGM = new Media(
			ClassLoader.getSystemResource("sounds/songs/07_Opening Stage Zero.mp3").toString());
	private static final Media bossStageBGM = new Media(
			ClassLoader.getSystemResource("sounds/songs/26_Boss.mp3").toString());
	private static final AudioClip warningSiren = new AudioClip(
			ClassLoader.getSystemResource("sounds/otherfx/Warning.wav").toString());
	private static MediaPlayer mediaPlayer = new MediaPlayer(normalStageBGM);
	
	public static void playMainMenuBGM() {
		startMusic(mainMenuBGM);
	}

	public static void playNormalStageBGM() {
		startMusic(normalStageBGM);
	}

	public static void playBossStageBGM() {
		startMusic(bossStageBGM);
	}

	public static void playWarningSiren() {
		warningSiren.setCycleCount(3);
		warningSiren.play();
	}
	
	public static void startMusic(Media media) {
		mediaPlayer.stop();
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(Integer.MAX_VALUE);
		mediaPlayer.play();
	}
	
	public static void pauseMusic() {
		mediaPlayer.pause();
	}

	public static void continueMusic() {
		mediaPlayer.play();
	}

	public static boolean isMainMenuBGMPlaying() {
		if (mediaPlayer == null) {
			return false;
		}
		return mediaPlayer.getMedia().equals(mainMenuBGM);
	}
}
