package game;

import game.animations.Animations;
import game.model.Model;
import game.updater.Updater;
import javafx.scene.layout.Pane;
import window.SceneManager;

public class GameMain {
	private static Pane gamePane;
	private static Updater updater;
	private static Animations animations;

	public static void newGame() {
		gamePane = new Pane();
		updater = new Updater();
		animations = new Animations();

		Model.initialize();
		SceneManager.gotoSceneOf(gamePane);

		updater.startGame();
		animations.startAnimation();
	}

	public static Pane getGamePane() {
		return gamePane;
	}
}
