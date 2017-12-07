package game.property;

import game.model.BackgroundMusic;
import game.model.Characters;
import game.model.Model;
import game.model.character.Player;
import game.model.gui.GUIImage;
import game.model.gui.GUIProgress;
import game.model.gui.GUIRectangle;
import game.model.gui.GUIText;
import game.updater.Animations;
import javafx.animation.Animation.Status;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import scene.GameMain;
import scene.SceneManager;

public class UserInterface {
	private static Model container;

	private GUIText name;
	private GUIText stageText;
	private GUIText scoreText;
	private GUIText[] cooldownText;
	private GUIProgress[] cooldownProgress;
	private GUIImage[] cooldownIcon;
	private GUIRectangle cooldownArea;
	private ProgressBar distanceBar;
	private ProgressBar hpBar;
	private ProgressBar manaBar;
	private GUIImage playerIcon;
	private GUIImage pauseButton;
	private static Canvas pauseArea;
	private GUIImage youAreDead;

	public UserInterface(Characters character) {
		// Load container
		container = Model.getContainer();

		// Player & Monster
		hpBar = new ProgressBar(1);
		hpBar.setOpacity(0.8);

		// Player only
		if (character instanceof Player) {
			name = new GUIText(0, 0, 200, 30, "", Color.WHITE, 15);
			name.getCanvas().setOpacity(0.5);
			container.add(name);

			stageText = new GUIText(0, 35, SceneManager.SCREEN_WIDTH, 40, "Stage " + 1, Color.WHITE, 30);
			container.add(stageText);

			scoreText = new GUIText(-360, -75, 800, 200, "Score: " + 0, Color.WHITE, 30);
			scoreText.getCanvas().getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
			scoreText.getCanvas().getGraphicsContext2D().setTextBaseline(VPos.TOP);
			container.add(scoreText);

			cooldownArea = new GUIRectangle(365, 640, 265, 90, Color.BLACK, 0.15);
			container.add(cooldownArea);

			cooldownIcon = new GUIImage[5];
			cooldownIcon[0] = new GUIImage(370, 645, 60, 60, new Image("images/skill/icon/fireball icon.png"), 60, 60);
			cooldownIcon[1] = new GUIImage(435, 645, 60, 60, new Image("images/skill/icon/lightning icon.png"), 60, 60);
			cooldownIcon[2] = new GUIImage(500, 645, 60, 60, new Image("images/skill/icon/thunderbolt icon.png"), 60,
					60);
			cooldownIcon[3] = new GUIImage(565, 645, 60, 60, new Image("images/skill/icon/slashy icon.png"), 60, 60);
			cooldownProgress = new GUIProgress[5];
			cooldownProgress[0] = new GUIProgress(370, 645, 60, 60, 0.0);
			cooldownProgress[1] = new GUIProgress(435, 645, 60, 60, 0.0);
			cooldownProgress[2] = new GUIProgress(500, 645, 60, 60, 0.0);
			cooldownProgress[3] = new GUIProgress(565, 645, 60, 60, 0.0);
			cooldownText = new GUIText[5];
			cooldownText[0] = new GUIText(370, 710, 60, 15, "Q", Color.WHITE, 15);
			cooldownText[1] = new GUIText(435, 710, 60, 15, "W", Color.WHITE, 15);
			cooldownText[2] = new GUIText(500, 710, 60, 15, "E", Color.WHITE, 15);
			cooldownText[3] = new GUIText(565, 710, 60, 15, "R", Color.WHITE, 15);
			container.add(cooldownIcon[0]);
			container.add(cooldownIcon[1]);
			container.add(cooldownIcon[2]);
			container.add(cooldownIcon[3]);
			container.add(cooldownProgress[0]);
			container.add(cooldownProgress[1]);
			container.add(cooldownProgress[2]);
			container.add(cooldownProgress[3]);
			container.add(cooldownText[0]);
			container.add(cooldownText[1]);
			container.add(cooldownText[2]);
			container.add(cooldownText[3]);

			distanceBar = new ProgressBar(1);
			distanceBar.setOpacity(0.8);
			distanceBar.relocate(200, 100);
			distanceBar.setPrefSize(600, 10);
			container.getGuiPane().getChildren().add(distanceBar);

			manaBar = new ProgressBar(1);
			manaBar.setPrefSize(200, 7);
			manaBar.setOpacity(0.8);

			playerIcon = new GUIImage(160, 80, 50, 50, new Image("images/cutscene/player icon.png"), 50, 50);
			container.add(playerIcon);

			pauseButton = new GUIImage(850, 50, 100, 100, new Image("images/cutscene/pause button.png"), 80, 80);
			GraphicsContext gc = pauseButton.getCanvas().getGraphicsContext2D();
			gc.setGlobalAlpha(0.5);
			gc.fillOval(10, 10, 80, 80);
			gc.setGlobalAlpha(1);
			pauseButton.draw();
			pauseButton.getCanvas().setOnMouseClicked(e -> GameMain.pauseOrResumeGame());
			container.add(pauseButton);

			youAreDead = new GUIImage(0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT,
					new Image("images/cutscene/you died.png"));
		}
	}

	public GUIText getName() {
		return name;
	}

	public void setName(String s) {
		name.setText(s);
		name.draw();
	}

	public void updateNamePos(double x, double y) {
		name.getCanvas().relocate(x, y);
	}

	public void updateScore(double score) {
		scoreText.setText("Score: " + String.format("%.0f", score));
		scoreText.draw();
	}

	private String changeUnit(double d) {
		int count = 0;
		String number;
		String unit = "";
		while (d >= 1000) {
			d /= 1000;
			count++;
		}
		number = String.format("%.0f ", d);
		if (count == 0)
			unit = "";
		else if (count == 1)
			unit = "K";
		else if (count == 2)
			unit = "M";
		else if (count == 3)
			unit = "B";
		else if (count == 4)
			unit = "T";
		else
			unit = ("" + (char) (97 + (count - 5) / 26)) + ("" + (char) (97 + (count - 5) % 26));
		return number + unit;
	}

	public void updateCooldown(int index, double progress) {
		cooldownProgress[index].setProgress(progress);
		cooldownProgress[index].draw();
	}

	public void updateStage(int stage) {
		stageText.setText("Stage " + stage);
		stageText.draw();
	}

	public void updateDistance(double progress) {
		if (progress < 1) {
			distanceBar.setVisible(true);
			playerIcon.getCanvas().setVisible(true);
			distanceBar.setProgress(progress);
			playerIcon.getCanvas().setTranslateX(200 + 600 * progress);
		} else {
			distanceBar.setVisible(false);
			playerIcon.getCanvas().setVisible(false);
		}
	}

	public ProgressBar getHpBar() {
		return hpBar;
	}

	public void updateHp(double posX, double posY) {
		hpBar.relocate(posX, posY);
	}

	public void updateHp(double progress) {
		hpBar.setProgress(progress);
		if (progress >= 0.75)
			hpBar.setStyle("-fx-accent:green;");
		else if (progress >= 0.5)
			hpBar.setStyle("-fx-accent:yellow;");
		else if (progress >= 0.25)
			hpBar.setStyle("-fx-accent:orange;");
		else
			hpBar.setStyle("-fx-accent:red;");
	}

	public ProgressBar getManaBar() {
		return manaBar;
	}

	public void updateMana(double posX, double posY) {
		manaBar.relocate(posX, posY);
	}

	public void updateMana(double progress) {
		manaBar.setProgress(progress);
	}

	public ProgressBar getDistanceBar() {
		return distanceBar;
	}

	private static void initializePauseArea() {
		pauseArea = new Canvas(SceneManager.SCREEN_WIDTH , SceneManager.SCREEN_HEIGHT);
		GraphicsContext gc = pauseArea.getGraphicsContext2D();
		gc.setGlobalAlpha(0.5);
		gc.fillRect(0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
		
		gc.setGlobalAlpha(1);
		gc.setFont(new Font(80));
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText("Paused", SceneManager.SCREEN_WIDTH / 2, 325);
		
		gc.setFont(new Font(30));
		gc.fillText("Press P to continue", SceneManager.SCREEN_WIDTH / 2, 425);
	}

	public static void showPauseArea() {
		if (pauseArea == null) {
			initializePauseArea();
		}
		container.add(pauseArea);
	}

	public static void closePauseArea() {
		container.remove(pauseArea);
	}

	public void dead() {
		container.getGuiPane().getChildren().clear();
		container.getGuiList().clear();
		container.add(youAreDead);
	}
}
