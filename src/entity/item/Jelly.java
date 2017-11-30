package entity.item;

import entity.characters.Characters;
import game.model.Model;
import game.property.Hitbox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Jelly extends Item {
	private static final double JELLY_WIDTH = 100;
	private static final double JELLY_HEIGHT = 100;
	private static final Image[] images = new Image[24];
	static {
		for (int i = 0; i < 24; ++i) {
			images[i] = new Image("images/item/jelly" + i + ".png");
		}
	}

	public Jelly(double x, double y) {
		super(x, y, JELLY_WIDTH, JELLY_HEIGHT);

		hb = new Hitbox(5, 20, 90, 80);
		currentAnimation = 0;
		isCollided = false;
	}

	public Jelly() {
		super();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		if (positionY != 500)
			currentAnimation += 12;
		gc.drawImage(images[currentAnimation], 0, 0, JELLY_WIDTH, JELLY_HEIGHT);
	}

	public void changeImage() {
		currentAnimation++;
		currentAnimation %= 12;
		draw();
	}

	public void affectTo(Characters player) {
		// add score by 100
		Model.getContainer().getPlayer().addScore(100);
	}

}
