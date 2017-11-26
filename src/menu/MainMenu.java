package menu;

import entity.gui.GUIImage;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import window.SceneManager;

public class MainMenu {
	private static final GUIImage[] menus = new GUIImage[10];
	static {
		menus[0] = new GUIImage(180, 100, 640, 325, new Image("images/cutscene/titan run logo.png"));
		menus[1] = new GUIImage(200, 400, 600, 75, new Image("images/cutscene/start.png"));
		menus[2] = new GUIImage(200, 475, 600, 75, new Image("images/cutscene/ranking.png"));
		menus[3] = new GUIImage(200, 550, 600, 75, new Image("images/cutscene/exit.png"));
	}

	private static Pane mainMenuPane;
	private static int selectedMenu;

	public static void initialize() {
		mainMenuPane = new Pane();
		for (int i = 0; i < 4; ++i) {
			mainMenuPane.getChildren().add(menus[i].getCanvas());
		}
		selectedMenu = 0;
		selectMenu();
	}

	public static Pane getMainMenu() {
		return mainMenuPane;
	}

	public static void moveDown() {
		selectedMenu++;
		selectedMenu %= 3;
		selectMenu();
	}

	public static void moveUp() {
		if (selectedMenu == 0) {
			selectedMenu = 2;
		} else {
			selectedMenu--;
		}
		selectMenu();
	}

	public static void selectMenu() {
		for (int i = 1; i < 4; ++i) {
			Canvas canvas = menus[i].getCanvas();
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

			if (selectedMenu + 1 == i) {
				menus[i].getCanvas().setScaleX(0.8);
				menus[i].getCanvas().setScaleY(0.8);
				gc.setFill(Color.gray(0, 0.25));
				gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				menus[i].draw();
			} else {
				menus[i].getCanvas().setScaleX(0.6);
				menus[i].getCanvas().setScaleY(0.6);
				menus[i].draw();
			}
		}
	}

	public static void choose() {
		if (selectedMenu == 0) {
			SceneManager.gotoGame();
		} else if (selectedMenu == 1) {
			SceneManager.gotoGame();
		} else if (selectedMenu == 2) {
			Platform.exit();
		}
	}
}
