package scene;

import game.model.BackgroundMusic;
import game.model.Model;
import game.updater.Animations;
import game.updater.Updater;
import javafx.animation.Animation.Status;
import javafx.scene.layout.Pane;

public class GameMain {
	private static Pane gamePane;
	private static Updater updater;
	private static Animations animations;
	
	private static double difficulty;
	private static double speed = 2;
	private static double obstacleSpace = 1200;
	private static double itemSpace = 100;
	private static double monsterSpace = 2000;
	private static double stageDistance = 25000;

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

		Updater.setPlayer();
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
	
	public static void pauseOrResumeGame() {
		if (Animations.getTimerAnimation().getStatus() == Status.PAUSED) {
			continueGame();
			if (Model.getContainer().getPlayer() != null) {
				Model.getContainer().getPlayer().getUserInterface().closePauseArea();
			}
			BackgroundMusic.continueMusic();
		} else {
			pauseGame();
			if (Model.getContainer().getPlayer() != null) {
				Model.getContainer().getPlayer().getUserInterface().showPauseArea();
			}
			BackgroundMusic.pauseMusic();
		}
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

	public static double getSpeed() {
		return speed;
	}

	public static void setSpeed(double s) {
		speed = s;
		SceneManager.getMap().setSpeedX(-s);
		Model.getContainer().getItemList().forEach(e -> e.setSpeedX(-s));
		Model.getContainer().getObstacleList().forEach(e -> e.setSpeedX(-s));
	}

	public static double getDifficulty() {
		return difficulty;
	}

	public static void setDifficulty(double d) {
		difficulty = d;
		if (d > 4) {
			d = 4;
		}
		obstacleSpace = 1400 - d * 200;
		itemSpace = 100;
		monsterSpace = 2600 - d * 200;
		stageDistance = 25000 - d * 2000;
	}

	public static double getObstacleSpace() {
		return obstacleSpace;
	}

	public static double getItemSpace() {
		return itemSpace;
	}

	public static double getMonsterSpace() {
		return monsterSpace;
	}

	public static double getStageDistance() {
		return stageDistance;
	}
}
