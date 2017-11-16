package controller;

import entity.map.Map;
import entity.player.Player;
import javafx.animation.Animation.Status;
import javafx.scene.input.KeyCode;
import main.Container;
import main.Main;
import property.State;

public class Controller {
	
	static State saveState;
	
	public static void update() {
		gravity();
		moveAll();
	}
	
	private static void gravity() {
		for (Player player : Container.getContainer().getPlayerList()) {
			if (player.getState() == State.JUMPING1 || player.getState() == State.JUMPING2)
				player.changeSpeed(Map.GRAVITY);
		}
	}

	private static void moveAll() {
		for (Map map : Container.getContainer().getMapList()) {
			map.moveX();
		}
		
		for (Player player : Container.getContainer().getPlayerList()) {
			
			player.decreaseHp(0.01);
			
			if (Listener.keys.contains(KeyCode.UP)) {		//JUMP
				if (player.getState() == State.RUNNING) {
					Listener.keys.remove(KeyCode.UP);
					player.setCurrentAnimation(8);
					player.draw();
					player.setYSpeed(-18);
					player.setState(State.JUMPING1);
					saveState = State.JUMPING1;
				} else if (player.getState() == State.JUMPING1) {
					player.setYSpeed(-15);
					player.setState(State.JUMPING2);
					saveState = State.JUMPING2;
				}
			} else if (Listener.keys.contains(KeyCode.DOWN) && player.getState() != State.JUMPING1 && player.getState() != State.JUMPING2) {
				player.slide();
			}
			else if (!Listener.keys.contains(KeyCode.DOWN)){
				player.getCanvas().setRotate(0);
				player.getCanvas().setTranslateX(player.getPosition().first);
				player.getCanvas().setTranslateY(player.getPosition().second);
				player.setState(saveState);
			}
			player.moveY();
		}
	}
	
	public static void animate() {
		animateAll();
	}
	
	private static void animateAll() {
		for (Player player : Container.getContainer().getPlayerList()) {
			player.changeImage();
		}
	}
}
