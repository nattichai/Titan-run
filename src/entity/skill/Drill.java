package entity.skill;

import game.model.Model;
import game.property.Hitbox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import window.SceneManager;

public class Drill extends Skill {
	public static final double SKILL_WIDTH = 150;
	public static final double SKILL_HEIGHT = 75;
	public static final double SKILL_DAMAGE = 0.8;
	public static final double SKILL_COOLDOWN = 10;

	private static final Image[] images = new Image[3];
	static {
		for (int i = 0; i < 3; ++i) {
			images[i] = new Image("images/skill/drill/drill" + i + ".png");
		}
	}

	public Drill(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 15;
		speedY = 0;
		damage = SKILL_DAMAGE;
		hb = new Hitbox(w / 4, h * 3 / 10, w / 2, h * 2 / 5);
	}

	public Drill() {

	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 3;
		gc.drawImage(images[currentAnimation], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}

	public boolean isDead() {
		if (owner.isDead() || positionX < -1000 || positionX >= SceneManager.SCREEN_WIDTH) {
			Model.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
