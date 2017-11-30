package window;

import entity.effect.Charge;
import entity.item.HealthPotion;
import entity.item.Jelly;
import entity.map.Map;
import entity.obstacle.AirObstacle;
import entity.obstacle.GroundObstacle;
import entity.obstacle.HoleObstacle;
import entity.skill.Beam;
import entity.skill.Darkspear;
import entity.skill.Drill;
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
		timer = new Timeline(new KeyFrame(Duration.millis(20), e -> {
			bar.setProgress(bar.getProgress() + 0.004);
		}));
		timer.setCycleCount(250);
		timer.play();
	}

	public static void loadResource() {
		Model.initialize();
		new Storage();
		new HealthPotion();
		new Jelly();
		new Map();
		new AirObstacle();
		new GroundObstacle();
		new HoleObstacle();
		new Darkspear();
		new Fireball();
		new Lightning();
		new Meteor();
		new Shield();
		new Slashy();
		new Thunderbolt();
		new Drill();
		new Charge();
		new Beam();

		timer.stop();
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e -> {
			SceneManager.initialize(window);
			SceneManager.gotoMainMenu();
		});
	}
}
