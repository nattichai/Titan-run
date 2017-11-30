package game;

import game.animations.Animations;
import game.model.Model;
import game.updater.Updater;
import javafx.scene.layout.Pane;
import window.SceneManager;

public class GameMain {
	public static double SPEED = 2;
	public static final int OBSTACLE_SPACE = 1200;
	public static final int ITEM_SPACE = 100;
	public static final int MONSTER_SPACE = 3000;
	public static final double STAGE_DISTANCE = 25000;

	private static Pane gamePane;
	private static Updater updater;
	private static Animations animations;

	public static void initialize() {
		gamePane = new Pane();
		Model.initialize();
		Model container = Model.getContainer();
		gamePane.getChildren().add(container.getObstaclePane());
		gamePane.getChildren().add(container.getItemPane());
		gamePane.getChildren().add(container.getMonsterPane());
		gamePane.getChildren().add(container.getPlayerPane());
		gamePane.getChildren().add(container.getSkillPane());
		gamePane.getChildren().add(container.getEffectPane());
		gamePane.getChildren().add(container.getGuiPane());
	}

	public static void setSpeed(double speed) {
		GameMain.SPEED = speed;
		SceneManager.getMap().setSpeedX(-speed);
		Model.getContainer().getItemList().forEach(e -> e.setSpeedX(-speed));
		Model.getContainer().getObstacleList().forEach(e -> e.setSpeedX(-speed));
	}

	public static Pane getGamePane() {
		return gamePane;
	}

	public static void startGame() {
		updater = new Updater();
		animations = new Animations();
		updater.startGame();
		animations.startAnimation();
	}

	public static void pauseGame() {
		updater.pauseGame();
		animations.pauseAnimation();
	}

	public static void continueGame() {
		updater.continueGame();
		animations.continueAnimation();
	}

	public static void stopGame() {
		updater.stopGame();
		animations.stopAnimation();
	}
}
