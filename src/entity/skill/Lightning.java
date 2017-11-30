package entity.skill;

import entity.map.Map;
import game.model.Model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Lightning extends Skill {
	public static final double SKILL_WIDTH = 100;
	public static final double SKILL_HEIGHT = Map.FLOOR_HEIGHT;
	public static final double SKILL_DAMAGE = 0.65;
	public static final double SKILL_COOLDOWN = 8;

	private static final Image[] images = new Image[12];
	static {
		for (int i = 0; i < 12; ++i) {
			images[i] = new Image("images/skill/lightning/lightning" + i + ".png");
		}
	}

	public Lightning(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 0;
		speedY = 0;
		damage = SKILL_DAMAGE;
	}

	public Lightning() {
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 12;
		gc.drawImage(images[currentAnimation], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}

	public boolean isDead() {
		if (owner.isDead() || currentAnimation == 11) {
			Model.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}
}
