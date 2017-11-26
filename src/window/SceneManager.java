package window;

import entity.map.Map;
import game.GameMain;
import game.animations.Animations;
import game.model.Model;
import game.updater.Updater;
import input.GameHandler;
import input.MainMenuHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import menu.MainMenu;

public class SceneManager {
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 750;
	private static Map map = new Map(0, 0, SceneManager.SCREEN_WIDTH * 2, SceneManager.SCREEN_HEIGHT);
	private static Stage primaryStage;
	private static Pane root;
	private static Scene scene;
	private static Pane oldPane;
	private static Pane newPane;
	private static Updater updater;
	private static Animations animations;
	private static boolean isTrasitioning;

	public static void initialize(Stage stage) {
		Model.initialize();

		oldPane = new Pane();
		root = new Pane(map.getCanvas(), oldPane);

		scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		scene.getStylesheets().add(ClassLoader.getSystemResource("utility/style.css").toExternalForm());

		primaryStage = stage;
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void gotoMainMenu() {
		MainMenu.initialize();
		newPane = MainMenu.getMainMenu();
		newPane.setFocusTraversable(true);
		newPane.setOnKeyPressed(event -> MainMenuHandler.keyPressed(event));
		gotoSceneOf(newPane, 2);
	}

	public static void gotoGame() {
		GameMain.initialize();
		newPane = GameMain.getGamePane();
		newPane.setFocusTraversable(true);
		newPane.setOnKeyPressed(event -> GameHandler.keyPressed(event));
		newPane.setOnKeyReleased(event -> GameHandler.keyReleased(event));
		gotoSceneOf(newPane, 10);
	}

	public static void gotoSceneOf(Pane pane, double speed) {
		double accel = (speed - GameMain.SPEED) / (2 * Updater.FPS * 3);
		double distance = (speed + GameMain.SPEED) / 2 * (2 * Updater.FPS * 3);

		pane.setTranslateX(distance);

		if (!root.getChildren().contains(pane)) {
			root.getChildren().add(pane);
		}

		isTrasitioning = true;
		Timeline transition = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME / 3), e -> {
			GameMain.setSpeed(GameMain.SPEED + accel);
			oldPane.setTranslateX(oldPane.getTranslateX() - GameMain.SPEED);
			pane.setTranslateX(pane.getTranslateX() - GameMain.SPEED);
		}));
		transition.setCycleCount((int) (Updater.FPS * 2) * 3);
		transition.play();
		transition.setOnFinished(e -> {
			isTrasitioning = false;
			root.getChildren().remove(oldPane);
			oldPane = pane;
		});

		startGame();

		primaryStage.setOnCloseRequest(e -> {
			stopGame();
		});
	}

	public static Map getMap() {
		return map;
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

	public static boolean isTrasitioning() {
		return isTrasitioning;
	}
}
