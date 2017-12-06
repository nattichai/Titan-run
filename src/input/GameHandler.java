package input;

import java.util.HashSet;

import game.model.Model;
import game.model.Skill;
import game.model.character.Player;
import game.property.Direction;
import game.property.PowerState;
import game.property.State;
import game.updater.Animations;
import game.updater.Updater;
import javafx.animation.Animation.Status;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import scene.GameMain;
import scene.SceneManager;

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
				GameMain.continueGame();
			} else {
				GameMain.pauseGame();
			}
		}

		// ENTER = RESTART
		else if (event.getCode() == KeyCode.ENTER && player.isDead()) {
			Model.getContainer().clearAllData();
			GameMain.stopGame();

			SceneManager.gotoScoreView();
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
			player.increaseHp(124356789);
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
			player.decreaseHp(987654321);
		}

		keys.add(event.getCode());
	}

	public static void keyReleased(KeyEvent event) {
		if (SceneManager.isTrasitioning()) {
			keys.clear();
			return;
		}

		// NORMAL WALKING
		if (event.getCode() == KeyCode.DOWN) {
			player.getCanvas().setRotate(0);
			player.setState(saveState);
		}

		// STAND STILL
		else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
			player.setSpeedX(0);
		}

		// NORMAL STATE
		else if (event.getCode() == KeyCode.SPACE) {
			player.setPowerState(PowerState.NORMAL);
			for (Skill skill : Model.getContainer().getSkillList()) {
				if (skill.getIndex() == 4) {
					skill.setPositionX(-1000); // delete shield
				}
			}
			Model.getContainer().getSkillList().removeIf(e -> e.isDead());
		}

		keys.remove(event.getCode());
	}

	public static void keyHeld() {
		if (SceneManager.isTrasitioning()) {
			return;
		}

		// DOWN ARROW = SLIDE & FAST FALL
		if (keys.contains(KeyCode.DOWN)) {
			player.goDown();
		}

		// RIGHT ARROW = RIGHT WALK
		if (keys.contains(KeyCode.RIGHT) && Updater.isBossStage()) {
			player.setDirection(Direction.RIGHT);
			player.setSpeedX(10);
		}

		// LEFT ARROW = LEFT WALK
		if (keys.contains(KeyCode.LEFT) && Updater.isBossStage()) {
			player.setDirection(Direction.LEFT);
			player.setSpeedX(-10);
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
