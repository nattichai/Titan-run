package game.storage;

import game.model.Model;
import game.model.entity.gui.GUIProgress;
import game.model.entity.gui.GUIText;
import game.model.entity.skill.Fireball;
import game.model.entity.skill.Lightning;
import game.model.entity.skill.Slashy;
import game.model.entity.skill.Thunderbolt;
import javafx.geometry.VPos;
import javafx.scene.text.TextAlignment;

public class PlayerData {
	public static final double[] fullCooldown = new double[100];
	static {
		fullCooldown[0] = Fireball.SKILL_COOLDOWN;
		fullCooldown[1] = Lightning.SKILL_COOLDOWN;
		fullCooldown[2] = Thunderbolt.SKILL_COOLDOWN;
		fullCooldown[3] = Slashy.SKILL_COOLDOWN;
	}

	private double score;
	private GUIText scoreText;
	private static double[] cooldown;
	private static GUIText[] cooldownText;
	private static GUIProgress[] cooldownProgress;

	public PlayerData() {
		score = 0;
		scoreText = new GUIText(-350, -50, 800, 200, "Score: " + score);
		scoreText.getCanvas().getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
		scoreText.getCanvas().getGraphicsContext2D().setTextBaseline(VPos.TOP);
		Model.getContainer().add(scoreText);

		cooldown = new double[100];
		cooldownText = new GUIText[100];
		cooldownText[0] = new GUIText(25, 125, 75, 75, "Q");
		cooldownText[1] = new GUIText(105, 125, 75, 75, "W");
		cooldownText[2] = new GUIText(185, 125, 75, 75, "E");
		cooldownText[3] = new GUIText(265, 125, 75, 75, "R");
		cooldownProgress = new GUIProgress[100];
		cooldownProgress[0] = new GUIProgress(25, 125, 75, 75, 0.0);
		cooldownProgress[1] = new GUIProgress(105, 125, 75, 75, 0.0);
		cooldownProgress[2] = new GUIProgress(185, 125, 75, 75, 0.0);
		cooldownProgress[3] = new GUIProgress(265, 125, 75, 75, 0.0);
		Model.getContainer().add(cooldownText[0]);
		Model.getContainer().add(cooldownText[1]);
		Model.getContainer().add(cooldownText[2]);
		Model.getContainer().add(cooldownText[3]);
		Model.getContainer().add(cooldownProgress[0]);
		Model.getContainer().add(cooldownProgress[1]);
		Model.getContainer().add(cooldownProgress[2]);
		Model.getContainer().add(cooldownProgress[3]);
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void addScore(double s) {
		score += s;
		scoreText.setText("Score: " + String.format("%.0f", score));
		scoreText.draw();
	}

	public String changeUnit(double d) {
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

	public double getCooldown(int index) {
		return cooldown[index];
	}

	public void resetCooldown(int index) {
		cooldown[index] = fullCooldown[index];
		cooldownProgress[index].setProgress(1);
		cooldownProgress[index].draw();
	}

	public void decreaseCooldown(double cd) {
		for (int i = 0; i < 4; ++i) {
			cooldown[i] -= cd;
			if (cooldown[i] < 0)
				cooldown[i] = 0;
			cooldownProgress[i].setProgress(cooldown[i] / fullCooldown[i]);
			cooldownProgress[i].draw();
		}
	}
}
