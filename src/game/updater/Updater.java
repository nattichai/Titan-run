package game.updater;

import java.util.ArrayList;
import java.util.Random;

import entity.Entity;
import entity.characters.Characters;
import entity.characters.Monster;
import entity.characters.Player;
import entity.gui.GUIRectangle;
import entity.gui.GUIText;
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
import entity.skill.Thunderbolt;
import game.GameMain;
import game.model.Model;
import input.GameHandler;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
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
	private static boolean isBossStage;

	public Updater() {
		map = SceneManager.getMap();
		player = Model.getContainer().getPlayer();
		spaceObstacle = GameMain.OBSTACLE_SPACE;
		spaceItem = GameMain.ITEM_SPACE;
		spaceMonster = GameMain.MONSTER_SPACE;
		height = 500;
		distance = 0;
		isBossStage = false;
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
		if (distance >= GameMain.STAGE_DISTANCE) {
			distance = 0;
			isBossStage = true;
			showWarning();
		}
		if (!isBossStage) {
			distance += GameMain.SPEED;
			normalStage();
		} else {
			bossStage();
		}
	}

	private static void normalStage() {
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
					HealthPotion healthPotion = new HealthPotion(SceneManager.SCREEN_WIDTH + 100, height);
					Model.getContainer().add(healthPotion);
				}
			}
		}
		if (distance >= spaceMonster) {
			spaceMonster += GameMain.MONSTER_SPACE;
			Monster monster = new Monster(SceneManager.SCREEN_WIDTH + 100, 0, new Random().nextInt(4) + 2);
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

	private static void showWarning() {
		GUIText warning = new GUIText(0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT, "WARNING",
				Color.rgb(0xC0, 0, 0), 150);
		GUIRectangle warningBackground = new GUIRectangle(0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT,
				Color.BLACK, 0.5);
		Model.getContainer().add(warningBackground);
		Model.getContainer().add(warning);
		FadeTransition ft1 = new FadeTransition(Duration.millis(200), warningBackground.getCanvas());
		ft1.setFromValue(0);
		ft1.setToValue(0.5);
		ft1.setAutoReverse(true);
		ft1.setCycleCount(15);
		ft1.play();
		FadeTransition ft2 = new FadeTransition(Duration.millis(200), warning.getCanvas());
		ft2.setFromValue(0);
		ft2.setToValue(1);
		ft2.setAutoReverse(true);
		ft2.setCycleCount(15);
		ft2.play();
		ft1.setOnFinished(e -> {
			Model.getContainer().remove(warning);
			Model.getContainer().remove(warningBackground);
		});
	}

	private static void bossStage() {

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
							&& !(secondEntity instanceof Meteor) && !(secondEntity instanceof Thunderbolt)) {
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
		if (!isBossStage)
			player.addDistance(GameMain.SPEED);
	}

	private static void drawHitbox() {
		Model.getContainer().getItemList().forEach(e -> e.drawHb());
		Model.getContainer().getMonsterList().forEach(e -> e.drawHb());
		Model.getContainer().getObstacleList().forEach(e -> e.drawHb());
		if (player != null) {
			player.drawHb();
		}
		Model.getContainer().getSkillList().forEach(e -> e.drawHb());
	}

	public static void playerDead() {
		player = null;
	}
}
