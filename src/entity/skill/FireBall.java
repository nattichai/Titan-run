package entity.skill;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Container;
import main.Main;
import utility.Pair;

public class Fireball extends Skill {
	public static final double SKILL_WIDTH = 50;
	public static final double SKILL_HEIGHT = 30;
	protected static final Image[] images = new Image[20];
	static {
		for (int i = 0; i < 20; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/fireball/Fireball" + i + ".png").toString());
		}
	}
	
	public Fireball(Pair pos, Pair size) {
		super(pos, size);
		
		speedX = 20;
		speedY = 0;
		currentAnimation = 0;
		
		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 20;
//		currentAnimation += 5;
		gc.drawImage(images[currentAnimation ++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}
	
	public boolean isDead() {
		if (position.first >= Main.SCREEN_WIDTH) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
