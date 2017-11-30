package entity.effect;

import entity.Entity;
import entity.characters.Characters;
import game.model.Model;
import game.property.Animatable;
import game.property.Hitbox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Effect extends Entity implements Animatable {
	protected int nImage;
	protected Image[] images;
	protected int currentAnimation;
	protected int currentRound;
	protected int round;

	public Effect(double x, double y, double w, double h) {
		super(x, y, w, h);

		currentAnimation = 0;
		currentRound = 0;
		hb = new Hitbox(0, 0, 0, 0);
	}

	public Effect() {
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.drawImage(images[currentAnimation], 0, 0, width, height);
	}

	public void changeImage() {
		currentAnimation++;
		if (currentAnimation >= nImage) {
			currentAnimation -= nImage;
			currentRound++;
		}
		draw();
	}

	public boolean isCollision(Entity e) {
		return false;
	}

	public void affectTo(Characters e) {

	}

	public boolean isDead() {
		if (currentRound >= round) {
			Model.getContainer().getEffectPane().getChildren().remove(canvas);
			return true;
		}
		return false;
	}

}