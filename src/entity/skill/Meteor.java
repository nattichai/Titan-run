package entity.skill;

import dataStorge.Container;
import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Main;
import property.Hitbox;

public class Meteor extends Skill {
	public static final double SKILL_WIDTH = 500;
	public static final double SKILL_HEIGHT = 500;
	public static final double SKILL_DAMAGE = 999;
	public static final double SKILL_COOLDOWN = 15;
	protected static final Image[] images = new Image[20];
	static {
		for (int i = 0; i < 8; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/skill/meteor/meteor" + i + ".png").toString());
		}
	}

	public Meteor(double x, double y, double w, double h) {
		super(x, y, w, h);

		hb = new Hitbox(0, 0, w, h);
		speedX = 5;
		speedY = 5;
		currentAnimation = 4;
		canvas.setRotate(45);

		draw();
	}

	public Meteor() {
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 4;
		gc.drawImage(images[currentAnimation ++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}
	
	public boolean isCollision(Entity e) {
		return positionX + hb.x < e.getPositionX() + e.getHb().x + e.getHb().w && positionX + hb.x + hb.w > e.getPositionX() + e.getHb().x
				&& positionY + hb.y < e.getPositionY() + e.getHb().y + e.getHb().h && positionY + hb.y + hb.h > e.getPositionY() + e.getHb().y;
	}

	public boolean isDead() {
		if (positionX >= Main.SCREEN_WIDTH || positionY >= Main.SCREEN_HEIGHT) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
