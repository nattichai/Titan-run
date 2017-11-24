package menu;

import entity.map.Map;
import game.updater.Updater;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import window.SceneManager;

public class MainMenu {
	private static final Font FONT = new Font("monospace", 40);
	private static final Image logo = new Image("images/cutscene/titan run logo.png");

	private static Pane mainMenuPane;
	private static Map map;
	private static Canvas canvas;
	private static Timeline timeline;

	public static void initialize() {
		map = new Map(0, 0, SceneManager.SCREEN_WIDTH * 2, SceneManager.SCREEN_HEIGHT);
		map.setSpeedX(-2);

		timeline = new Timeline(new KeyFrame(Duration.millis(Updater.LOOP_TIME), e -> {
			map.move();
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		canvas = new Canvas(SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(FONT);
		gc.fillText("PRESS ENTER TO CONTINUE", SceneManager.SCREEN_WIDTH / 2, 500);

		gc.drawImage(logo, (SceneManager.SCREEN_WIDTH - logo.getWidth()) / 2, 200);

		mainMenuPane = new Pane(map.getCanvas(), canvas);
	}

	public static Pane getMainMenuPane() {
		return mainMenuPane;
	}

	public static void stopTimeline() {
		timeline.stop();
	}
}
