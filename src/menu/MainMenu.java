package menu;

import game.GameMain;
import game.logic.Logic;
import game.model.entity.map.Map;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import window.SceneManager;

public class MainMenu {
	private static final Font FONT = new Font("monospacee", 35);

	private static Pane mainMenuPane;
	private static Map map;
	private static Canvas canvas;
	private static Timeline timeline;

	public static void initialize() {
		map = new Map(0, 0, SceneManager.SCREEN_WIDTH * 2, SceneManager.SCREEN_HEIGHT);
		map.setSpeedX(-2);
		
		timeline = new Timeline (new KeyFrame(Duration.millis(Logic.LOOP_TIME), e -> {
			map.move();
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		canvas = new Canvas(SceneManager.SCREEN_HEIGHT, SceneManager.SCREEN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(FONT);
		gc.fillText("PRESS ENTER TO CONTINUE", SceneManager.SCREEN_WIDTH / 2, SceneManager.SCREEN_HEIGHT / 2);

		mainMenuPane = new Pane(map.getCanvas(), canvas);
	}

	public static void keyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			timeline.stop();
			GameMain.newGame();
		} else if (event.getCode() == KeyCode.ESCAPE) {
			timeline.stop();
			Platform.exit();
		}
	}

	public static Pane getMainMenuPane() {
		return mainMenuPane;
	}
}
