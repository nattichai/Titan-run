package entity.skill;

import dataStorge.Container;
import entity.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Lightning extends Skill {
	public static final double SKILL_WIDTH = 100;
	public static final double SKILL_HEIGHT = Map.FLOOR_HEIGHT;
	public static final double SKILL_DAMAGE = 20;
	public static final double SKILL_COOLDOWN = 8;
	
	protected static final Image[] images = new Image[20];
	static {
		for (int i = 0; i < 12; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/lightning/lightning" + i + ".png").toString());
		}
	}
	
	public Lightning(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		speedX = 0;
		speedY = 0;
		damage = SKILL_DAMAGE;
	}

	public Lightning() {}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 12;
		gc.drawImage(images[currentAnimation ++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}
	
	public boolean isDead() {
		if (owner.getHp() == 0.00001 || currentAnimation == 11) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}
}
