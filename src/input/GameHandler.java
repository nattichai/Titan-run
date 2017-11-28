package input;

import java.util.HashSet;

import entity.characters.Player;
import game.animations.Animations;
import game.model.Model;
import game.property.PowerState;
import game.property.State;
import javafx.animation.Animation.Status;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import window.SceneManager;

public class GameHandler {
	private static HashSet<KeyCode> keys = new HashSet<>();
	private static State saveState;
	private static Player player;

	public static void keyPressed(KeyEvent event) {
		if (keys.contains(event.getCode()) || SceneManager.isTrasitioning()) {
			return;
		}

		// P = PAUSE & UNPAUSE
		if (event.getCode() == KeyCode.P) {
			if (Animations.getTimerAnimation().getStatus() == Status.PAUSED) {
				SceneManager.continueGame();
			} else {
				SceneManager.pauseGame();
			}
		}

		// ENTER = RESTART
		else if (event.getCode() == KeyCode.ENTER && Model.getContainer().getPlayerPane().getChildren().isEmpty()) {
			Model.getContainer().clearAllData();
			SceneManager.stopGame();
			SceneManager.gotoMainMenu();
		}

		// UP ARROW = JUMP
		else if (event.getCode() == KeyCode.UP) {
			saveState = State.JUMPING;
			player.jump();
		}

		// SPACE = SHIELD
		else if (event.getCode() == KeyCode.SPACE) {
			player.makeShield();
		}

		// H = HEAL
		else if (event.getCode() == KeyCode.H) {
			player.setHp(player.getMaxHp());
		}

		// W = LIGHTNING
		else if (event.getCode() == KeyCode.W && Animations.getTimerAnimation().getStatus() != Status.PAUSED) {
			player.useLightning();
		}

		// E = THUNDERBOLT
		else if (event.getCode() == KeyCode.E && Animations.getTimerAnimation().getStatus() != Status.PAUSED) {
			player.useThunderbolt();
		}

		// R = SLASHY
		else if (event.getCode() == KeyCode.R && Animations.getTimerAnimation().getStatus() != Status.PAUSED) {
			player.useSlashy();
		}

		// S = SUICIDE
		else if (event.getCode() == KeyCode.S) {
			player.die();
		}

		keys.add(event.getCode());
	}

	public static void keyReleased(KeyEvent event) {
		// NORMAL WALKING
		if (event.getCode() == KeyCode.DOWN) {
			player.getCanvas().setRotate(0);
			player.setState(saveState);
		}

		// NORMAL STATE
		else if (event.getCode() == KeyCode.SPACE) {
			player.setPowerState(PowerState.NORMAL);
		}

		keys.remove(event.getCode());
	}

	public static void keyHeld() {
		// DOWN ARROW = SLIDE & FAST FALL
		if (keys.contains(KeyCode.DOWN)) {
			player.goDown();
		}

		// USE SHIELD = SPEND MANA & GET IMMORTAL
		if (keys.contains(KeyCode.SPACE)) {
			player.useShield();
		}

		// Q = FIREBALL (NORMAL ATTACK)
		if (keys.contains(KeyCode.Q)) {
			player.useFireball();
		}
	}

	public static HashSet<KeyCode> getKeys() {
		return keys;
	}

	public static void setPlayer() {
		player = Model.getContainer().getPlayer();
	}

}
