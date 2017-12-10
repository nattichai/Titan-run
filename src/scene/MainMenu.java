package scene;

import game.model.BackgroundMusic;
import game.model.GUI;
import game.model.gui.GUIImage;
import game.model.gui.GUIShape;
import game.model.gui.GUIText;
import input.MainMenuHandler;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utility.DeleteEmptyInputExeption;
import utility.LongInputException;
import utility.ShortInputException;
import utility.WrongInputException;

public class MainMenu {
	private static final GUI[] menus = new GUI[12];
	static {
		menus[0] = new GUIImage(180, 100, 640, 325, new Image("images/gui/titan run logo.png")); // titan run
		menus[1] = new GUIImage(200, 400, 600, 75, new Image("images/gui/start.png")); // start
		menus[2] = new GUIImage(200, 475, 600, 75, new Image("images/gui/ranking.png")); // rankings
		menus[3] = new GUIImage(200, 550, 600, 75, new Image("images/gui/exit.png")); // exit
		menus[4] = new GUIShape(200, 425, 600, 100, Color.WHITE, 0.3); // register box
		menus[5] = new GUIText(200, 375, 600, 50, "Enter your name", Color.WHITE, 30); // label
		menus[6] = new GUIText(200, 425, 600, 75, "", Color.BLACK, 40); // input
		menus[7] = new GUIText(200, 490, 600, 35, "", Color.BLACK, 20); // guide text
		menus[8] = new GUIImage(200, 430, 135, 90, new Image("images/gui/easy.png"), 135, 90); // easy button
		menus[9] = new GUIImage(355, 430, 135, 90, new Image("images/gui/normal.png"), 135, 90); // normal button
		menus[10] = new GUIImage(510, 430, 135, 90, new Image("images/gui/hard.png"), 135, 90); // hard button
		menus[11] = new GUIImage(665, 430, 135, 90, new Image("images/gui/insane.png"), 135, 90); // insane button
	}
	private static final AudioClip selectfx = new AudioClip(
			ClassLoader.getSystemResource("sounds/otherfx/SelectButton.wav").toString());
	private static final AudioClip movefx = new AudioClip(
			ClassLoader.getSystemResource("sounds/otherfx/Cursormove.wav").toString());

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
			FadeTransition ft = new FadeTransition(Duration.millis(1000), menus[i].getCanvas());
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.play();
			mainMenuPane.getChildren().add(menus[i].getCanvas());
		}

		if (!BackgroundMusic.isMainMenuBGMPlaying()) {
			BackgroundMusic.playMainMenuBGM();
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
				gc.setFill(Color.gray(1, 0.3));
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
			SceneManager.gotoRankings();
		} else if (selectedMenu == 2) {
			Platform.exit();
		}
	}

	public static int getSelectedMenu() {
		return selectedMenu;
	}

	public static void setSelectedMenu(int menu) {
		selectedMenu = menu;
	}

	private static void register() {
		FadeTransition ft = null;
		isTransitioning = true;
		((GUIText) menus[5]).setText("Enter your name");
		for (int i = 1; i < 4; ++i) {
			ft = new FadeTransition(Duration.millis(500), menus[i].getCanvas());
			ft.setFromValue(1.0);
			ft.setToValue(0);
			ft.play();
		}
		for (int i = 4; i < 8; ++i) {
			ft = new FadeTransition(Duration.millis(500), menus[i].getCanvas());
			ft.setFromValue(0);
			if (i == 5 || i == 6) {
				ft.setToValue(0.9);
			} else {
				ft.setToValue(0.5);
			}
			ft.play();
			mainMenuPane.getChildren().add(menus[i].getCanvas());
		}
		ft.setOnFinished(e -> {
			isTransitioning = false;
			mainMenuPane.getChildren().remove(1, 4);
			mainMenuPane.setOnKeyPressed(f -> MainMenuHandler.registerKeyPressed(f));
		});
	}

	public static void addRegisterName(KeyEvent event) throws Exception {
		GUIText registerName = getRegisterName();
		if (event.getText().matches("[A-Za-z]{1}")) {
			if (registerName.getText().length() < 12) {
				registerName.setText(registerName.getText() + event.getText().charAt(0));
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
			selectfx.play();
			selectMode();
		} else {
			throw new ShortInputException();
		}
	}

	public static void setValidMessage() {
		((GUIText) menus[7]).setText("Press [ Enter ] to confirm");
	}

	public static void setErrorMessage(String errorMessage) {
		((GUIText) menus[7]).setText(errorMessage);
	}

	public static void selectMode() {
		FadeTransition ft = null;
		isTransitioning = true;
		((GUIText) menus[5]).setText("Select difficulty");
		for (int i = 4; i < 8; ++i) {
			if (i == 5) {
				continue;
			}
			ft = new FadeTransition(Duration.millis(500), menus[i].getCanvas());
			ft.setFromValue(1.0);
			ft.setToValue(0);
			ft.play();
		}
		for (int i = 8; i < 12; ++i) {
			((GUIImage) menus[8]).setImage(new Image("images/gui/easy.png"));
			((GUIImage) menus[9]).setImage(new Image("images/gui/normal.png"));
			((GUIImage) menus[10]).setImage(new Image("images/gui/hard.png"));
			((GUIImage) menus[11]).setImage(new Image("images/gui/insane.png"));
			ft = new FadeTransition(Duration.millis(500), menus[i].getCanvas());
			ft.setFromValue(0);
			ft.setToValue(0.8);
			ft.play();
			mainMenuPane.getChildren().add(menus[i].getCanvas());
		}
		ft.setOnFinished(e -> {
			isTransitioning = false;
			mainMenuPane.getChildren().remove(1);
			mainMenuPane.getChildren().remove(3, 4);
			mainMenuPane.setOnKeyPressed(f -> MainMenuHandler.modeKeyPressed(f));
			menus[8].getCanvas().setOnMouseEntered(f -> MainMenuHandler.selectMode(8, new Image("images/gui/easy selected.png")));
			menus[9].getCanvas().setOnMouseEntered(f -> MainMenuHandler.selectMode(9, new Image("images/gui/normal selected.png")));
			menus[10].getCanvas().setOnMouseEntered(f -> MainMenuHandler.selectMode(10, new Image("images/gui/hard selected.png")));
			menus[11].getCanvas().setOnMouseEntered(f -> MainMenuHandler.selectMode(11, new Image("images/gui/insane selected.png")));
			menus[8].getCanvas().setOnMouseExited(f -> ((GUIImage) menus[8]).setImage(new Image("images/gui/easy.png")));
			menus[9].getCanvas().setOnMouseExited(f -> ((GUIImage) menus[9]).setImage(new Image("images/gui/normal.png")));
			menus[10].getCanvas().setOnMouseExited(f -> ((GUIImage) menus[10]).setImage(new Image("images/gui/hard.png")));
			menus[11].getCanvas().setOnMouseExited(f -> ((GUIImage) menus[11]).setImage(new Image("images/gui/insane.png")));
			menus[8].getCanvas().setOnMouseClicked(f -> chooseModeEffect(8, 0.5));
			menus[9].getCanvas().setOnMouseClicked(f -> chooseModeEffect(9, 1.5));
			menus[10].getCanvas().setOnMouseClicked(f -> chooseModeEffect(10, 3));
			menus[11].getCanvas().setOnMouseClicked(f -> chooseModeEffect(11, 4));
		});
	}
	
	public static void chooseModeEffect(int idx, double difficulty) {
		if (isTransitioning) {
			return;
		}
		isTransitioning = true;
		ScaleTransition st = new ScaleTransition(Duration.millis(100), menus[idx].getCanvas());
		st.setFromX(0.9);
		st.setToX(1);
		st.setFromY(0.9);
		st.setToY(1);
		st.setCycleCount(3);
		st.play();
		st.setOnFinished(e -> {
			isTransitioning = false;
			chooseMode(difficulty);
		});
	}

	private static void chooseMode(double difficulty) {
		selectfx.play();
		MainMenuHandler.chooseMode(difficulty);
	}

	public static GUI[] getMenus() {
		return menus;
	}
	
	public static void playMovefx() {
		movefx.play();
	}
	
	public static void playSelectfx() {
		selectfx.play();
	}

	public static GUIText getRegisterName() {
		return (GUIText) menus[6];
	}

	public static boolean isTransitioning() {
		return isTransitioning;
	}
}
