package entity.skill;

import dataStorge.Container;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Main;
import property.Hitbox;

public class Darkspear extends Skill {
	public static final double SKILL_WIDTH = 600;
	public static final double SKILL_HEIGHT = 600;
	public static final double SKILL_DAMAGE = 20;
	public static final double SKILL_COOLDOWN = 10;
	
	protected static final Image[] images = new Image[16];
	static {
		for (int i = 0; i < 16; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/darkspear/darkspear" + i + ".png").toString());
		}
	}
	
	public Darkspear(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		speedX = 20;
		speedY = 0;
		damage = SKILL_DAMAGE;
		hb = new Hitbox(250, 250, SKILL_WIDTH - 500, SKILL_HEIGHT - 300);
	}

	public Darkspear() {
		
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 16;
		gc.drawImage(images[currentAnimation ++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}
	
	public boolean isDead() {
		if (owner.getHp() == 0.00001 || positionX < -1000 || positionX >= Main.SCREEN_WIDTH) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
