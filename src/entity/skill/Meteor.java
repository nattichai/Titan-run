package entity.skill;

import entity.Entity;
import game.model.Model;
import game.property.Hitbox;
import game.property.Side;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import window.SceneManager;

public class Meteor extends Skill {
	public static final double SKILL_WIDTH = 800;
	public static final double SKILL_HEIGHT = 270;
	public static final double SKILL_DAMAGE = 2.5;
	public static final double SKILL_COOLDOWN = 15;

	private static final Image[] images = new Image[60];
	static {
		for (int i = 0; i < 60; ++i) {
			images[i] = new Image("images/skill/meteor/meteor" + i + ".png");
		}
	}

	public Meteor(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 12;
		speedY = 9;
		damage = SKILL_DAMAGE;
		hb = new Hitbox(0, 250, 270, 270);

		draw();
	}

	public Meteor() {
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 60;
		gc.drawImage(images[currentAnimation], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}

	public boolean isCollision(Entity e) {
		if (side == e.getSide() || (side == Side.NEUTRAL && e.getSide() == Side.MONSTER))
			return false;
		return positionX + hb.x < e.getPositionX() + e.getHb().x + e.getHb().w
				&& positionX + hb.x + hb.w > e.getPositionX() + e.getHb().x
				&& positionY + hb.y < e.getPositionY() + e.getHb().y + e.getHb().h
				&& positionY + hb.y + hb.h > e.getPositionY() + e.getHb().y;
	}

	public boolean isDead() {
		if (owner.isDead() || positionX >= SceneManager.SCREEN_WIDTH || positionY >= SceneManager.SCREEN_HEIGHT) {
			Model.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
