package game;

import game.animate.Animate;
import game.logic.Logic;
import game.model.Model;
import javafx.scene.layout.Pane;
import window.SceneManager;

public class GameMain {
	private static Pane gamePane;
	private static Logic logic;
	private static Animate animate;
	
	public static void newGame() {
		gamePane = new Pane();
		logic = new Logic();
		animate = new Animate();
		
		Model.initialize();
		SceneManager.gotoSceneOf(gamePane);
		
		logic.startGame();
		animate.startAnimation();
	}

	public static Pane getGamePane() {
		return gamePane;
	}
}
