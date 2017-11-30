package application;

import javafx.application.Application;
import javafx.stage.Stage;
import window.LoadingScreen;

public class Main extends Application {

	public void start(Stage primaryStage) throws Exception {
		LoadingScreen.load(primaryStage);
		primaryStage.setTitle("Titan Run");
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
