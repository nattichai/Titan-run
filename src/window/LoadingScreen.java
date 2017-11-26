package window;

import entity.item.HealthPotion;
import entity.item.Jelly;
import entity.map.Map;
import entity.obstacle.AirObstacle;
import entity.obstacle.GroundObstacle;
import entity.obstacle.HoleObstacle;
import entity.skill.Darkspear;
import entity.skill.Fireball;
import entity.skill.Lightning;
import entity.skill.Meteor;
import entity.skill.Shield;
import entity.skill.Slashy;
import entity.skill.Thunderbolt;
import game.model.Model;
import game.storage.Storage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScreen {
	static Thread progress;
	static Thread loader;
	static Stage window;
	static Scene scene;
	static Pane pane;
	static ProgressBar bar;
	static Canvas canvas;
	static Timeline timer;

	public static void load(Stage stage) {
		progress = new Thread(new Runnable() {
			public void run() {
				runProgress();
			}
		});
		loader = new Thread(new Runnable() {
			public void run() {
				loadResource();
			}
		});
		loader.start();
		progress.start();

		window = stage;
		canvas = new Canvas(SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
		bar = new ProgressBar(0);
		bar.relocate(100, 700);
		bar.setPrefSize(800, 20);
		pane = new Pane(canvas, bar);
		scene = new Scene(pane, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
		scene.getStylesheets().add(ClassLoader.getSystemResource("utility/style.css").toExternalForm());
		window.setScene(scene);
		window.show();
	}

	public static void runProgress() {
		Image image = Map.images[1];
		canvas.getGraphicsContext2D().drawImage(image, 0, 0);
		timer = new Timeline(new KeyFrame(Duration.millis(20), e -> {
			bar.setProgress(bar.getProgress() + 0.004);
		}));
		timer.setCycleCount(250);
		timer.play();
	}

	public static void loadResource() {
		Model.initialize();

		GraphicsContext gc = canvas.getGraphicsContext2D();
		new Storage();

		for (int i = 1; i < 6; ++i) {
			for (int j = 0; j < Storage.characters[i].nImage; ++j) {
				Image image = Storage.characters[i].images[j];
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
						(canvas.getHeight() - image.getHeight()) / 2);
			}
		}

		for (Image image : HealthPotion.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : Jelly.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : Map.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : AirObstacle.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : GroundObstacle.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : HoleObstacle.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : Darkspear.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : Fireball.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : Lightning.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : Meteor.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : Shield.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : Slashy.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		for (Image image : Thunderbolt.images) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.drawImage(image, (canvas.getWidth() - image.getWidth()) / 2,
					(canvas.getHeight() - image.getHeight()) / 2);
		}

		timer.stop();
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e -> {
			SceneManager.initialize(window);
			SceneManager.gotoMainMenu();
		});
	}
}
