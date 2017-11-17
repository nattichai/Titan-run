package controller;

import java.util.Random;

import entity.map.Map;
import entity.obstacle.AirObstacle;
import entity.obstacle.GroundObstacle;
import entity.obstacle.HoleObstacle;
import entity.obstacle.Obstacle;
import entity.player.Player;
import entity.skill.Fireball;
import entity.skill.Meteor;
import entity.skill.Skill;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import main.Container;
import main.Main;
import property.PowerState;
import property.State;
import utility.Pair;

public class Controller {
	public static final int MIN_OBSTACLE_SPACE = 40;
	public static final int RANGE_OBSTACLE_SPACE = 20;

	private static State saveState;
	private static int tick = 0;
	private static int rnd = 0;

	public static void update() {
		randomObstacle();
		gravity();
		moveAll();
		checkCollision();
		removeAllDead();
	}

	private static void randomObstacle() {
		if (tick == 0) {
			rnd = new Random().nextInt(RANGE_OBSTACLE_SPACE) + MIN_OBSTACLE_SPACE;
		}
		tick++;
		if (tick > rnd) {
			tick = 0;
			int rnd = new Random().nextInt(3);
			if (rnd == 0) {
				GroundObstacle obstacle = new GroundObstacle(new Pair(1100, 0),
						new Pair(Obstacle.OBSTACLE_WIDTH, Main.SCREEN_HEIGHT));
				Container.getContainer().add(obstacle);
			} else if (rnd == 1) {
				AirObstacle obstacle = new AirObstacle(new Pair(1100, 0),
						new Pair(Obstacle.OBSTACLE_WIDTH, Main.SCREEN_HEIGHT));
				Container.getContainer().add(obstacle);
			} else if (rnd == 2) {
				HoleObstacle obstacle = new HoleObstacle(new Pair(1100, 0),
						new Pair(HoleObstacle.HOLE_WIDTH, Main.SCREEN_HEIGHT));
				Container.getContainer().add(obstacle);
			}
		}
	}

	private static void gravity() {
		for (Player player : Container.getContainer().getPlayerList()) {
			if (player.getState() == State.JUMPING)
				player.changeSpeed(0, Map.GRAVITY);
		}
		for (Skill skill : Container.getContainer().getSkillList()) {
			if (skill instanceof Meteor) {
				((Meteor) skill).changeSpeed(0, Map.GRAVITY);
			}
		}
	}

	private static void moveAll() {
		for (Map map : Container.getContainer().getMapList()) {
			map.move();
		}

		for (Obstacle obstacle : Container.getContainer().getObstacleList()) {
			obstacle.move();
		}

		for (Player player : Container.getContainer().getPlayerList()) {
			if (Listener.keys.contains(KeyCode.UP)) { // JUMP
				if (player.getState() == State.RUNNING) { // FIRST JUMP
					Listener.keys.remove(KeyCode.UP);
					player.setCurrentAnimation(8);
					player.draw();
					player.setSpeedY(-18);
					player.addJump(1 - player.getJump());
					player.setState(State.JUMPING);
					saveState = State.JUMPING;
				} else if (player.getState() == State.JUMPING && player.getJump() < Player.MAX_JUMP) { // OTHER JUMP
					Listener.keys.remove(KeyCode.UP);
					player.setSpeedY(-15);
					player.addJump(1);
				}
			}

			if (Listener.keys.contains(KeyCode.DOWN)) { // SLIDE
				if (player.getState() != State.JUMPING) { // NOT JUMPING THEN SLIDE
					player.slide();
				} else {
					player.setSpeedY(player.getSpeedY() + Player.MOVEDOWN_SPEED); // FAST MOVEDOWN
				}
			}

			if (!Listener.keys.contains(KeyCode.DOWN)) { // NOT SLIDING THEN RETURN TO SAVE STAGE
				player.getCanvas().setRotate(0);
				player.setState(saveState);
			}

			if (Listener.keys.contains(KeyCode.Q)) { // Q = FIREBALL (NORMAL ATTACK)
				if (player.getState() != State.SLIDING) { // NOT SLIDING
					if (Container.getContainer().getSkillList().size() < Player.LIMIT_NORMAL_ATTACK) { // NOT EXCEED
																										// LIMIT NUMBER
						Listener.keys.remove(KeyCode.Q);
						Fireball fireball = new Fireball(
								new Pair(Player.PLAYER_POSITON_X,
										player.getPosition().second + Player.PLAYER_HEIGHT / 2),
								new Pair(Fireball.SKILL_WIDTH, Fireball.SKILL_HEIGHT));
						Container.getContainer().add(fireball);
					}
				}
			}

			if (Listener.keys.contains(KeyCode.R)) { // R = METEOR (ULTIMATE)
				if (player.getState() != State.SLIDING) { // NOT SLIDING
					if (Container.getContainer().getSkillList().size() < Player.LIMIT_NORMAL_ATTACK) { // NOT EXCEED
																										// LIMIT NUMBER
						Listener.keys.remove(KeyCode.R);
						
						Timeline timerMeteor = new Timeline(new KeyFrame(Duration.millis(150), e -> {
							Meteor meteor = new Meteor(
									new Pair(Player.PLAYER_POSITON_X,
											player.getPosition().second + Player.PLAYER_HEIGHT / 2),
									new Pair(Meteor.SKILL_WIDTH, Meteor.SKILL_HEIGHT));
							Container.getContainer().add(meteor);
						}));
						timerMeteor.setCycleCount(7);
						timerMeteor.play();
					}
				}
			}

			player.move();
			player.decreaseHp(Map.PASSIVE_DAMAGE); // LOST HP FROM MOVING
			player.addScore(Map.PASSIVE_SCORE); // ADD SCORE FROM MOVING
		}

		for (Skill skill : Container.getContainer().getSkillList()) {
			skill.move();
		}

	}

	public static void checkCollision() {
		for (Player player : Container.getContainer().getPlayerList()) {
			if (player.getPowerState() == PowerState.NORMAL) { // NOT IMMORTAL
				for (Obstacle obstacle : Container.getContainer().getObstacleList()) {
					if (obstacle.isCollision(player.getPosition(), player.getState())) { // CHECK COLLISION
						if (obstacle instanceof HoleObstacle) // FALL IN A HOLE
							player.die();
						else
							player.decreaseHp(Obstacle.OBSTACLE_DAMAGE); // DAMAGED BY HITTING
					}
				}
			}
		}
	}

	public static void removeAllDead() {
		Container.getContainer().getObstacleList().removeIf(o -> o.isDead());
		Container.getContainer().getSkillList().removeIf(f -> f.isDead());
		Container.getContainer().getPlayerList().removeIf(p -> p.isDead());
	}

	public static void animate() {
		animateAll();
	}

	private static void animateAll() {
		for (Player player : Container.getContainer().getPlayerList()) {
			player.changeImage();
		}

		for (Skill skill : Container.getContainer().getSkillList()) {
			skill.changeImage();
		}
	}

}
