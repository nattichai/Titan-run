package entity.item;

import entity.characters.player.Player;
import entity.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.State;
import utility.Hitbox;
import utility.Pair;

public class Jelly extends Item{
	public static final double JELLY_WIDTH = 100;
	public static final double JELLY_HEIGHT = 100;
	protected static final Image[] images = new Image[25];
	static {
		for (int i = 0; i < 24; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/item/jelly" + i + ".png").toString());
		}
	}
	
	public Jelly(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		height = positionY + Map.FLOOR_HEIGHT - JELLY_HEIGHT;
		hb = new Hitbox(5, positionY + Map.FLOOR_HEIGHT - 80, 90, 80);
		currentAnimation = 0;
		isCollected = false;
		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 12;
		if (positionY != 0)
			currentAnimation += 12;
		gc.drawImage(images[currentAnimation ++], 0, height, JELLY_WIDTH, JELLY_HEIGHT);
	}
	
	public void changeImage() {
		draw();
	}
	
	public void effect(Player player) {
		player.addScore(player.getScore());
	}

	public boolean isCollision(Pair pos, State state) {
		if (		(positionX + 30 <=	pos.first + Player.PLAYER_WIDTH
									&& pos.first + Player.PLAYER_WIDTH	<= positionX + canvas.getWidth())
			||	(positionX + 30 <= pos.first
									&& pos.first							<= positionX + canvas.getWidth())	) {
			
			if (pos.second <= height && height <= pos.second + Player.PLAYER_HEIGHT) {
				return true;
			}
		}
		return false;
	}

}
