package entity.item;

import entity.characters.player.Player;
import entity.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.Hitbox;

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
		
		height = positionY + Map.FLOOR_HEIGHT - POTION_HEIGHT;
		hb = new Hitbox(0, height, 100, 100);
		currentAnimation = 0;
	}

	public HealthPotion() {
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());	//clear canvas
		currentAnimation %= 48;
		gc.drawImage(images[currentAnimation ++], 0, height, POTION_WIDTH, POTION_HEIGHT);
	}
	
	public void changeImage() {
		draw();
	}
	
	public void effect(Player player) {
		player.setHp(player.getHp() + 15);
	}
}
