package game.model.obstacle;

import java.util.Random;

import game.model.Obstacle;
import game.model.character.Player;
import game.property.Hitbox;
import game.property.State;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scene.SceneManager;

public class AirObstacle extends Obstacle {
	private static final int AIR_HEIGHT = 500;
	private static final Image[] images = new Image[12];
	static {
		for (int i = 0; i < 12; ++i) {
			images[i] = new Image("images/obstacle/air" + i + ".png");
		}
	}

	public AirObstacle() {
		super(SceneManager.SCREEN_WIDTH + 210, -50, OBSTACLE_WIDTH, SceneManager.SCREEN_HEIGHT);

		int rnd = new Random().nextInt(7);
		obstacle = images[rnd];
		width = obstacle.getWidth();
		height = obstacle.getHeight();

		hb = new Hitbox(75, 0, 50, AIR_HEIGHT + 50);

		draw();
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(obstacle, 0, 0, OBSTACLE_WIDTH, AIR_HEIGHT + 50);
	}

	public boolean isCollision(Player e) {
		if (e.getState() != State.SLIDING)
			return true;
		return this.isCollision(e);
	}
}
