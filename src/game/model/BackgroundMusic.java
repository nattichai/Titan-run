package game.model;

import javafx.scene.media.AudioClip;

public class BackgroundMusic {
	private static AudioClip normalStageBGM = new AudioClip(
			ClassLoader.getSystemResource("sounds/songs/07_Opening Stage Zero.mp3").toString());;;
	private static AudioClip bossStageBGM = new AudioClip(
			ClassLoader.getSystemResource("sounds/songs/26_Boss.mp3").toString());;
	private static AudioClip warningSiren = new AudioClip(
			ClassLoader.getSystemResource("sounds/otherfx/Warning.wav").toString());;
	private static AudioClip mainMenuBGM = new AudioClip(
			ClassLoader.getSystemResource("sounds/songs/05_Stage Select.mp3").toString());;

	public static AudioClip getNormalStageBGM() {
		return normalStageBGM;
	}

	public static AudioClip getBossStageBGM() {
		return bossStageBGM;
	}

	public static AudioClip getWarningSiren() {
		return warningSiren;
	}

	public static AudioClip getMainMenuBGM() {
		return mainMenuBGM;
	}

	public static void playNormalStageBGM() {
		normalStageBGM.setCycleCount(AudioClip.INDEFINITE);
		normalStageBGM.play();
	}

	public static void stopNormalStageBGM() {
		normalStageBGM.stop();
	}

	public static void playBossStageBGM() {
		bossStageBGM.setCycleCount(AudioClip.INDEFINITE);
		bossStageBGM.play();
	}

	public static void stopBossStageBGM() {
		bossStageBGM.stop();
	}

	public static void playWarningSiren() {
		warningSiren.setCycleCount(3);
		warningSiren.play();
	}

	public static void stopWarningSiren() {
		warningSiren.stop();
	}

	public static void playMainMenuBGM() {
		mainMenuBGM.setCycleCount(AudioClip.INDEFINITE);
		mainMenuBGM.play();
	}

	public static void stopMain​MenuBGM() {
		mainMenuBGM.stop();
	}
}
