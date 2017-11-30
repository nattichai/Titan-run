package entity.skill;

import entity.Entity;
import game.model.Model;
import game.property.Side;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Beam extends Skill {
	public static final double SKILL_WIDTH = 150;
	public static final double SKILL_HEIGHT = 600;
	public static final double SKILL_DAMAGE = 2;
	public static final double SKILL_COOLDOWN = 10;

	private static final Image[] images = new Image[12];
	static {
		for (int i = 0; i < 12; ++i) {
			images[i] = new Image("images/skill/beam/beam" + i + ".png");
		}
	}

	public Beam(double x, double y) {
		super(x, y, SKILL_WIDTH, SKILL_HEIGHT);

		speedX = 0;
		speedY = 0;
		damage = SKILL_DAMAGE;

		draw();
	}

	public Beam() {
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 12;
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
		if (owner.isDead()) {
			Model.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}
