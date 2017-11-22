package entity.skill;

import dataStorge.Container;
import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Main;

public class Slashy extends Skill {
	public static final double SKILL_WIDTH = Main.SCREEN_WIDTH;
	public static final double SKILL_HEIGHT = Main.SCREEN_HEIGHT;
	public static final double SKILL_DAMAGE = 10;
	public static final double SKILL_COOLDOWN = 20;
	protected static final Image[] images = new Image[40];
	static {
		for (int i = 0; i < 20; ++i) {
			images[i] = new Image(
					ClassLoader.getSystemResource("images/skill/slashy/slashy" + i + ".png").toString());
		}
	}

	public Slashy(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 0;
		speedY = 0;
	}

	public Slashy() {
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 38;
		gc.drawImage(images[currentAnimation++], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}
	
	public boolean isCollision(Entity e) {
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
