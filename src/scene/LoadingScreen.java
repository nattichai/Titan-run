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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreen {
	static Stage window;
	static Scene scene;
	static Pane pane;
	static ProgressBar bar;
	static Timeline timer;

	public static void load(Stage stage) {
		new Thread(new Runnable() {
			public void run() {
				runProgress();
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				loadResource();
			}
		}).start();

		bar = new ProgressBar(0);
		bar.relocate(100, 700);
		bar.setPrefSize(800, 20);

		pane = new Pane(new ImageView(new Image("images/map/map2.jpg")), bar);

		scene = new Scene(pane, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
		scene.getStylesheets().add(ClassLoader.getSystemResource("utility/style.css").toExternalForm());

		window = stage;
		window.setScene(scene);
		window.show();
	}

	public static void runProgress() {
		timer = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME), e -> {
			bar.setProgress(bar.getProgress() + 0.004);
		}));
		timer.setCycleCount(250);
		timer.play();

	}

	public static void loadResource() {
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

		bar.setProgress(1);
		timer.stop();
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e -> {
			SceneManager.initialize(window);
			SceneManager.gotoMainMenu();
		});
	}
}
