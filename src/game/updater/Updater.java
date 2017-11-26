package game.updater;

import java.util.ArrayList;
import java.util.Random;

import entity.Entity;
import entity.characters.Characters;
import entity.characters.Monster;
import entity.characters.Player;
import entity.item.HealthPotion;
import entity.item.Item;
import entity.item.Jelly;
import entity.map.Map;
import entity.obstacle.AirObstacle;
import entity.obstacle.GroundObstacle;
import entity.obstacle.HoleObstacle;
import entity.obstacle.Obstacle;
import entity.skill.Darkspear;
import entity.skill.Meteor;
import entity.skill.Shield;
import entity.skill.Skill;
import game.GameMain;
import game.model.Model;
import input.GameHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import window.SceneManager;

public class Updater {
	public static final double FPS = 60;
	public static final double LOOP_TIME = 1000 / FPS;

	private static Timeline timerUpdate;
	static {
		timerUpdate = new Timeline(new KeyFrame(Duration.millis(LOOP_TIME), e -> {
			GameHandler.keyHeld();
			generateMap();
			moveAll();
			checkAllCollision();
			removeAllDead();
			updatePlayer();
			// drawHitbox();
		}));
		timerUpdate.setCycleCount(Animation.INDEFINITE);
	}

	private static Map map;
	private static Player player;
	private static double spaceObstacle;
	private static double spaceItem;
	private static double spaceMonster;
	private static double height; // item's spawn height
	private static double distance;

	public Updater() {
		map = SceneManager.getMap();
		player = Model.getContainer().getPlayer();
		spaceObstacle = GameMain.OBSTACLE_SPACE;
		spaceItem = GameMain.ITEM_SPACE;
		spaceMonster = GameMain.MONSTER_SPACE;
		height = 500;
		distance = 0;
	}

	public void startGame() {
		new Thread(new Runnable() {
			public void run() {
				timerUpdate.play();
			}
		}).start();
	}

	public void pauseGame() {
		if (timerUpdate != null) {
			timerUpdate.pause();
		}
	}

	public void continueGame() {
		if (timerUpdate != null) {
			timerUpdate.play();
		}
	}

	public void stopGame() {
		if (timerUpdate != null) {
			timerUpdate.stop();
		}
	}

	private static void generateMap() {
		distance += GameMain.SPEED;
		if (distance >= spaceObstacle) {
			spaceObstacle += GameMain.OBSTACLE_SPACE;
			Obstacle obstacle;
			int r = new Random().nextInt(3);
			if (r == 0) {
				obstacle = new GroundObstacle();
				height = 500;
			} else if (r == 1) {
				obstacle = new AirObstacle();
				height = 440;
			} else {
				obstacle = new HoleObstacle();
				height = 500;
			}
			Model.getContainer().add(obstacle);
		}
		if (distance >= spaceItem) {
			if (isEmpty()) {
				spaceItem = distance + GameMain.ITEM_SPACE;
				if (new Random().nextInt(20) >= 1) {
					Jelly jelly = new Jelly(SceneManager.SCREEN_WIDTH + 100, height);
					Model.getContainer().add(jelly);
				} else {
					HealthPotion healthPotion = new HealthPotion(SceneManager.SCREEN_WIDTH + 100, height,
							HealthPotion.POTION_WIDTH, SceneManager.SCREEN_HEIGHT);
					Model.getContainer().add(healthPotion);
				}
			}
		}
		if (distance >= spaceMonster) {
			spaceMonster += GameMain.MONSTER_SPACE;
			Monster monster = new Monster(SceneManager.SCREEN_WIDTH + 100, 0, SceneManager.SCREEN_WIDTH,
					SceneManager.SCREEN_HEIGHT, new Random().nextInt(4) + 2);
			Model.getContainer().add(monster);
		}
	}

	private static boolean isEmpty() {
		boolean check = true;
		double rangeCheck = 0;
		for (Obstacle obstacle : Model.getContainer().getObstacleList()) {
			if (obstacle instanceof AirObstacle) {
				rangeCheck = 0;
			} else if (obstacle instanceof GroundObstacle) {
				rangeCheck = 350;
			} else if (obstacle instanceof HoleObstacle) {
				rangeCheck = 150;
			}
			if (obstacle.getPositionX() - rangeCheck <= SceneManager.SCREEN_WIDTH + 200
					&& SceneManager.SCREEN_WIDTH + 100 <= obstacle.getPositionX() + obstacle.getWidth() + rangeCheck) {
				check = false;
				break;
			}
		}
		return check;
	}

	private static void moveAll() {
		map.move();

		for (Obstacle obstacle : Model.getContainer().getObstacleList()) {
			obstacle.move();
		}

		if (player != null) {
			player.move();
		}

		for (Skill skill : Model.getContainer().getSkillList()) {
			skill.move();
		}

		for (Item item : Model.getContainer().getItemList()) {
			item.move();
		}

		for (Monster monster : Model.getContainer().getMonsterList()) {
			monster.move();
		}

	}

	private static void checkAllCollision() {
		if (player == null) {
			return;
		}
		ArrayList<Player> playerList = new ArrayList<>();
		playerList.add(player);
		ArrayList<Monster> monsterList = Model.getContainer().getMonsterList();
		ArrayList<Obstacle> obstacleList = Model.getContainer().getObstacleList();
		ArrayList<Item> itemList = Model.getContainer().getItemList();
		ArrayList<Skill> skillList = Model.getContainer().getSkillList();

		checkPairCollision(playerList, obstacleList);
		checkPairCollision(playerList, monsterList);
		checkPairCollision(playerList, itemList);
		checkPairCollision(playerList, skillList);
		checkPairCollision(monsterList, skillList);
		checkPairCollision(skillList, skillList);
	}

	private static void checkPairCollision(ArrayList<? extends Entity> first, ArrayList<? extends Entity> second) {
		if (player == null) {
			return;
		}
		for (Entity firstEntity : first) {
			for (Entity secondEntity : second) {

				if (firstEntity instanceof Characters) {
					if (secondEntity.isCollision(firstEntity)) {
						secondEntity.affectTo((Characters) firstEntity);
					}
				}

				else if (firstEntity instanceof Skill) {
					if (firstEntity instanceof Shield && !(secondEntity instanceof Darkspear)
							&& !(secondEntity instanceof Meteor)) {
						if (secondEntity.isCollision(firstEntity)) {
							((Shield) firstEntity).affectTo((Skill) secondEntity);
						}
					} else {
						break;
					}
				}
			}
		}
	}

	private static void removeAllDead() {
		Model.getContainer().getObstacleList().removeIf(o -> o.isDead());
		Model.getContainer().getSkillList().removeIf(f -> f.isDead());
		Model.getContainer().getItemList().removeIf(i -> i.isDead());
		Model.getContainer().getMonsterList().removeIf(m -> m.isDead());
	}

	private static void updatePlayer() {
		if (player == null) {
			return;
		}
		player.decreaseHp(Map.PASSIVE_DAMAGE);
		if (player == null) {
			return;
		}
		player.decreaseCooldown(1 / Updater.FPS);
		player.addMana(Map.PASSIVE_MANA_REGEN);
		player.addScore(Map.PASSIVE_SCORE);
		player.addDistance(GameMain.SPEED);
	}

	private static void drawHitbox() {
		Model.getContainer().getItemList().forEach(e -> e.drawHb());
		Model.getContainer().getMonsterList().forEach(e -> e.drawHb());
		Model.getContainer().getObstacleList().forEach(e -> e.drawHb());
		player.drawHb();
		Model.getContainer().getSkillList().forEach(e -> e.drawHb());
	}

	public static void playerDead() {
		player = null;
	}
}
