package input;

import game.model.gui.GUIImage;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import scene.GameMain;
import scene.MainMenu;
import scene.SceneManager;

public class MainMenuHandler {

	public static void keyPressed(KeyEvent event) {
		if (MainMenu.isTransitioning() || SceneManager.isTrasitioning()) {
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
		if (MainMenu.isTransitioning() || SceneManager.isTrasitioning()) {
			return;
		}
		if (MainMenu.getSelectedMenu() != idx - 1) {
			MainMenu.setSelectedMenu(idx - 1);
			MainMenu.selectMenu();
		}
	}

	public static void mouseClicked(MouseEvent event) {
		if (MainMenu.isTransitioning() || SceneManager.isTrasitioning()) {
			return;
		}
		MainMenu.chooseEffect();
	}

	public static void registerKeyPressed(KeyEvent event) {
		if (MainMenu.isTransitioning() || SceneManager.isTrasitioning()) {
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
	
	public static void selectMode(int idx, Image image) {
		if (MainMenu.isTransitioning() || SceneManager.isTrasitioning()) {
			return;
		}
		MainMenu.playMovefx();
		((GUIImage) MainMenu.getMenus()[idx]).setImage(image);
	}
	
	public static void modeKeyPressed(KeyEvent event) {
		if (MainMenu.isTransitioning() || SceneManager.isTrasitioning()) {
			return;
		}
		if (event.getCode() == KeyCode.E) {
			((GUIImage) MainMenu.getMenus()[8]).setImage(new Image("images/gui/easy selected.png"));
			MainMenu.chooseModeEffect(8, 0.5);
		} else if (event.getCode() == KeyCode.N) {
			((GUIImage) MainMenu.getMenus()[9]).setImage(new Image("images/gui/normal selected.png"));
			MainMenu.chooseModeEffect(9, 1.5);
		} else if (event.getCode() == KeyCode.H) {
			((GUIImage) MainMenu.getMenus()[10]).setImage(new Image("images/gui/hard selected.png"));
			MainMenu.chooseModeEffect(10, 3);
		} else if (event.getCode() == KeyCode.I) {
			((GUIImage) MainMenu.getMenus()[11]).setImage(new Image("images/gui/insane selected.png"));
			MainMenu.chooseModeEffect(11, 4);
		}
	}
	
	public static void chooseMode(double difficulty) {
		if (MainMenu.isTransitioning() || SceneManager.isTrasitioning()) {
			return;
		}
		GameMain.setDifficulty(difficulty);
		SceneManager.gotoGame();
	}
}
