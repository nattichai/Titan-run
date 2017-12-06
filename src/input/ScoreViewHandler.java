package input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import scene.SceneManager;

public class ScoreViewHandler {

	public static void keyPressed(KeyEvent event) {
		if (SceneManager.isTrasitioning()) {
			return;
		}

		if (event.getCode() == KeyCode.ENTER) {
			SceneManager.gotoMainMenu();
		}
	}
}
