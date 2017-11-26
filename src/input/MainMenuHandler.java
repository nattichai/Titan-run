package input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import menu.MainMenu;
import utility.InvalidInputException;
import window.SceneManager;

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
		MainMenu.setSelectedMenu(idx - 1);
		MainMenu.selectMenu();
	}

	public static void mouseClicked(MouseEvent event) {
		if (MainMenu.isTransitioning()) {
			return;
		}
		MainMenu.chooseEffect();
	}

	public static void registerKeyPressed(KeyEvent event) throws InvalidInputException {
		if (SceneManager.isTrasitioning()) {
			return;
		}
		if (event.getCode().isLetterKey()) {
			if ((event.getText().charAt(0) >= 'a' && event.getText().charAt(0) <= 'z')
					|| (event.getText().charAt(0) >= 'A' && event.getText().charAt(0) <= 'Z')) {
				MainMenu.typeRegisterName(event.getText().charAt(0));
			} else {
				throw new InvalidInputException(-1);
			}
		} else if (event.getCode() == KeyCode.BACK_SPACE) {
			MainMenu.deleteRegisterName();
		} else if (event.getCode() == KeyCode.ENTER) {
			MainMenu.comfirmName();
		} else {
			throw new InvalidInputException(-1);
		}
	}
}
