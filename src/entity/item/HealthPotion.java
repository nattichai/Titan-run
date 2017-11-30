package entity.item;

import entity.characters.Characters;
import game.model.Model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HealthPotion extends Item {
	private static final double POTION_WIDTH = 100;
	private static final double POTION_HEIGHT = 100;
	private static final Image[] images = new Image[48];
	static {
		for (int i = 0; i < 48; ++i) {
			images[i] = new Image("images/item/health potion" + i + ".png");
		}
	}

	public HealthPotion(double x, double y) {
		super(x, y, POTION_WIDTH, POTION_HEIGHT);

		currentAnimation = 0;
	}

	public HealthPotion() {
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		gc.drawImage(images[currentAnimation], 0, 0, POTION_WIDTH, POTION_HEIGHT);
	}

	public void changeImage() {
		currentAnimation++;
		currentAnimation %= images.length;
		draw();
	}

	public void affectTo(Characters player) {
		// increase hp by 15
		player.setHp(player.getHp() + 15);
		// add score by 500
		Model.getContainer().getPlayer().addScore(500);
	}
}
