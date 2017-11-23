package entity.skill;

import dataStorge.Container;
import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Main;
import property.Hitbox;
import property.Side;

public class Thunderbolt extends Skill {
	public static final double SKILL_WIDTH = 600;
	public static final double SKILL_HEIGHT = Main.SCREEN_HEIGHT;
	public static final double SKILL_DAMAGE = 20;
	public static final double SKILL_COOLDOWN = 15;
	
	protected static final Image[] images = new Image[40];
	static {
		for (int i = 0; i < 38; ++i) {
			images[i] = new Image(
					ClassLoader.getSystemResource("images/skill/thunderbolt/thunderbolt" + i + ".png").toString());
		}
	}

	public Thunderbolt(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 10;
		speedY = 0;
		damage = SKILL_DAMAGE;
		hb = new Hitbox(200, 0, SKILL_WIDTH - 400, SKILL_HEIGHT);
	}

	public Thunderbolt() {
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 38;
		gc.drawImage(images[currentAnimation++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}
	
	public boolean isCollision(Entity e) {
		if (side == e.getSide() || (side == Side.NEUTRAL && e.getSide() == Side.MONSTER))
			return false;
		return positionX + hb.x < e.getPositionX() + e.getHb().x + e.getHb().w && positionX + hb.x + hb.w > e.getPositionX() + e.getHb().x
				&& positionY + hb.y < e.getPositionY() + e.getHb().y + e.getHb().h && positionY + hb.y + hb.h > e.getPositionY() + e.getHb().y;
	}

	public boolean isDead() {
		if (owner.getHp() == 0.00001 || currentAnimation == 38) {
			Container.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}
}
