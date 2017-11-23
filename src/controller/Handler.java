package controller;

import java.util.HashSet;

import dataStorge.Container;
import dataStorge.PlayerData;
import entity.characters.monster.Monster;
import entity.characters.player.Player;
import entity.skill.Fireball;
import entity.skill.Lightning;
import entity.skill.Meteor;
import entity.skill.Shield;
import entity.skill.Slashy;
import entity.skill.Thunderbolt;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import main.Main;
import property.PowerState;
import property.State;

public class Handler {
	private static HashSet<KeyCode> keys = new HashSet<>();
	private static State saveState;
	private static boolean hasShield;

	public static void keyPressed(KeyEvent event) {
		keys.add(event.getCode());

		if (keys.contains(KeyCode.P)) { // PAUSE
			if (Main.getTimerAnimate().getStatus() == Status.PAUSED) {
				Main.getTimerUpdate().play();
				Main.getTimerAnimate().play();
			} else {
				Main.getTimerUpdate().pause();
				Main.getTimerAnimate().pause();
			}
		}

		if (keys.contains(KeyCode.ENTER) && Container.getContainer().getPlayerList().isEmpty()) { // RETRY
			Container.initialize();
		}
	}

	public static void keyReleased(KeyEvent event) {
		keys.remove(event.getCode());
		if (event.getCode() == KeyCode.SPACE) {
			hasShield = false;
			Container.getContainer().getPlayerList().forEach(p -> p.setPowerState(PowerState.NORMAL));
		}
	}

	public static void handler() {
//		 if (keys.contains(KeyCode.RIGHT)) { // RIGHT = RUNNING
//		 Container.getContainer().getMapList().forEach(e -> e.setSpeedX(-20));
//		 Container.getContainer().getItemList().forEach(e -> e.setSpeedX(-20));
//		 Container.getContainer().getObstacleList().forEach(e -> e.setSpeedX(-20));
//		 } else {
//		 Container.getContainer().getMapList().forEach(e -> e.setSpeedX(-10));
//		 Container.getContainer().getItemList().forEach(e -> e.setSpeedX(-10));
//		 Container.getContainer().getObstacleList().forEach(e -> e.setSpeedX(-10));
//		 }

		if (keys.contains(KeyCode.H)) { // H = HEAL
			for (Player player : Container.getContainer().getPlayerList()) {
				player.setHp(player.getMaxHp());
			}
		}

		if (keys.contains(KeyCode.UP)) { // UP = JUMP
			for (Player player : Container.getContainer().getPlayerList()) {
				if (player.getState() == State.RUNNING) { // FIRST JUMP
					keys.remove(KeyCode.UP);
					player.setCurrentAnimation(8);
					player.draw();
					player.setSpeedY(-18);
					player.addJump(1 - player.getJump());
					player.setState(State.JUMPING);
					saveState = State.JUMPING;
				} else if (player.getState() == State.JUMPING && player.getJump() < Player.MAX_JUMP) { // OTHER JUMP
					keys.remove(KeyCode.UP);
					player.setSpeedY(-15);
					player.addJump(1);
				}
			}
		}

		if (keys.contains(KeyCode.DOWN)) { // DOWN = SLIDE & FAST FALL
			for (Player player : Container.getContainer().getPlayerList()) {
				if (player.getState() != State.JUMPING) { // NOT JUMPING THEN SLIDE
					player.slide();
				} else {
					player.setSpeedY(player.getSpeedY() + Player.MOVEDOWN_SPEED); // FAST MOVEDOWN
				}
			}
		}

		if (!keys.contains(KeyCode.DOWN)) { // NOT SLIDING THEN RETURN TO SAVE STAGE
			for (Player player : Container.getContainer().getPlayerList()) {
				player.getCanvas().setRotate(0);
				player.setState(saveState);
			}
		}

		if (keys.contains(KeyCode.SPACE)) { // SPACE = SHIELD (USE MANA)
			for (Player player : Container.getContainer().getPlayerList()) {
				if (hasShield) {
					player.addMana(-0.5);
					player.setPowerState(PowerState.IMMORTAL);
					if (player.getMana() <= 0) {
						keyReleased(new KeyEvent(null, null, KeyEvent.KEY_RELEASED, null, "SPACE", KeyCode.SPACE, false, false, false, false));
					}
				} else if (player.getMana() >= 0.5) {
					hasShield = true;
					Shield shield = new Shield(player.getPositionX() - 150, player.getPositionY() - 50, Shield.SKILL_WIDTH,
							Shield.SKILL_HEIGHT);
					shield.setOwner(player);
					Container.getContainer().add(shield);
				}
			}
		}

		if (keys.contains(KeyCode.Q)) { // Q = FIREBALL (NORMAL ATTACK)
			for (Player player : Container.getContainer().getPlayerList()) {
				if (player.getState() != State.SLIDING) { // NOT SLIDING
					if (PlayerData.getCooldown(0) <= 0) {
						PlayerData.resetCooldown(0);
						Fireball fireball = new Fireball(player.getPositionX(),
								player.getPositionY() + player.getHeight() / 2, Fireball.SKILL_WIDTH,
								Fireball.SKILL_HEIGHT);
						fireball.setOwner(player);
						Container.getContainer().add(fireball);
					}
				}
			}
		}

		if (keys.contains(KeyCode.W)) { // W = LIGHTNING
			for (Player player : Container.getContainer().getPlayerList()) {
				if (player.getState() != State.SLIDING) { // NOT SLIDING
					if (PlayerData.getCooldown(1) <= 0) {
						PlayerData.resetCooldown(1);
						Timeline timerLightning = new Timeline(new KeyFrame(Duration.millis(100), e -> {
							Lightning lightning = new Lightning(nearestMonsterPosition(), 0, Lightning.SKILL_WIDTH,
									Lightning.SKILL_HEIGHT);
							lightning.setOwner(player);
							Container.getContainer().add(lightning);
						}));
						timerLightning.setCycleCount(10);
						timerLightning.play();
					}
				}
			}
		}

		if (keys.contains(KeyCode.E)) { // E = THUNDERBOLT
			for (Player player : Container.getContainer().getPlayerList()) {
				if (player.getState() != State.SLIDING) { // NOT SLIDING
					if (PlayerData.getCooldown(2) <= 0) {
						PlayerData.resetCooldown(2);
						Timeline timerThunderbolt = new Timeline(new KeyFrame(Duration.millis(100), e -> {
							Thunderbolt thunderbolt = new Thunderbolt(0, 0, Thunderbolt.SKILL_WIDTH,
									Thunderbolt.SKILL_HEIGHT);
							thunderbolt.setOwner(player);
							Container.getContainer().add(thunderbolt);
						}));
						timerThunderbolt.setCycleCount(1);
						timerThunderbolt.play();
					}
				}
			}
		}

//		if (keys.contains(KeyCode.R)) { // R = METEOR (ULTIMATE)
//			for (Player player : Container.getContainer().getPlayerList()) {
//				if (player.getState() != State.SLIDING) { // NOT SLIDING
//					if (PlayerData.getCooldown(3) <= 0) {
//						PlayerData.resetCooldown(3);
//						Timeline timerMeteor = new Timeline(new KeyFrame(Duration.millis(150), e -> {
//							Meteor meteor = new Meteor(player.getPositionX(), -200, Meteor.SKILL_WIDTH,
//									Meteor.SKILL_HEIGHT);
//							meteor.setOwner(player);
//							Container.getContainer().add(meteor);
//						}));
//						timerMeteor.setCycleCount(1);
//						timerMeteor.play();
//					}
//				}
//			}
//		}

		if (keys.contains(KeyCode.R)) { // 1 = SLASHY
			for (Player player : Container.getContainer().getPlayerList()) {
				if (player.getState() != State.SLIDING) { // NOT SLIDING
					if (PlayerData.getCooldown(3) <= 0) {
						PlayerData.resetCooldown(3);
						Timeline timerSlashy = new Timeline(new KeyFrame(Duration.millis(200), e -> {
							Slashy slashy = new Slashy(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
							slashy.setOwner(player);
							Container.getContainer().add(slashy);
						}));
						timerSlashy.setCycleCount(1);
						timerSlashy.play();
					}
				}
			}
		}
	}

	public static double nearestMonsterPosition() {
		double pos = 800;
		for (Monster monster : Container.getContainer().getMonsterList()) {
			pos = Math.min(pos, monster.getPositionX() + 50);
		}
		return pos;
	}

	public static HashSet<KeyCode> getKeys() {
		return keys;
	}

	public static void setKeys(HashSet<KeyCode> keys) {
		Handler.keys = keys;
	}
}
