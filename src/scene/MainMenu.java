package scene;

import game.model.GUI;
import game.model.gui.GUIImage;
import game.model.gui.GUIRectangle;
import game.model.gui.GUIText;
import input.MainMenuHandler;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utility.DeleteEmptyInputExeption;
import utility.LongInputException;
import utility.ShortInputException;
import utility.WrongInputException;

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
	private static final AudioClip backgroundMusic = new AudioClip(
			ClassLoader.getSystemResource("sounds/songs/07_Opening Stage Zero.mp3").toString());;
	private static final AudioClip selectfx = new AudioClip(
			ClassLoader.getSystemResource("sounds/otherfx/SelectButton.wav").toString());;
	private static final AudioClip movefx = new AudioClip(
			ClassLoader.getSystemResource("sounds/otherfx/Cursormove.wav").toString());;

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
		if (!backgroundMusic.isPlaying()){
			backgroundMusic.play();
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
		movefx.play();
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
		selectfx.play();
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
			mainMenuPane.setOnKeyPressed(f -> MainMenuHandler.registerKeyPressed(f));
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

	public static void addRegisterName(KeyEvent event) throws Exception {
		GUIText registerName = getRegisterName();
		if (event.getText().matches("[A-Za-z]{1}")) {
			if (registerName.getText().length() < 12) {
				registerName.setText(registerName.getText() + event.getText().charAt(0));
				registerName.draw();
				if (registerName.getText().length() >= 2) {
					setValidMessage();
				} else {
					throw new ShortInputException();
				}
			} else {
				throw new LongInputException();
			}
		} else {
			throw new WrongInputException();
		}
	}

	public static void deleteRegisterName() throws Exception {
		GUIText registerName = getRegisterName();
		if (registerName.getText().length() > 0) {
			registerName.setText(registerName.getText().substring(0, registerName.getText().length() - 1));
			registerName.draw();
			if (registerName.getText().length() >= 2) {
				setValidMessage();
			} else {
				throw new ShortInputException();
			}
		} else {
			throw new DeleteEmptyInputExeption();
		}
	}

	public static void comfirmName() throws Exception {
		GUIText registerName = getRegisterName();
		if (registerName.getText().length() >= 2) {
			SceneManager.gotoGame();
		} else {
			throw new ShortInputException();
		}
	}

	public static void setValidMessage() {
		((GUIText) menus[7]).setText("press enter to confirm");
		menus[7].draw();
	}

	public static void setErrorMessage(String errorMessage) {
		((GUIText) menus[7]).setText(errorMessage);
		menus[7].draw();
	}

	public static GUIText getRegisterName() {
		return (GUIText) menus[6];
	}

	public static boolean isTransitioning() {
		return isTransitioning;
	}
}
