package scene;

import game.model.Map;
import game.model.Model;
import game.model.item.HealthPotion;
import game.model.item.Jelly;
import game.model.obstacle.AirObstacle;
import game.model.obstacle.GroundObstacle;
import game.model.obstacle.HoleObstacle;
import game.storage.CharactersData;
import game.storage.EffectsData;
import game.storage.SkillsData;
import game.updater.Updater;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoadingScreen {
	private static Thread progressThread;
	private static Thread loadingThread;
	private static Stage window;
	private static Scene scene;
	private static Pane pane;

	public static void load(Stage stage) {
		pane = new Pane(new ImageView(new Image("images/map/map2.jpg")));

		scene = new Scene(pane, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
		scene.getStylesheets().add(ClassLoader.getSystemResource("utility/style.css").toExternalForm());

		window = stage;
		window.setScene(scene);
		window.show();

		loadResource();
		runProgress();
	}

	public static void runProgress() {
		ProgressBar bar = new ProgressBar(0);
		bar.relocate(100, 700);
		bar.setPrefSize(800, 20);
		pane.getChildren().add(bar);

		progressThread = new Thread(new Runnable() {
			public void run() {
				try {
					for (int i = 0; i < 250; ++i) {
						bar.setProgress(bar.getProgress() + 0.004);

						Thread.sleep((long) Updater.LOOP_TIME);
					}
					while (!Thread.interrupted()) {
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					bar.setProgress(1);

					Platform.runLater(() -> {
						SceneManager.initialize(window);
						SceneManager.gotoMainMenu();
					});
				}
			}
		});
		progressThread.start();
	}

	public static void loadResource() {
		loadingThread = new Thread(new Runnable() {
			public void run() {
				// create container
				Model.initialize();
				// load storage data
				new CharactersData();
				new SkillsData();
				new EffectsData();
				// load assets
				new HealthPotion();
				new Jelly();
				new Map();
				new AirObstacle();
				new GroundObstacle();
				new HoleObstacle();

				progressThread.interrupt();
			}
		});
		loadingThread.start();
	}
}
