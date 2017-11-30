package entity.skill;

import entity.Entity;
import game.model.Model;
import game.property.Hitbox;
import game.property.Side;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import window.SceneManager;

public class Thunderbolt extends Skill {
	public static final double SKILL_WIDTH = 600;
	public static final double SKILL_HEIGHT = SceneManager.SCREEN_HEIGHT;
	public static final double SKILL_DAMAGE = 1.5;
	public static final double SKILL_COOLDOWN = 15;

	private static final Image[] images = new Image[38];
	static {
		for (int i = 0; i < 38; ++i) {
			images[i] = new Image("images/skill/thunderbolt/thunderbolt" + i + ".png");
		}
	}

	public Thunderbolt(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 8;
		speedY = 0;
		damage = SKILL_DAMAGE;
		hb = new Hitbox(200, 0, SKILL_WIDTH - 400, SKILL_HEIGHT);
	}

	public Thunderbolt() {

	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 38;
		gc.drawImage(images[currentAnimation], 0, 0, SKILL_WIDTH, SKILL_HEIGHT);
	}

	public boolean isCollision(Entity e) {
		if ((isCollided && e.isCollided()) || side == e.getSide()
				|| (side == Side.NEUTRAL && e.getSide() == Side.MONSTER))
			return false;
		if (positionX + hb.x < e.getPositionX() + e.getHb().x + e.getHb().w
				&& positionX + hb.x + hb.w > e.getPositionX() + e.getHb().x
				&& positionY + hb.y < e.getPositionY() + e.getHb().y + e.getHb().h
				&& positionY + hb.y + hb.h > e.getPositionY() + e.getHb().y) {
			isCollided = true;
			e.setCollided(true);
			Timeline timer = new Timeline(new KeyFrame(Duration.millis(100)));
			timer.setCycleCount(1);
			timer.setOnFinished(f -> isCollided = false);
			timer.play();
			return true;
		}
		return false;
	}

	public boolean isDead() {
		if (owner.isDead()) {
			Model.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}
}
