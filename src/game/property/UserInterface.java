package game.property;

import entity.characters.Characters;
import entity.characters.Player;
import entity.gui.GUIImage;
import entity.gui.GUIProgress;
import entity.gui.GUIRectangle;
import entity.gui.GUIText;
import game.model.Model;
import javafx.geometry.VPos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import window.SceneManager;

public class UserInterface {
	private GUIText name;
	private GUIText scoreText;
	private GUIText[] cooldownText;
	private GUIProgress[] cooldownProgress;
	private GUIRectangle cooldownArea;
	private ProgressBar distanceBar;
	private ProgressBar hpBar;
	private ProgressBar manaBar;
	private GUIImage playerIcon;
	private GUIImage youAreDead;

	public UserInterface(Characters character) {
		hpBar = new ProgressBar(1);
		hpBar.setOpacity(0.8);

		if (character instanceof Player) {
			name = new GUIText(0, 0, 200, 30, "Po", Color.WHITE, 15);
			name.getCanvas().setOpacity(0.5);
			Model.getContainer().add(name);

			scoreText = new GUIText(-375, -75, 800, 200, "Score: " + 0, Color.WHITE, 30);
			scoreText.getCanvas().getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
			scoreText.getCanvas().getGraphicsContext2D().setTextBaseline(VPos.TOP);
			Model.getContainer().add(scoreText);

			cooldownArea = new GUIRectangle(335, 645, 325, 85, Color.BLACK, 0.1);
			Model.getContainer().add(cooldownArea);

			cooldownProgress = new GUIProgress[5];
			cooldownProgress[0] = new GUIProgress(340, 650, 75, 75, 0.0);
			cooldownProgress[1] = new GUIProgress(420, 650, 75, 75, 0.0);
			cooldownProgress[2] = new GUIProgress(500, 650, 75, 75, 0.0);
			cooldownProgress[3] = new GUIProgress(580, 650, 75, 75, 0.0);
			cooldownText = new GUIText[5];
			cooldownText[0] = new GUIText(340, 650, 75, 75, "Q", Color.BLACK, 40);
			cooldownText[1] = new GUIText(420, 650, 75, 75, "W", Color.BLACK, 40);
			cooldownText[2] = new GUIText(500, 650, 75, 75, "E", Color.BLACK, 40);
			cooldownText[3] = new GUIText(580, 650, 75, 75, "R", Color.BLACK, 40);
			Model.getContainer().add(cooldownProgress[0]);
			Model.getContainer().add(cooldownProgress[1]);
			Model.getContainer().add(cooldownProgress[2]);
			Model.getContainer().add(cooldownProgress[3]);
			Model.getContainer().add(cooldownText[0]);
			Model.getContainer().add(cooldownText[1]);
			Model.getContainer().add(cooldownText[2]);
			Model.getContainer().add(cooldownText[3]);

			distanceBar = new ProgressBar(1);
			distanceBar.setOpacity(0.8);
			distanceBar.relocate(200, 100);
			distanceBar.setPrefSize(600, 10);
			Model.getContainer().getGuiPane().getChildren().add(distanceBar);

			manaBar = new ProgressBar(1);
			manaBar.setPrefSize(200, 7);
			manaBar.setOpacity(0.8);

			playerIcon = new GUIImage(175, 80, 50, 50, new Image("images/cutscene/player icon.png"), 50, 50);
			Model.getContainer().add(playerIcon);

			youAreDead = new GUIImage(0, 0, SceneManager.SCREEN_WIDTH, SceneManager.SCREEN_HEIGHT,
					new Image("images/cutscene/you died.png"));
		}
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

	public void dead() {
		Model.getContainer().getGuiPane().getChildren().clear();
		Model.getContainer().getGuiList().clear();
		Model.getContainer().add(youAreDead);
	}
}
