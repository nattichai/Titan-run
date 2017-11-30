package entity.skill;

import entity.Entity;
import game.model.Model;
import game.property.Side;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import window.SceneManager;

public class Slashy extends Skill {
	public static final double SKILL_WIDTH = SceneManager.SCREEN_WIDTH;
	public static final double SKILL_HEIGHT = SceneManager.SCREEN_HEIGHT;
	public static final double SKILL_DAMAGE = 3;
	public static final double SKILL_COOLDOWN = 24;

	private static final Image[] images = new Image[20];
	static {
		for (int i = 0; i < 20; ++i) {
			images[i] = new Image("images/skill/slashy/slashy" + i + ".png");
		}
	}

	public Slashy(double x, double y, double w, double h) {
		super(x, y, w, h);

		speedX = 0;
		speedY = 0;
		damage = SKILL_DAMAGE;
	}

	public Slashy() {
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 20;
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
			Timeline timer = new Timeline(new KeyFrame(Duration.millis(20)));
			timer.setCycleCount(1);
			timer.setOnFinished(f -> isCollided = false);
			timer.play();
			return true;
		}
		return false;
	}

	public boolean isDead() {
		if (owner.isDead() || currentAnimation == 19) {
			Model.getContainer().getSkillPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}
}
