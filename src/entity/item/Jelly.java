package entity.item;

import entity.map.Map;
import entity.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.State;
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
	
	public Jelly(Pair pos, Pair size) {
		super(pos, size);
		
		currentAnimation = 0;
		isCollected = false;
		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 12;
		if (position.second != 0)
			currentAnimation += 12;
		height = position.second + Map.FLOOR_HEIGHT - JELLY_HEIGHT;
		gc.drawImage(images[currentAnimation ++], 0, height, JELLY_WIDTH, JELLY_HEIGHT);
	}
	
	public void changeImage() {
		draw();
	}
	
	public void effect(Player player) {
		player.addScore(player.getScore());
	}

	public boolean isCollision(Pair pos, State state) {
		if (		(position.first + 20 <=	pos.first + Player.PLAYER_WIDTH
									&& pos.first + Player.PLAYER_WIDTH	<= position.first + canvas.getWidth() - 20)
			||	(position.first + 20 <= pos.first
									&& pos.first							<= position.first + canvas.getWidth() - 20)	) {
			
			if (pos.second <= height && height <= pos.second + Player.PLAYER_HEIGHT) {
				return true;
			}
		}
		return false;
	}

}
