package input;

import java.util.HashSet;

import game.model.BackgroundMusic;
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
			GameMain.pauseOrResumeGame();
		}

		// ENTER = RESTART
		else if (event.getCode() == KeyCode.ENTER && player.isDead()) {
			Model.getContainer().clearAllData();
			GameMain.stopGame();

			SceneManager.gotoScoreView();
		}

		// UP ARROW = JUMP
		else if (event.getCode() == KeyCode.UP && Animations.getTimerAnimation().getStatus() != Status.PAUSED) {
			saveState = State.JUMPING;
			player.jump();
		}

		// SPACE = SHIELD
		else if (event.getCode() == KeyCode.SPACE && Animations.getTimerAnimation().getStatus() != Status.PAUSED) {
			player.makeShield();
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

		/* BELOW KEY IS A CHEAT FOR DEBUGING */
		// H = HEAL
		else if (event.getCode() == KeyCode.H) {
			player.increaseHp(123456789);
		}

		// S = SUICIDE
		else if (event.getCode() == KeyCode.S) {
			player.decreaseHp(987654321);
		}

		// B = BUFF ATK
		else if (event.getCode() == KeyCode.B) {
			if (player.getAtk() < 100) {
				player.setAtk(1234);
			} else {
				player.setAtk(10);
			}
		}

		// N = NO COOLDOWN
		else if (event.getCode() == KeyCode.N) {
			player.decreaseCooldown(100);
		}

		// G = GOTO BOSS
		else if (event.getCode() == KeyCode.G) {
			player.addDistance(50000);
			Updater.setDistance(50000);
		}

		keys.add(event.getCode());
	}

	public static void keyReleased(KeyEvent event) {
		if (SceneManager.isTrasitioning()) {
			return;
		}

		// NORMAL WALKING
		if (event.getCode() == KeyCode.DOWN && Animations.getTimerAnimation().getStatus() != Status.PAUSED) {
			player.getCanvas().setRotate(0);
			player.setState(saveState);
		}

		// STAND STILL
		else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
			player.setSpeedX(0);
		}

		// NORMAL STATE
		else if (event.getCode() == KeyCode.SPACE && Animations.getTimerAnimation().getStatus() != Status.PAUSED) {
			if (!player.isInjuring()) {
				player.setPowerState(PowerState.NORMAL);
			}
			for (Skill skill : Model.getContainer().getSkillList()) {
				if (skill.getIndex() == 4) {
					skill.getCanvas().setOpacity(0);
				}
			}
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
		} else {
			GameHandler.keyReleased(new KeyEvent(null, null, KeyEvent.KEY_RELEASED, null, "DOWN", KeyCode.DOWN, false,
					false, false, false));
		}

		// RIGHT ARROW = RIGHT WALK
		if (keys.contains(KeyCode.RIGHT) && Updater.isBossReady() && Updater.isBossStage()) {
			player.setDirection(Direction.RIGHT);
			player.setSpeedX(10);
		}

		// LEFT ARROW = LEFT WALK
		if (keys.contains(KeyCode.LEFT) && Updater.isBossReady() && Updater.isBossStage()) {
			player.setDirection(Direction.LEFT);
			player.setSpeedX(-10);
		}

		// USE SHIELD = SPEND MANA & GET IMMORTAL
		if (keys.contains(KeyCode.SPACE)) {
			player.useShield();
		} else {
			GameHandler.keyReleased(new KeyEvent(null, null, KeyEvent.KEY_RELEASED, null, "SPACE", KeyCode.SPACE, false,
					false, false, false));
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
