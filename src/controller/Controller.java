package controller;

import java.util.Random;

import entity.characters.monster.Monster;
import entity.characters.player.Player;
import entity.item.HealthPotion;
import entity.item.Item;
import entity.item.Jelly;
import entity.map.Map;
import entity.obstacle.AirObstacle;
import entity.obstacle.GroundObstacle;
import entity.obstacle.HoleObstacle;
import entity.obstacle.Obstacle;
import entity.skill.Fireball;
import entity.skill.Lightning;
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

public class Controller {
	public static final int MIN_OBSTACLE_SPACE = 100;
	public static final int RANGE_OBSTACLE_SPACE = 20;

	private static State saveState;
	private static int tick = 0;
	private static int rnd = MIN_OBSTACLE_SPACE;
	private static int height = 0;

	public static void update() {
		generateMap();
		gravity();
		moveAll();
		checkCollision();
		removeAllDead();
		drawHitbox();
	}
	
	public static void drawHitbox() {
		Container.getContainer().getItemList().forEach(e -> e.drawHb());
		Container.getContainer().getMonsterList().forEach(e -> e.drawHb());
		Container.getContainer().getObstacleList().forEach(e -> e.drawHb());
		Container.getContainer().getPlayerList().forEach(e -> e.drawHb());
		Container.getContainer().getSkillList().forEach(e -> e.drawHb());
	}

	private static void generateMap() {
		tick++;
		if (tick > rnd) {
			rnd += new Random().nextInt(RANGE_OBSTACLE_SPACE) + MIN_OBSTACLE_SPACE;
			int r = new Random().nextInt(3);
			if (r == 0) {
				GroundObstacle obstacle = new GroundObstacle(1100, 0, Obstacle.OBSTACLE_WIDTH, Main.SCREEN_HEIGHT);
				Container.getContainer().add(obstacle);
				height = 0;
			} else if (r == 1) {
				AirObstacle obstacle = new AirObstacle(1100, 0, Obstacle.OBSTACLE_WIDTH, Main.SCREEN_HEIGHT);
				Container.getContainer().add(obstacle);
				height = -60;
			} else if (r == 2) {
				HoleObstacle obstacle = new HoleObstacle(1100, 0, HoleObstacle.HOLE_WIDTH, Main.SCREEN_HEIGHT);
				Container.getContainer().add(obstacle);
				height = 0;
			}
		}
		if (tick % 25 == 0) {
			boolean isEmpty = true;
			for (Obstacle obstacle : Container.getContainer().getObstacleList()) {
				if (obstacle.getPositionX() - 100 <= 1100
						&& 1100 <= obstacle.getPositionX() + obstacle.getHb().h + 100) {
					isEmpty = false;
					break;
				}
			}
			if (isEmpty) {
				if (new Random().nextInt(20) >= 1) {
					Jelly jelly = new Jelly(1100, height, Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
					Container.getContainer().add(jelly);
				} else {
					HealthPotion healthPotion = new HealthPotion(1100, height, HealthPotion.POTION_WIDTH,
							Main.SCREEN_HEIGHT);
					Container.getContainer().add(healthPotion);
				}
			}
		}
		if (tick % 50 == 0 && Container.getContainer().getMonsterList().size() == 0) {
			Monster monster = new Monster(1100, Map.FLOOR_HEIGHT - Monster.MONSTER_HEIGHT, Monster.MONSTER_WIDTH,
					Main.SCREEN_HEIGHT, 5);
			Container.getContainer().add(monster);
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
			if (Listener.keys.contains(KeyCode.RIGHT)) { // RIGHT = RUNNING
				Container.getContainer().getMapList().forEach(e -> e.setSpeedX(-20));
				Container.getContainer().getItemList().forEach(e -> e.setSpeedX(-20));
				Container.getContainer().getObstacleList().forEach(e -> e.setSpeedX(-20));
			} else {
				Container.getContainer().getMapList().forEach(e -> e.setSpeedX(-10));
				Container.getContainer().getItemList().forEach(e -> e.setSpeedX(-10));
				Container.getContainer().getObstacleList().forEach(e -> e.setSpeedX(-10));
			}

			if (Listener.keys.contains(KeyCode.H)) {
				player.setHp(player.getMaxHp());
			}

			if (Listener.keys.contains(KeyCode.UP)) { // UP = JUMP
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

			if (Listener.keys.contains(KeyCode.DOWN)) { // DOWN = SLIDE & FAST FALL
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
						Fireball fireball = new Fireball(Player.PLAYER_POSITON_X,
								player.getPositionY() + Player.PLAYER_HEIGHT / 2, Fireball.SKILL_WIDTH,
								Fireball.SKILL_HEIGHT);
						fireball.setPlayer(player);
						Container.getContainer().add(fireball);
					}
				}
			}

			if (Listener.keys.contains(KeyCode.W)) { // W = LIGHTNING
				if (player.getState() != State.SLIDING) { // NOT SLIDING
					if (Container.getContainer().getSkillList().size() < Player.LIMIT_NORMAL_ATTACK) { // NOT EXCEED
																										// LIMIT NUMBER
						Listener.keys.remove(KeyCode.W);

						Timeline timerLightning = new Timeline(new KeyFrame(Duration.millis(200), e -> {
							Lightning lightning = new Lightning(nearestMonsterPosition(), 0, Lightning.SKILL_WIDTH,
									Lightning.SKILL_HEIGHT);
							lightning.setPlayer(player);
							Container.getContainer().add(lightning);
						}));
						timerLightning.setCycleCount(1);
						timerLightning.play();
					}
				}
			}

			if (Listener.keys.contains(KeyCode.R)) { // R = METEOR (ULTIMATE)
				if (player.getState() != State.SLIDING) { // NOT SLIDING
					if (Container.getContainer().getSkillList().size() < Player.LIMIT_NORMAL_ATTACK) { // NOT EXCEED
																										// LIMIT NUMBER
						Listener.keys.remove(KeyCode.R);

						Timeline timerMeteor = new Timeline(new KeyFrame(Duration.millis(150), e -> {
							Meteor meteor = new Meteor(Player.PLAYER_POSITON_X,
									player.getPositionY() + Player.PLAYER_HEIGHT / 2, Meteor.SKILL_WIDTH,
									Meteor.SKILL_HEIGHT);
							meteor.setPlayer(player);
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

		for (Item item : Container.getContainer().getItemList()) {
			item.move();
		}

		for (Monster monster : Container.getContainer().getMonsterList()) {
			monster.move();
		}

	}

	public static double nearestMonsterPosition() {
		double pos = 800;
		for (Monster monster : Container.getContainer().getMonsterList()) {
			pos = Math.min(pos, monster.getPositionX() + 50);
		}
		return pos;
	}

	public static void checkCollision() {
		for (Player player : Container.getContainer().getPlayerList()) {
			if (player.getPowerState() == PowerState.NORMAL) { // NOT IMMORTAL
				for (Obstacle obstacle : Container.getContainer().getObstacleList()) {
					if (obstacle.isCollision(player)) { // CHECK COLLISION
						if (obstacle instanceof HoleObstacle) // FALL IN A HOLE
							player.die();
						else
							player.decreaseHp(Obstacle.OBSTACLE_DAMAGE); // DAMAGED BY HITTING
					}
				}
			}

			for (Item item : Container.getContainer().getItemList()) {
				if (item.isCollision(player)) {
					item.setCollected(true);
					item.effect(player);
				}
			}
		}

		for (Monster monster : Container.getContainer().getMonsterList()) {
			for (Skill skill : Container.getContainer().getSkillList()) {
				if (skill.isCollision(monster)) {
					if (skill instanceof Fireball)
						monster.decreaseHp(Fireball.SKILL_DAMAGE);
					else if (skill instanceof Lightning)
						monster.decreaseHp(Lightning.SKILL_DAMAGE);
					else if (skill instanceof Meteor)
						monster.decreaseHp(Meteor.SKILL_DAMAGE);
					if (monster.isDead())
						skill.getPlayer().addScore(skill.getPlayer().getScore() * 10);
				}
			}
			for (Player player : Container.getContainer().getPlayerList()) {
				if (player.isCollision(monster)) {
					player.decreaseHp(monster.getAtk());
				}
			}
		}
	}

	public static void removeAllDead() {
		Container.getContainer().getObstacleList().removeIf(o -> o.isDead());
		Container.getContainer().getSkillList().removeIf(f -> f.isDead());
		Container.getContainer().getPlayerList().removeIf(p -> p.isDead());
		Container.getContainer().getItemList().removeIf(i -> i.isDead());
		Container.getContainer().getMonsterList().removeIf(m -> m.isDead());
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

		for (Item item : Container.getContainer().getItemList()) {
			item.changeImage();
		}

		for (Monster monster : Container.getContainer().getMonsterList()) {
			monster.changeImage();
		}
	}

}
