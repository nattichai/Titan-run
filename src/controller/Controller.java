package controller;

import java.util.Random;

import dataStorge.Container;
import dataStorge.PlayerData;
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
import entity.skill.Slashy;
import entity.skill.Thunderbolt;
import main.Main;
import property.PowerState;

public class Controller {
	public static final int MIN_OBSTACLE_SPACE = 100;
	public static final int RANGE_OBSTACLE_SPACE = 20;

	private static int tick = 0;
	private static int rnd = MIN_OBSTACLE_SPACE;
	private static int height = 0;
	
	public Controller() {
		tick = 0;
		rnd = MIN_OBSTACLE_SPACE;
		height = 0;
	}

	public static void update() {
		Handler.handler();
		generateMap();
		moveAll();
		checkCollision();
		removeAllDead();
		decreaseCooldown();
//		drawHitbox();
	}

	private static void generateMap() {
		tick++;
		if (tick > rnd) {
			rnd += new Random().nextInt(RANGE_OBSTACLE_SPACE) + MIN_OBSTACLE_SPACE;
			int r = new Random().nextInt(3);
			if (r == 0) {
				GroundObstacle groundObstacle = new GroundObstacle(1100, 0, Obstacle.OBSTACLE_WIDTH,
						Main.SCREEN_HEIGHT);
				Container.getContainer().add(groundObstacle);
				height = 0;
			} else if (r == 1) {
				AirObstacle airObstacle = new AirObstacle(1100, 0, Obstacle.OBSTACLE_WIDTH, Main.SCREEN_HEIGHT);
				Container.getContainer().add(airObstacle);
				height = -60;
			} else if (r == 2) {
				HoleObstacle holeObstacle = new HoleObstacle(1100, 0, HoleObstacle.HOLE_WIDTH, Main.SCREEN_HEIGHT);
				Container.getContainer().add(holeObstacle);
				height = 0;
			}
		}
		if (tick % 15 == 0 && tick + 15 < rnd) {
			if (isEmpty()) {
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
		if (tick % 200 == 0) {
			Monster monster = new Monster(1100, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, new Random().nextInt(4) + 2);
			Container.getContainer().add(monster);
		}
	}

	private static boolean isEmpty() {
		boolean check = true;
		for (Obstacle obstacle : Container.getContainer().getObstacleList()) {
			if (obstacle.getPositionX() <= 1100 && 1100 <= obstacle.getPositionX() + obstacle.getWidth()) {
				check = false;
				break;
			}
		}
		return check;
	}

	private static void moveAll() {
		for (Map map : Container.getContainer().getMapList()) {
			map.move();
		}

		for (Obstacle obstacle : Container.getContainer().getObstacleList()) {
			obstacle.move();
		}

		for (Player player : Container.getContainer().getPlayerList()) {
			player.move();
			player.addMana(Map.PASSIVE_MANA_REGEN);
			player.decreaseHp(Map.PASSIVE_DAMAGE); // LOST HP FROM MOVING
			player.getPlayerData().addScore(Map.PASSIVE_SCORE); // ADD SCORE FROM MOVING
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

	public static void checkCollision() {
		for (Player player : Container.getContainer().getPlayerList()) {
			if (player.getPowerState() != PowerState.IMMORTAL) { // NOT IMMORTAL
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
					else if (skill instanceof Thunderbolt)
						monster.decreaseHp(Thunderbolt.SKILL_DAMAGE);
					else if (skill instanceof Slashy)
						monster.decreaseHp(Slashy.SKILL_DAMAGE);
					else if (skill instanceof Meteor)
						monster.decreaseHp(Meteor.SKILL_DAMAGE);
					if (monster.isDead()) {
						if (skill.getOwner() instanceof Player) {
							Player player = (Player) skill.getOwner();
							player.getPlayerData().addScore(player.getPlayerData().getScore() * 10);
						}
					}
				}
			}
			if (monster.getHp() > 0.00001) {
				for (Player player : Container.getContainer().getPlayerList()) {
					if (player.getPowerState() != PowerState.IMMORTAL && monster.isCollision(player)) {
						player.decreaseHp(monster.getAtk());
					}
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

	public static void decreaseCooldown() {
		double timePassed = 1 / Main.FRAME_RATE;
		PlayerData.decreaseCooldown(timePassed);
	}

	public static void drawHitbox() {
		Container.getContainer().getItemList().forEach(e -> e.drawHb());
		Container.getContainer().getMonsterList().forEach(e -> e.drawHb());
		Container.getContainer().getObstacleList().forEach(e -> e.drawHb());
		Container.getContainer().getPlayerList().forEach(e -> e.drawHb());
		Container.getContainer().getSkillList().forEach(e -> e.drawHb());
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

	public static void setTick(int tick) {
		Controller.tick = tick;
	}
	
	public static void setRnd(int rnd) {
		Controller.rnd = rnd;
	}
}
