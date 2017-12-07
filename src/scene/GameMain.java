package scene;

import game.model.BackgroundMusic;
import game.model.Model;
import game.property.UserInterface;
import game.updater.Animations;
import game.updater.Updater;
import javafx.animation.Animation.Status;
import javafx.scene.layout.Pane;

public class GameMain {
	public static final int OBSTACLE_SPACE = 1200;
	public static final int ITEM_SPACE = 100;
	public static final int MONSTER_SPACE = 3000;
	public static final double STAGE_DISTANCE = 25000;

	private static Pane gamePane;
	private static Updater updater;
	private static Animations animations;

	private static double speed = 2;

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
			UserInterface.closePauseArea();
			BackgroundMusic.continueMusic();
		} else {
			pauseGame();
			UserInterface.showPauseArea();
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
}
