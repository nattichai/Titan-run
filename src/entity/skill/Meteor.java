package entity.skill;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Container;
import main.Main;
import utility.Pair;

public class Meteor extends Skill {
	public static final double SKILL_WIDTH = 200;
	public static final double SKILL_HEIGHT = 200;
	static {
		for (int i = 0; i < 8; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/meteor/meteor" + i + ".png").toString());
		}
	}

	public Meteor(Pair pos, Pair size) {
		super(pos, size);

		speedX = 8;
		speedY = -30;
		currentAnimation = 0;

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 8;
		gc.drawImage(images[currentAnimation ++], 0, 0);
		canvas.setRotate(Math.atan(speedY / speedX) / Math.PI * 180);
	}
	
	public void changeSpeed(double accelX, double accelY) {
		speedX += accelX;
		speedY += accelY;
	}

	public boolean isDead() {
		if (position.first >= Main.SCREEN_WIDTH || position.second >= Main.SCREEN_HEIGHT) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
