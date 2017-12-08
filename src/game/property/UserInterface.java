package game.property;

import game.model.BackgroundMusic;
import game.model.Characters;
import game.model.Model;
import game.model.character.Boss;
import game.model.character.Monster;
import game.model.character.Player;
import game.model.gui.GUIImage;
import game.model.gui.GUIProgress;
import game.model.gui.GUIShape;
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
	private Model container;

	private Canvas nameArea;
	private GUIText nameText;

	private Canvas levelArea;
	private GUIText levelText;

	private ProgressBar hpBar;
	private ProgressBar manaBar;

	private GUIShape cooldownArea;
	private GUIImage[] cooldownIcon;
	private GUIProgress[] cooldownProgress;
	private GUIShape[] cooldownTextArea;
	private GUIText[] cooldownText;
	
	private GUIImage expArea;
	private GUIProgress expProgress;
	private GUIText expLevel;

	private GUIText stageText;

	private ProgressBar distanceBar;
	private GUIImage playerIcon;

	private GUIText scoreText;

	private Canvas pauseArea;
	private GUIImage pauseButton;

	private GUIImage youAreDead;

	public UserInterface(Characters character) {
		// Load container
		container = Model.getContainer();

		if (character instanceof Boss) {
			addNameUI(200);
			addLevelUI();
			addHpBarUI(400);
			hpBar.setPrefSize(400, 30);
			hpBar.relocate(300, 100);
		} else if (character instanceof Monster) {
			addNameUI(100);
			addLevelUI();
			addHpBarUI(100);
		} else if (character instanceof Player) {
			addNameUI(200);
			addLevelUI();
			addHpBarUI(200);
			addManaBarUI();
			addCooldownUI();
			addExpUI();
			addStageUI();
			addDistanceUI();
			addScoreUI();
			addCutSceneUI();
		}
	}

	private void addNameUI(double length) {
		nameArea = new Canvas(length, 30);
		GraphicsContext gc = nameArea.getGraphicsContext2D();
		gc.setGlobalAlpha(0.3);
		gc.fillPolygon(new double[] { 0, length - 9, length, 9 }, new double[] { 30, 30, 0, 0 }, 4);
		container.add(nameArea);

		nameText = new GUIText(0, 0, length, 30, "", Color.WHITE, 15);
		container.add(nameText);
	}

	private void addLevelUI() {
		levelArea = new Canvas(43, 30);
		GraphicsContext gc = levelArea.getGraphicsContext2D();
		gc.setGlobalAlpha(0.6);
		gc.fillPolygon(new double[] { 0, 34, 43, 9 }, new double[] { 30, 30, 0, 0 }, 4);
		container.add(levelArea);

		levelText = new GUIText(0, 0, 43, 30, "", Color.WHITE, 15);
		container.add(levelText);
	}

	private void addHpBarUI(double length) {
		hpBar = new ProgressBar(1);
		hpBar.setOpacity(0.8);
		hpBar.setPrefSize(length, 15);
		hpBar.setStyle("-fx-accent:green;");
		container.getGuiPane().getChildren().add(hpBar);
	}

	private void addManaBarUI() {
		manaBar = new ProgressBar(1);
		manaBar.setPrefSize(200, 7);
		manaBar.setOpacity(0.8);
		container.getGuiPane().getChildren().add(manaBar);
	}

	private void addCooldownUI() {
		cooldownArea = new GUIShape(370, 650, 345, 75, Color.rgb(0x15, 0x3b, 0x48), 0.3);
		container.add(cooldownArea);

		double startPosX = 435;
		double space = 70;
		double posY = 660;
		cooldownIcon = new GUIImage[4];
		cooldownText = new GUIText[4];
		cooldownTextArea = new GUIShape[4];
		cooldownProgress = new GUIProgress[4];
		cooldownIcon[0] = new GUIImage(startPosX, posY, 60, 60, new Image("images/gui/fireball icon.png"), 60, 60);
		cooldownIcon[1] = new GUIImage(startPosX + 1 * space, posY, 60, 60, new Image("images/gui/lightning icon.png"), 60, 60);
		cooldownIcon[2] = new GUIImage(startPosX + 2 * space, posY, 60, 60, new Image("images/gui/thunderbolt icon.png"), 60, 60);
		cooldownIcon[3] = new GUIImage(startPosX + 3 * space, posY, 60, 60, new Image("images/gui/slashy icon.png"), 60, 60);
		cooldownText[0] = new GUIText(startPosX, posY, 20, 20, "Q", Color.WHITE, 15);
		cooldownText[1] = new GUIText(startPosX + 1 * space, posY, 20, 20, "W", Color.WHITE, 15);
		cooldownText[2] = new GUIText(startPosX + 2 * space, posY, 20, 20, "E", Color.WHITE, 15);
		cooldownText[3] = new GUIText(startPosX + 3 * space, posY, 20, 20, "R", Color.WHITE, 15);

		for (int i = 0; i < 4; ++i) {
			cooldownProgress[i] = new GUIProgress(startPosX + i * space, posY, 60, 60, 0);
			cooldownTextArea[i] = new GUIShape(startPosX + i * space, posY, 20, 20, Color.BLACK, 0.6, "Oval");
			container.add(cooldownIcon[i]);
			container.add(cooldownProgress[i]);
			container.add(cooldownTextArea[i]);
			container.add(cooldownText[i]);
		}
	}
	
	private void addExpUI() {
		expProgress = new GUIProgress(318.5, 628, 104, 104, Color.YELLOW, -35, 0, 125);
		container.add(expProgress);
		
		expArea = new GUIImage(325, 625, 100, 100, new Image("images/gui/player exp bar.png"), 100, 100);
		container.add(expArea);
		
		expLevel = new GUIText(388, 696, 28, 28, "", Color.WHITE, 15);
		container.add(expLevel);
	}

	private void addStageUI() {
		stageText = new GUIText(0, 35, SceneManager.SCREEN_WIDTH, 40, "Stage " + 1, Color.WHITE, 30);
		container.add(stageText);
	}

	private void addDistanceUI() {
		distanceBar = new ProgressBar(1);
		distanceBar.setOpacity(0.8);
		distanceBar.relocate(200, 100);
		distanceBar.setPrefSize(600, 10);
		container.getGuiPane().getChildren().add(distanceBar);

		playerIcon = new GUIImage(160, 80, 50, 50, new Image("images/gui/player icon.png"), 50, 50);
		container.add(playerIcon);
	}

	private void addScoreUI() {
		scoreText = new GUIText(-360, -75, 800, 200, "Score: 0", Color.WHITE, 30);
		scoreText.getCanvas().getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
		scoreText.getCanvas().getGraphicsContext2D().setTextBaseline(VPos.TOP);
		container.add(scoreText);
	}

	private void addCutSceneUI() {
		pauseButton = new GUIImage(850, 50, 100, 100, new Image("images/gui/pause button.png"), 80, 80);
		GraphicsContext gc = pauseButton.getCanvas().getGraphicsContext2D();
		gc.setGlobalAlpha(0.5);
		gc.fillOval(10, 10, 80, 80);
		gc.setGlobalAlpha(1);
		pauseButton.draw();
		pauseButton.getCanvas().setOnMouseClicked(e -> GameMain.pauseOrResumeGame());
		container.add(pauseButton);

		youAreDead = new GUIImage(0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT,
				new Image("images/gui/you died.png"));
	}

	public void updateNamePos(double x, double y) {
		nameArea.relocate(x, y);
		nameText.getCanvas().relocate(x, y);
	}

	public void updateLevelPos(double x, double y) {
		levelArea.relocate(x, y);
		levelText.getCanvas().relocate(x, y);
	}

	public void updateHpPos(double posX, double posY) {
		hpBar.relocate(posX, posY);
	}

	public void updateManaPos(double posX, double posY) {
		manaBar.relocate(posX, posY);
	}

	public void updateName(String name) {
		nameText.setText(name);
		nameText.draw();
	}

	public void updateLevel(int level) {
		levelText.setText("" + level);
		levelText.draw();
		
		if (expLevel != null) {
			expLevel.setText("" + level);
			expLevel.draw();
		}
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

	public void updateMana(double progress) {
		manaBar.setProgress(progress);
	}

	public void updateCooldown(int index, double progress) {
		cooldownProgress[index].setProgress(progress);
		cooldownProgress[index].draw();
	}
	
	public void updateExp(double progress) {
		expProgress.setProgress(progress);
		expProgress.draw();
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

	public void updateScore(double score) {
		scoreText.setText("Score: " + String.format("%.0f", score));
		scoreText.draw();
	}

	private void initializePauseArea() {
		pauseArea = new Canvas(SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT);
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

	public void showPauseArea() {
		if (pauseArea == null) {
			initializePauseArea();
		}
		container.add(pauseArea);
	}

	public void closePauseArea() {
		container.remove(pauseArea);
	}

	public void dead(Characters character) {
		if (character instanceof Monster) {
			Model.getContainer().getGuiPane().getChildren().removeAll(nameText.getCanvas(), nameArea,
					levelText.getCanvas(), levelArea, hpBar);
		} else if (character instanceof Player) {
			container.getGuiPane().getChildren().clear();
			container.getGuiList().clear();
			container.add(youAreDead);
		}
	}
}
