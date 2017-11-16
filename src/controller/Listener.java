package controller;

import java.util.*;

import javafx.animation.Animation.Status;
import javafx.scene.input.*;
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
	}
	
	public static void keyReleased(KeyEvent event) {
		keys.remove(event.getCode());
	}
}
