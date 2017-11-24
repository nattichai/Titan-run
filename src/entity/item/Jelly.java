package entity.item;

import entity.characters.Characters;
import entity.characters.Player;
import entity.map.Map;
import game.property.Hitbox;
import game.storage.PlayerData;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Jelly extends Item {
	public static final double JELLY_WIDTH = 100;
	public static final double JELLY_HEIGHT = 100;
	protected static final Image[] images = new Image[25];
	static {
		for (int i = 0; i < 24; ++i) {
			images[i] = new Image("images/item/jelly" + i + ".png");
		}
	}

	public Jelly(double x, double y, double w, double h) {
		super(x, y, w, h);

		height = positionY + Map.FLOOR_HEIGHT - JELLY_HEIGHT;
		hb = new Hitbox(5, positionY + Map.FLOOR_HEIGHT - 80, 90, 80);
		currentAnimation = 0;
		isCollided = false;
	}

	public Jelly() {
		super();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // clear canvas
		currentAnimation %= 12;
		if (positionY != 0)
			currentAnimation += 12;
		gc.drawImage(images[currentAnimation++], 0, height, JELLY_WIDTH, JELLY_HEIGHT);
	}

	public void changeImage() {
		draw();
	}

	public void affectTo(Characters player) {
		// plus score by double
		PlayerData playerData = ((Player) player).getPlayerData();
		playerData.addScore(1);
	}

}
