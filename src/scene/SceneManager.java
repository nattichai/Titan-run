package scene;

import game.model.BackgroundMusic;
import game.model.Map;
import game.updater.Updater;
import input.GameHandler;
import input.MainMenuHandler;
import input.ScoreViewHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneManager {
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 750;
	private static Map map = new Map(0, 0, SceneManager.SCREEN_WIDTH * 2, SceneManager.SCREEN_HEIGHT);
	private static Stage primaryStage;
	private static Pane root;
	private static Scene scene;
	private static Pane oldPane;
	private static Pane newPane;
	private static boolean isTrasitioning;

	public static void initialize(Stage stage) {
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
		BackgroundMusic.stopMain​MenuBGM();
		BackgroundMusic.playNormalStageBGM();
	}

	public static void gotoRankings() {
		Rankings.initialize();
		newPane = Rankings.getRankingsPane();
		newPane.setFocusTraversable(true);
		newPane.setOnKeyPressed(event -> ScoreViewHandler.keyPressed(event));
		gotoSceneOf(newPane, 5);
	}

	public static void gotoScoreView() {
		ScoreView.initialize();
		newPane = ScoreView.getScoreViewPane();
		newPane.setFocusTraversable(true);
		newPane.setOnKeyPressed(event -> ScoreViewHandler.keyPressed(event));
		gotoSceneOf(newPane, 5);
	}

	public static void gotoSceneOf(Pane pane, double speed) {
		double accel = (speed - GameMain.getSpeed()) / (2 * Updater.FPS * 3);
		double distance = (speed + GameMain.getSpeed()) / 2 * (2 * Updater.FPS * 3);

		pane.setTranslateX(distance);

		if (!root.getChildren().contains(pane)) {
			root.getChildren().add(pane);
		}

		isTrasitioning = true;
		Timeline transition = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME / 3), e -> {
			GameMain.setSpeed(GameMain.getSpeed() + accel);
			oldPane.setTranslateX(oldPane.getTranslateX() - GameMain.getSpeed());
			pane.setTranslateX(pane.getTranslateX() - GameMain.getSpeed());
		}));
		transition.setCycleCount((int) (Updater.FPS * 2) * 3);
		transition.play();
		transition.setOnFinished(e -> {
			isTrasitioning = false;
			root.getChildren().remove(oldPane);
			oldPane = pane;
		});

		GameMain.startGame();

		primaryStage.setOnCloseRequest(e -> {
			GameMain.stopGame();
		});
	}

	public static Map getMap() {
		return map;
	}

	public static boolean isTrasitioning() {
		return isTrasitioning;
	}

	public static void setTrasitioning(boolean isTrasitioning) {
		SceneManager.isTrasitioning = isTrasitioning;
	}
}
