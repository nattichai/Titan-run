package input;

import java.util.HashSet;

import entity.characters.Player;
import game.GameMain;
import game.animations.Animations;
import game.model.Model;
import game.property.PowerState;
import game.property.State;
import game.updater.Updater;
import javafx.animation.Animation.Status;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import window.SceneManager;

public class GameHandler {
	private static HashSet<KeyCode> keys = new HashSet<>();
	private static State saveState;

	public static void keyPressed(KeyEvent event) {
		if (keys.contains(event.getCode())) {
			return;
		}

		// P = PAUSE & UNPAUSE
		if (event.getCode() == KeyCode.P) {
			if (Animations.getTimerAnimation().getStatus() == Status.PAUSED) {
				Updater.getTimerUpdate().play();
				Animations.getTimerAnimation().play();
			} else {
				Updater.getTimerUpdate().pause();
				Animations.getTimerAnimation().pause();
			}
		}

		// ENTER = RESTART
		else if (event.getCode() == KeyCode.ENTER && Model.getContainer().getPlayerList().isEmpty()) {
			Model.getContainer().clearAllData();
			Updater.getTimerUpdate().stop();
			Animations.getTimerAnimation().stop();
			GameMain.newGame();
		}

		// RIGHT ARROW = RUNNING
		else if (event.getCode() == KeyCode.RIGHT) {
			setSpeed(20);
		}

		// UP ARROW = JUMP
		else if (event.getCode() == KeyCode.UP) {
			saveState = State.JUMPING;
			for (Player player : Model.getContainer().getPlayerList()) {
				player.jump();
			}
		}

		// SPACE = SHIELD
		else if (event.getCode() == KeyCode.SPACE) {
			for (Player player : Model.getContainer().getPlayerList()) {
				player.makeShield();
			}
		}

		// H = HEAL
		else if (event.getCode() == KeyCode.H) {
			for (Player player : Model.getContainer().getPlayerList()) {
				player.setHp(player.getMaxHp());
			}
		}

		// W = LIGHTNING
		else if (event.getCode() == KeyCode.W) {
			for (Player player : Model.getContainer().getPlayerList()) {
				player.useLightning();
			}
		}

		// E = THUNDERBOLT
		else if (event.getCode() == KeyCode.E) {
			for (Player player : Model.getContainer().getPlayerList()) {
				player.useThunderbolt();
			}
		}

		// R = SLASHY
		else if (event.getCode() == KeyCode.R) {
			for (Player player : Model.getContainer().getPlayerList()) {
				player.useSlashy();
			}
		}

		keys.add(event.getCode());
	}

	public static void keyReleased(KeyEvent event) {
		// NORMAL SPEED
		if (event.getCode() == KeyCode.RIGHT) {
			setSpeed(10);
		}

		// NORMAL WALKING
		else if (event.getCode() == KeyCode.DOWN) {
			for (Player player : Model.getContainer().getPlayerList()) {
				player.getCanvas().setRotate(0);
				player.setState(saveState);
			}
		}

		// NORMAL STATE
		else if (event.getCode() == KeyCode.SPACE) {
			Model.getContainer().getPlayerList().forEach(p -> p.setPowerState(PowerState.NORMAL));
		}

		keys.remove(event.getCode());
	}

	public static void keyHeld() {
		// DOWN ARROW = SLIDE & FAST FALL
		if (keys.contains(KeyCode.DOWN)) {
			for (Player player : Model.getContainer().getPlayerList()) {
				player.goDown();
			}
		}

		// USE SHIELD = SPEND MANA & GET IMMORTAL
		if (keys.contains(KeyCode.SPACE)) {
			for (Player player : Model.getContainer().getPlayerList()) {
				player.useShield();
			}
		}

		// Q = FIREBALL (NORMAL ATTACK)
		if (keys.contains(KeyCode.Q)) {
			for (Player player : Model.getContainer().getPlayerList()) {
				player.useFireball();
			}
		}
	}

	public static void setSpeed(double speed) {
		SceneManager.SPEED = speed;
		Model.getContainer().getMapList().forEach(e -> e.setSpeedX(-speed));
		Model.getContainer().getItemList().forEach(e -> e.setSpeedX(-speed));
		Model.getContainer().getObstacleList().forEach(e -> e.setSpeedX(-speed));
	}

	public static HashSet<KeyCode> getKeys() {
		return keys;
	}

	public static void setKeys(HashSet<KeyCode> keys) {
		GameHandler.keys = keys;
	}
}
