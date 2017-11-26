package input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import menu.MainMenu;
import window.SceneManager;

public class MainMenuHandler {
	public static void keyPressed(KeyEvent event) {
		if (SceneManager.isTrasitioning())
			return;

		if (event.getCode() == KeyCode.ENTER) {
			MainMenu.choose();
		} else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.LEFT) {
			MainMenu.moveUp();
		} else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.RIGHT) {
			MainMenu.moveDown();
		}
	}
}
