package input;

import game.GameMain;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import menu.MainMenu;

public class MainMenuHandler {

	public static void keyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			MainMenu.stopTimeline();
			GameMain.newGame();
		} else if (event.getCode() == KeyCode.ESCAPE) {
			MainMenu.stopTimeline();
			Platform.exit();
		}
	}
}
