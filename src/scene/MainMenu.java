package scene;

import entity.gui.GUI;
import entity.gui.GUIImage;
import entity.gui.GUIRectangle;
import entity.gui.GUIText;
import input.MainMenuHandler;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utility.InvalidInputException;

public class MainMenu {
	private static final GUI[] menus = new GUI[10];
	static {
		menus[0] = new GUIImage(180, 100, 640, 325, new Image("images/cutscene/titan run logo.png"));
		menus[1] = new GUIImage(200, 400, 600, 75, new Image("images/cutscene/start.png"));
		menus[2] = new GUIImage(200, 475, 600, 75, new Image("images/cutscene/ranking.png"));
		menus[3] = new GUIImage(200, 550, 600, 75, new Image("images/cutscene/exit.png"));
		menus[4] = new GUIRectangle(200, 425, 600, 100, Color.WHITE, 0.3);
		menus[5] = new GUIText(200, 375, 600, 50, "Enter your name", Color.WHITE, 30);
		menus[6] = new GUIText(200, 425, 600, 75, "", Color.BLACK, 40);
		menus[7] = new GUIText(200, 490, 600, 35, "", Color.BLACK, 20);
	}
	private static final Media backgroundMusic = new Media(
			ClassLoader.getSystemResource("sounds/background music1.mp3").toString());;
	private static MediaPlayer mediaPlayer = new MediaPlayer(backgroundMusic);

	private static Pane mainMenuPane;
	private static int selectedMenu;
	private static boolean isTransitioning;

	public static void initialize() {
		isTransitioning = false;

		mainMenuPane = new Pane();
		menus[1].getCanvas().setOnMouseMoved(e -> MainMenuHandler.mouseMoved(e, 1));
		menus[2].getCanvas().setOnMouseMoved(e -> MainMenuHandler.mouseMoved(e, 2));
		menus[3].getCanvas().setOnMouseMoved(e -> MainMenuHandler.mouseMoved(e, 3));
		menus[1].getCanvas().setOnMouseClicked(e -> MainMenuHandler.mouseClicked(e));
		menus[2].getCanvas().setOnMouseClicked(e -> MainMenuHandler.mouseClicked(e));
		menus[3].getCanvas().setOnMouseClicked(e -> MainMenuHandler.mouseClicked(e));
		for (int i = 0; i < 4; ++i) {
			if (i != 0) {
				menus[i].getCanvas().setTranslateX(200);
			}
			mainMenuPane.getChildren().add(menus[i].getCanvas());
			FadeTransition ft = new FadeTransition(Duration.millis(3000), menus[i].getCanvas());
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.play();
		}
		mediaPlayer.setAutoPlay(true);
		if (!mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
			mediaPlayer.play();
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

	public static void chooseEffect() {
		isTransitioning = true;
		ScaleTransition st = new ScaleTransition(Duration.millis(100), menus[selectedMenu + 1].getCanvas());
		st.setFromX(0.8);
		st.setToX(0.9);
		st.setFromY(0.8);
		st.setToY(0.9);
		st.setCycleCount(3);
		st.play();
		st.setOnFinished(e -> {
			isTransitioning = false;
			choose();
		});
	}

	private static void choose() {
		if (selectedMenu == 0) {
			register();
		} else if (selectedMenu == 1) {
			SceneManager.gotoGame();
		} else if (selectedMenu == 2) {
			Platform.exit();
		}
	}

	public static void setSelectedMenu(int menu) {
		selectedMenu = menu;
	}

	private static void register() {
		TranslateTransition tt = null;
		FadeTransition ft;
		isTransitioning = true;
		for (int i = 1; i < 4; ++i) {
			tt = new TranslateTransition(Duration.millis(1000), menus[i].getCanvas());
			double posX = menus[i].getCanvas().getTranslateX();
			tt.setFromX(posX);
			tt.setToX(posX - 100);
			tt.play();
			ft = new FadeTransition(Duration.millis(1000), menus[i].getCanvas());
			ft.setFromValue(1.0);
			ft.setToValue(0);
			ft.play();
		}
		tt.setOnFinished(e -> {
			isTransitioning = false;
			mainMenuPane.getChildren().remove(1, 4);
			mainMenuPane.setOnKeyPressed(f -> {
				try {
					MainMenuHandler.registerKeyPressed(f);
				} catch (InvalidInputException i) {
					((GUIText) menus[7]).setText(i.getInvalidText());
					menus[7].draw();
				}
			});
		});
		for (int i = 4; i < 8; ++i) {
			ft = new FadeTransition(Duration.millis(1000), menus[i].getCanvas());
			ft.setFromValue(0);
			if (i == 6) {
				ft.setToValue(0.8);
			} else {
				ft.setToValue(0.5);
			}
			ft.play();
			mainMenuPane.getChildren().add(menus[i].getCanvas());
		}
	}

	public static void typeRegisterName(char c) throws InvalidInputException {
		GUIText registerName = getRegisterName();
		if (registerName.getText().length() < 12) {
			registerName.setText(registerName.getText() + c);
			registerName.draw();
			throw new InvalidInputException(registerName.getText().length());
		} else {
			throw new InvalidInputException(registerName.getText().length() + 1);
		}
	}

	public static void deleteRegisterName() throws InvalidInputException {
		GUIText registerName = getRegisterName();
		if (registerName.getText().length() > 0) {
			registerName.setText(registerName.getText().substring(0, registerName.getText().length() - 1));
			registerName.draw();
		}
		throw new InvalidInputException(registerName.getText().length());
	}

	public static GUIText getRegisterName() {
		return (GUIText) menus[6];
	}

	public static void comfirmName() {
		if (((GUIText) menus[7]).getText() == InvalidInputException.validInput) {
			SceneManager.gotoGame();
		}
	}

	public static boolean isTransitioning() {
		return isTransitioning;
	}
}
