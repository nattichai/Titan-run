package window;

import input.GameHandler;
import input.MainMenuHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import menu.MainMenu;

public class SceneManager {
	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 750;
	public static double SPEED = 10;

	private static Stage primaryStage;
	private static Pane mainMenuPane;
	private static Scene mainMenuScene;

	public static void initialize(Stage stage) {
		primaryStage = stage;
		primaryStage.show();

		MainMenu.initialize();
		mainMenuPane = MainMenu.getMainMenuPane();
		mainMenuScene = new Scene(mainMenuPane, SCREEN_WIDTH, SCREEN_HEIGHT);
		mainMenuScene.setOnKeyPressed(event -> MainMenuHandler.keyPressed(event));
	}

	public static void gotoMainMenu() {
		primaryStage.setScene(mainMenuScene);
	}

	public static void gotoSceneOf(Pane pane) {
		Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);
		scene.getStylesheets().add(ClassLoader.getSystemResource("utility/style.css").toExternalForm());
		scene.setOnKeyPressed(e -> GameHandler.keyPressed(e));
		scene.setOnKeyReleased(e -> GameHandler.keyReleased(e));
		primaryStage.setScene(scene);

	}
}
