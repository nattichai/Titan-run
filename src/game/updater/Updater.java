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
import game.model.Model;
import input.GameHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import window.SceneManager;

public class Updater {
	public static final double FPS = 50;
	public static final double LOOP_TIME = 1000 / FPS;
	public static final int MIN_OBSTACLE_SPACE = 100;
	public static final int RANGE_OBSTACLE_SPACE = 20;

	private static Timeline timerUpdate;
	private static int tick = 0;
	private static int rnd = MIN_OBSTACLE_SPACE;
	private static int height = 0;

	public Updater() {
		tick = 0;
		rnd = MIN_OBSTACLE_SPACE;
		height = 0;
	}

	public void startGame() {
		new Thread(this::gameLoop, "Game Loop Thread").start();
	}

	public void stopGame() {
		if (timerUpdate != null)
			timerUpdate.stop();
	}

	private void gameLoop() {
		timerUpdate = new Timeline(new KeyFrame(Duration.millis(LOOP_TIME), e -> {
			update();
		}));
		timerUpdate.setCycleCount(Animation.INDEFINITE);
		timerUpdate.play();
	}

	public static void update() {
		GameHandler.keyHeld();
		generateMap();
		moveAll();
		checkAllCollision();
		removeAllDead();
		decreaseCooldown();
		// showSize();
//		 drawHitbox();
	}

	private static void generateMap() {
		tick++;
		if (tick > rnd) {
			rnd += new Random().nextInt(RANGE_OBSTACLE_SPACE) + MIN_OBSTACLE_SPACE;
			int r = new Random().nextInt(3);
			if (r == 0) {
				GroundObstacle groundObstacle = new GroundObstacle(1100, 0, Obstacle.OBSTACLE_WIDTH,
						SceneManager.SCREEN_HEIGHT);
				Model.getContainer().add(groundObstacle);
				height = 0;
			} else if (r == 1) {
				AirObstacle airObstacle = new AirObstacle(1100, 0, Obstacle.OBSTACLE_WIDTH, SceneManager.SCREEN_HEIGHT);
				Model.getContainer().add(airObstacle);
				height = -60;
			} else if (r == 2) {
				HoleObstacle holeObstacle = new HoleObstacle(1100, 0, HoleObstacle.HOLE_WIDTH,
						SceneManager.SCREEN_HEIGHT);
				Model.getContainer().add(holeObstacle);
				height = 0;
			}
		}
		if (tick % 15 == 0 && tick + 15 < rnd) {
			if (isEmpty()) {
				if (new Random().nextInt(20) >= 1) {
					Jelly jelly = new Jelly(1100, height, Jelly.JELLY_WIDTH, SceneManager.SCREEN_HEIGHT);
					Model.getContainer().add(jelly);
				} else {
					HealthPotion healthPotion = new HealthPotion(1100, height, HealthPotion.POTION_WIDTH,
							SceneManager.SCREEN_HEIGHT);
					Model.getContainer().add(healthPotion);
				}
			}
		}
		if (tick % 200 == 0) {
			Monster monster = new Monster(1100, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT,
					new Random().nextInt(4) + 2);
			Model.getContainer().add(monster);
		}
	}

	private static boolean isEmpty() {
		boolean check = true;
		for (Obstacle obstacle : Model.getContainer().getObstacleList()) {
			if (obstacle.getPositionX() <= 1100 && 1100 <= obstacle.getPositionX() + obstacle.getWidth()) {
				check = false;
				break;
			}
		}
		return check;
	}

	private static void moveAll() {
		for (Map map : Model.getContainer().getMapList()) {
			map.move();
		}

		for (Obstacle obstacle : Model.getContainer().getObstacleList()) {
			obstacle.move();
		}

		for (Player player : Model.getContainer().getPlayerList()) {
			player.move();
			player.addMana(Map.PASSIVE_MANA_REGEN);
			player.decreaseHp(Map.PASSIVE_DAMAGE); // LOST HP FROM MOVING
			player.getPlayerData().addScore(Map.PASSIVE_SCORE); // ADD SCORE FROM MOVING
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

	public static void checkAllCollision() {
		ArrayList<Player> playerList = Model.getContainer().getPlayerList();
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

	public static void checkPairCollision(ArrayList<? extends Entity> first, ArrayList<? extends Entity> second) {
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

	public static void removeAllDead() {
		Model.getContainer().getObstacleList().removeIf(o -> o.isDead());
		Model.getContainer().getSkillList().removeIf(f -> f.isDead());
		Model.getContainer().getPlayerList().removeIf(p -> p.isDead());
		Model.getContainer().getItemList().removeIf(i -> i.isDead());
		Model.getContainer().getMonsterList().removeIf(m -> m.isDead());
	}

	public static void decreaseCooldown() {
		for (Player player : Model.getContainer().getPlayerList()) {
			player.getPlayerData().decreaseCooldown(1 / Updater.FPS);
		}
	}

	public static void showSize() {
		System.out.print(Model.getContainer().getMapPane().getChildren().size() + " ");
		System.out.print(Model.getContainer().getObstaclePane().getChildren().size() + " ");
		System.out.print(Model.getContainer().getItemPane().getChildren().size() + " ");
		System.out.print(Model.getContainer().getPlayerPane().getChildren().size() + " ");
		System.out.print(Model.getContainer().getMonsterPane().getChildren().size() + " ");
		System.out.print(Model.getContainer().getSkillPane().getChildren().size() + " ");
		System.out.println(Model.getContainer().getGuiPane().getChildren().size());
		System.out.print(Model.getContainer().getMapList().size() + " ");
		System.out.print(Model.getContainer().getObstacleList().size() + " ");
		System.out.print(Model.getContainer().getItemList().size() + " ");
		System.out.print(Model.getContainer().getPlayerList().size() + " ");
		System.out.print(Model.getContainer().getMonsterList().size() + " ");
		System.out.print(Model.getContainer().getSkillList().size() + " ");
		System.out.println(Model.getContainer().getGuiList().size());
	}

	public static void drawHitbox() {
		Model.getContainer().getItemList().forEach(e -> e.drawHb());
		Model.getContainer().getMonsterList().forEach(e -> e.drawHb());
		Model.getContainer().getObstacleList().forEach(e -> e.drawHb());
		Model.getContainer().getPlayerList().forEach(e -> e.drawHb());
		Model.getContainer().getSkillList().forEach(e -> e.drawHb());
	}

	public static void setTick(int tick) {
		Updater.tick = tick;
	}

	public static void setRnd(int rnd) {
		Updater.rnd = rnd;
	}

	public static Timeline getTimerUpdate() {
		return timerUpdate;
	}
}
