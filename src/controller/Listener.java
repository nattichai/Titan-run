package controller;

import java.util.HashSet;

import javafx.animation.Animation.Status;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.Container;
import main.Main;

public class Listener {
	static HashSet<KeyCode> keys = new HashSet<>();
	
	public static void keyPressed(KeyEvent event) {
		keys.add(event.getCode());
		
		if (keys.contains(KeyCode.SPACE)) {		//PAUSE
			if (Main.getTimerAnimate().getStatus() == Status.PAUSED) {
				Main.getTimerUpdate().play();
				Main.getTimerAnimate().play();
			} else {
				Main.getTimerUpdate().pause();
				Main.getTimerAnimate().pause();
			}
		}
		
		if (keys.contains(KeyCode.ENTER) && Container.getContainer().getPlayerList().isEmpty()) {		//RETRY
			Container.initialize();
		}
	}
	
	public static void keyReleased(KeyEvent event) {
		keys.remove(event.getCode());
	}
}
