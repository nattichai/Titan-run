package entity.obstacle;

import java.util.Random;

import entity.characters.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import property.State;

public class AirObstacle extends Obstacle {
	public static final int AIR_HEIGHT = 500;

	public AirObstacle(double x, double y, double w, double h) {
		super(x, y, w, h);

		int rnd = new Random().nextInt(2) + 3;
		obstacle = new Image(ClassLoader.getSystemResource("images/obstacle/air" + rnd + ".png").toString());
		height = obstacle.getHeight();

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, AIR_HEIGHT - height);
	}

	public boolean isCollision(Player e) {
		if (e.getState() != State.SLIDING)
			return true;
		return this.isCollision(e);
	}

}
