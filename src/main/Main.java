package main;

import controller.Controller;
import controller.Handler;
import dataStorge.Container;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	public static final double SCREEN_WIDTH = 1000;
	public static final double SCREEN_HEIGHT = 750;
	public static final double FRAME_RATE = 50;
	public static final double ANIMATE_RATE = 20;
	public static double SPEED = 10;

	private static Pane root;
	private static Timeline timerUpdate;
	private static Timeline timerAnimate;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		root = new Pane();

		Container.initialize();

		timerUpdate = new Timeline(new KeyFrame(Duration.millis(1000 / FRAME_RATE), e -> {
			Controller.update();
		}));
		timerUpdate.setCycleCount(Animation.INDEFINITE);
		timerUpdate.play();

		timerAnimate = new Timeline(new KeyFrame(Duration.millis(1000 / ANIMATE_RATE), e -> {
			Controller.animate();
		}));
		timerAnimate.setCycleCount(Animation.INDEFINITE);
		timerAnimate.play();

		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		scene.getStylesheets().add(ClassLoader.getSystemResource("utility/style.css").toExternalForm());
		scene.setOnKeyPressed(event -> Handler.keyPressed(event));
		scene.setOnKeyReleased(event -> Handler.keyReleased(event));

		stage.setTitle("Titan Run");
		stage.setScene(scene);
		stage.setResizable(false);
//		stage.setFullScreen(true);
		stage.show();
		stage.setOnCloseRequest(event -> {
			timerUpdate.stop();
			timerAnimate.stop();
		});
	}

	public static Pane getRoot() {
		return root;
	}

	public static Timeline getTimerUpdate() {
		return timerUpdate;
	}

	public static Timeline getTimerAnimate() {
		return timerAnimate;
	}

}
