package entity.skill;

import java.util.Random;

import dataStorge.Container;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Main;

public class Fireball extends Skill {
	public static final double SKILL_WIDTH = 50;
	public static final double SKILL_HEIGHT = 30;
	public static final double SKILL_DAMAGE = 5;
	public static final double SKILL_COOLDOWN = 0.10;
	protected static final Image[] images = new Image[10];
	static {
		for (int i = 0; i < 10; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/fireball/fireball" + i + ".png").toString());
		}
	}
	
	public Fireball(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		speedX = 20;
		speedY = 0;
		damage = SKILL_DAMAGE;
		currentAnimation = new Random().nextInt(10);
	}

	public Fireball() {
		
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 10;
		gc.drawImage(images[currentAnimation ++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}
	
	public boolean isDead() {
		if (owner.getHp() == 0.00001 || isCollided || positionX < -100 || positionX >= Main.SCREEN_WIDTH) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
