package entity.obstacle;

import java.util.Random;

import entity.characters.player.Player;
import entity.item.Jelly;
import entity.map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Container;
import main.Main;
import property.State;
import utility.Hitbox;

public class HoleObstacle extends Obstacle {

	public static final double HOLE_WIDTH = 300;

	public HoleObstacle(double x, double y, double w, double h) {
		super(x, y, w, h);

		int rnd = new Random().nextInt(1) + 1;
		obstacle = new Image(ClassLoader.getSystemResource("images/obstacle/hole" + rnd + ".png").toString());
		height = obstacle.getHeight();
		
		hb = new Hitbox(150, Map.FLOOR_HEIGHT - 1, 40, 20);
		
		Jelly jelly = new Jelly(1100, -50, Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
		Container.getContainer().add(jelly);
		jelly = new Jelly(1200, -100, Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
		Container.getContainer().add(jelly);
		jelly = new Jelly(1300, -50, Jelly.JELLY_WIDTH, Main.SCREEN_HEIGHT);
		Container.getContainer().add(jelly);

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.drawImage(obstacle, 0, Map.FLOOR_HEIGHT - height / 2);
	}
	
	public boolean isCollision(Player e) {
		if (e.getState() != State.JUMPING)
			return true;
		return this.isCollision(e);
	}

}
