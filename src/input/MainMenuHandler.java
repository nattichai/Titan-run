package input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import scene.MainMenu;
import scene.SceneManager;

public class MainMenuHandler {

	public static void keyPressed(KeyEvent event) {
		if (MainMenu.isTransitioning()) {
			return;
		}
		if (event.getCode() == KeyCode.ENTER) {
			MainMenu.chooseEffect();
		} else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.LEFT) {
			MainMenu.moveUp();
		} else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.RIGHT) {
			MainMenu.moveDown();
		}
	}

	public static void mouseMoved(MouseEvent event, int idx) {
		if (MainMenu.getSelectedMenu() != idx - 1) {
			MainMenu.setSelectedMenu(idx - 1);
			MainMenu.selectMenu();
		}
	}

	public static void mouseClicked(MouseEvent event) {
		if (MainMenu.isTransitioning()) {
			return;
		}
		MainMenu.chooseEffect();
	}

	public static void registerKeyPressed(KeyEvent event) {
		if (SceneManager.isTrasitioning()) {
			return;
		}
		try {
			if (event.getCode() == KeyCode.ENTER) {
				MainMenu.comfirmName();
			} else if (event.getCode() == KeyCode.BACK_SPACE) {
				MainMenu.deleteRegisterName();
			} else {
				MainMenu.addRegisterName(event);
			}
		} catch (Exception e) {
			MainMenu.setErrorMessage(e.getMessage());
		}
	}
}
