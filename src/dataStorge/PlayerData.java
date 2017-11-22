package dataStorge;

import entity.gui.GUI;
import entity.skill.Fireball;
import entity.skill.Lightning;
import entity.skill.Meteor;
import entity.skill.Slashy;
import entity.skill.Thunderbolt;
import javafx.geometry.VPos;
import javafx.scene.text.TextAlignment;

public class PlayerData {
	public static final double[] fullCooldown = new double[100];
	static {
		fullCooldown[0] = Fireball.SKILL_COOLDOWN;
		fullCooldown[1] = Lightning.SKILL_COOLDOWN;
		fullCooldown[2] = Thunderbolt.SKILL_COOLDOWN;
		fullCooldown[3] = Meteor.SKILL_COOLDOWN;
		fullCooldown[4] = Slashy.SKILL_COOLDOWN;
	}
	
	private double score;
	private GUI scoreText;
	private static double[] cooldown;
	private static GUI[] cooldownText;
	private static GUI[] cooldownProgress;
	
	public PlayerData() {
		score = 0;
		scoreText = new GUI(-350, -50, 800, 200, "Score: " + score);
		scoreText.getCanvas().getGraphicsContext2D().setTextAlign(TextAlignment.LEFT);
		scoreText.getCanvas().getGraphicsContext2D().setTextBaseline(VPos.TOP);
		Container.getContainer().add(scoreText);
		
		cooldown = new double[100];
		cooldownText = new GUI[100];
		cooldownText[0] = new GUI(25, 125, 75, 75, "Q");
		cooldownText[1] = new GUI(105, 125, 75, 75, "W");
		cooldownText[2] = new GUI(185, 125, 75, 75, "E");
		cooldownText[3] = new GUI(265, 125, 75, 75, "R");
		cooldownText[4] = new GUI(25, 205, 75, 75, "1");
		cooldownProgress = new GUI[100];
		cooldownProgress[0] = new GUI(25, 125, 75, 75, 0.0);
		cooldownProgress[1] = new GUI(105, 125, 75, 75, 0.0);
		cooldownProgress[2] = new GUI(185, 125, 75, 75, 0.0);
		cooldownProgress[3] = new GUI(265, 125, 75, 75, 0.0);
		cooldownProgress[4] = new GUI(25, 205, 75, 75, 0.0);
		Container.getContainer().add(cooldownText[0]);
		Container.getContainer().add(cooldownText[1]);
		Container.getContainer().add(cooldownText[2]);
		Container.getContainer().add(cooldownText[3]);
		Container.getContainer().add(cooldownText[4]);
		Container.getContainer().add(cooldownProgress[0]);
		Container.getContainer().add(cooldownProgress[1]);
		Container.getContainer().add(cooldownProgress[2]);
		Container.getContainer().add(cooldownProgress[3]);
		Container.getContainer().add(cooldownProgress[4]);
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	public void addScore(double s) {
		score += s;
		score *= 1.01;
		scoreText.setText("Score: " + changeUnit(score));
		scoreText.drawText();
	}
	
	public String changeUnit(double d) {
		int count = 0;
		String number;
		String unit = "";
		while (d >= 1000) {
			d /= 1000;
			count ++;
		}
		number = String.format("%.2f ", d);
		if (count == 0) unit = "";
		else if (count == 1) unit = "K";
		else if (count == 2) unit = "M";
		else if (count == 3) unit = "B";
		else if (count == 4) unit = "T";
		else unit = ("" + (char)(97 + (count-5) / 26)) + ("" + (char)(97 + (count-5) % 26));
		return number + unit;
	}
	
	public static double getCooldown(int index) {
		return cooldown[index];
	}

	public static void resetCooldown(int index) {
		cooldown[index] = fullCooldown[index];
		cooldownProgress[index].setProgress(1);
		cooldownProgress[index].drawProgress();
	}
	
	public static void decreaseCooldown(double cd) {
		for (int i = 0; i < 5; ++i) {
			cooldown[i] -= cd;
			if (cooldown[i] < 0)
				cooldown[i] = 0;
			cooldownProgress[i].setProgress(cooldown[i] / fullCooldown[i]);
			cooldownProgress[i].drawProgress();
		}
	}
}
