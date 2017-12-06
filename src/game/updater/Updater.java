package game.updater;

import java.util.ArrayList;
import java.util.Random;

import game.model.BackgroundMusic;
import game.model.Characters;
import game.model.Entity;
import game.model.Item;
import game.model.Map;
import game.model.Model;
import game.model.Obstacle;
import game.model.Skill;
import game.model.character.Boss;
import game.model.character.Monster;
import game.model.character.Player;
import game.model.gui.GUIRectangle;
import game.model.gui.GUIText;
import game.model.item.HealthPotion;
import game.model.item.Jelly;
import game.model.obstacle.AirObstacle;
import game.model.obstacle.GroundObstacle;
import game.model.obstacle.HoleObstacle;
import game.property.State;
import input.GameHandler;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import scene.GameMain;
import scene.SceneManager;

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
		spaceObstacle = GameMain.OBSTACLE_SPACE;
		spaceItem = GameMain.ITEM_SPACE;
		spaceMonster = GameMain.MONSTER_SPACE;
		height = 500;
		distance = 0;
		isBossStage = false;
	}

	public void startGame() {
		timerUpdate.play();
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
			showWarning();
		}
		if (!isBossStage && player != null) {
			distance += GameMain.getSpeed();
			normalStage();
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
		// all monster go away
		Model.getContainer().getMonsterList().forEach(e -> {
			e.setCanMove(true);
			e.setCanMoveOut(true);
		});

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
			bossStage();
		});
		BackgroundMusic.stopNormalStageBGM();
		BackgroundMusic.playWarningSiren();
		BackgroundMusic.playBossStageBGM();
	}

	private static void bossStage() {
		isBossStage = true;
		// force release spacebar
		GameHandler.keyReleased(new KeyEvent(null, null, KeyEvent.KEY_RELEASED, null, "SPACE", KeyCode.SPACE, false,
				false, false, false));
		SceneManager.setTrasitioning(true);

		Boss robotek = new Boss(SceneManager.SCREEN_WIDTH + 100, 150, 7);
		robotek.moveTo(600, 150, 3000);
		Model.getContainer().add(robotek);

		if (player != null) {
			player.setSpeedX(0.5);
			if (player.getState() == State.SLIDING) {
				player.getCanvas().setRotate(0);
				player.setState(State.RUNNING);
			}
		}

		map.getCanvas().setScaleY(1.04);
		Random rnd = new Random();
		Timeline shake = new Timeline(new KeyFrame(Duration.millis(LOOP_TIME), e -> {
			map.getCanvas().setTranslateX(map.getPositionX() + rnd.nextInt(31) - 15);
			map.getCanvas().setTranslateY(map.getPositionY() + rnd.nextInt(31) - 15);
		}));
		shake.setCycleCount((int) (FPS * 3));
		shake.play();
		shake.setOnFinished(e -> {
			map.getCanvas().setScaleY(1);
		});

		double damp = GameMain.getSpeed() / (FPS * 3);
		Timeline slowDown = new Timeline(new KeyFrame(Duration.millis(LOOP_TIME), e -> {
			GameMain.setSpeed(GameMain.getSpeed() - damp);
		}));
		slowDown.setCycleCount((int) (FPS * 3));
		slowDown.play();
		slowDown.setOnFinished(e -> {
			GameMain.setSpeed(0);
			SceneManager.setTrasitioning(false);
			if (player != null) {
				player.setSpeedX(0);
				player.setState(State.STILL);
				robotek.setReady(true);
			}
		});
	}

	public static void victory() {
		SceneManager.setTrasitioning(true);

		if (player != null) {
			player.setSpeedX((90 - player.getPositionX()) / FPS);
		}

		double accel = 10 / (FPS * 3);
		Timeline speedUp = new Timeline(new KeyFrame(Duration.millis(LOOP_TIME), e -> {
			GameMain.setSpeed(GameMain.getSpeed() + accel);
			if (player != null) {
				if (player.getPositionX() < 90) {
					player.backToRunningPosition();
				}
			}
		}));
		speedUp.setCycleCount((int) (FPS * 3));
		speedUp.play();
		speedUp.setOnFinished(e -> {
			new Updater();
			if (player != null) {
				player.addStage(1);
				GameMain.setSpeed(10 + 10 * player.getStage() / (player.getStage() + 10));
			}
			SceneManager.setTrasitioning(false);
		});
		BackgroundMusic.stopBossStageBGM();
		BackgroundMusic.playNormalStageBGM();
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
			if (!(monster instanceof Boss)) {
				monster.move();
			} else if (((Boss) monster).isReady()) {
				((Boss) monster).update();
			}
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
		// checkPairCollision(skillList, skillList);
	}

	private static void checkPairCollision(ArrayList<? extends Entity> first, ArrayList<? extends Entity> second) {
		for (Entity firstEntity : first) {
			for (Entity secondEntity : second) {
				if (secondEntity.isCollision(firstEntity)) {
					secondEntity.affectTo((Characters) firstEntity);
				}
			}
		}
	}

	private static void removeAllDead() {
		Model.getContainer().getObstacleList().removeIf(o -> o.isDead());
		Model.getContainer().getSkillList().removeIf(f -> f.isDead());
		Model.getContainer().getItemList().removeIf(i -> i.isDead());
		Model.getContainer().getMonsterList().removeIf(m -> m.isDead());
		Model.getContainer().getEffectList().removeIf(e -> e.isDead());
	}

	private static void updatePlayer() {
		if (player == null) {
			return;
		}
		player.decreaseCooldown(1 / Updater.FPS);
		player.addMana(Map.PASSIVE_MANA_REGEN);
		player.addScore(Map.PASSIVE_SCORE);
		if (!isBossStage) {
			player.addDistance(GameMain.getSpeed());
			player.decreaseHp(Map.PASSIVE_DAMAGE);
		}
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
	
	public static void setPlayer() {
		player = Model.getContainer().getPlayer();
	}

	public static void playerDead() {
		player = null;
	}

	public static boolean isBossStage() {
		return isBossStage;
	}

	public static void setBossStage(boolean isBossStage) {
		Updater.isBossStage = isBossStage;
	}
}
