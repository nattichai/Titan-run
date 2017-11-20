package entity.item;

import entity.characters.player.Player;
import entity.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.State;
import utility.Pair;

public class HealthPotion extends Item{
	public static final double POTION_WIDTH = 100;
	public static final double POTION_HEIGHT = 100;
	protected static final Image[] images = new Image[50];
	static {
		for (int i = 0; i < 48; ++i) {
			images[i] = new Image(ClassLoader.getSystemResource("images/item/health potion" + i + ".png").toString());
		}
	}
	
	public HealthPotion(double x, double y, double w, double h) {
		super(x, y, w, h);
		
		currentAnimation = 0;
		isCollected = false;
		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 48;
		height = positionY + Map.FLOOR_HEIGHT - POTION_HEIGHT;
		gc.drawImage(images[currentAnimation ++], 0, height, POTION_WIDTH, POTION_HEIGHT);
	}
	
	public void changeImage() {
		draw();
	}
	
	public void effect(Player player) {
		player.setHp(player.getHp() + 15);
	}

	public boolean isCollision(Pair pos, State state) {
		if (		(positionX + 20 <=	pos.first + Player.PLAYER_WIDTH
									&& pos.first + Player.PLAYER_WIDTH	<= positionX + canvas.getWidth() - 20)
			||	(positionX + 20 <= pos.first
									&& pos.first							<= positionX + canvas.getWidth() - 20)	) {
			
			if (pos.second <= height && height <= pos.second + Player.PLAYER_HEIGHT) {
				return true;
			}
		}
		return false;
	}
}
